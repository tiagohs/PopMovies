package br.com.tiagohs.popmovies.ui.interceptor;

import javax.inject.Inject;

import br.com.tiagohs.popmovies.database.repository.MovieRepository;
import br.com.tiagohs.popmovies.model.db.MovieDB;
import br.com.tiagohs.popmovies.model.movie.MovieDetails;
import br.com.tiagohs.popmovies.model.response.VideosResponse;
import br.com.tiagohs.popmovies.server.methods.MoviesMethod;
import br.com.tiagohs.popmovies.ui.contracts.MovieDetailsContract;
import br.com.tiagohs.popmovies.util.EmptyUtils;
import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;
import io.reactivex.schedulers.Schedulers;

public class MovieDetailsInterceptor implements MovieDetailsContract.MovieDetailsInterceptor {
    private final String US_LOCALE = "en-US";

    private MoviesMethod mMoviesMethod;
    private MovieRepository mMovieRepository;

    @Inject
    public MovieDetailsInterceptor(MoviesMethod moviesMethod, MovieRepository movieRepository) {
        mMoviesMethod = moviesMethod;
        mMovieRepository = movieRepository;
    }

    @Override
    public Observable<MovieDetails> getMovieDetails(int movieID, String[] appendToResponse) {
        Observable<MovieDetails> mCurrentLanguageMovieDetails = mMoviesMethod.getMovieDetails(movieID, appendToResponse);
        Observable<MovieDetails> mUsLanguageMovieDetails = mMoviesMethod.getMovieDetails(movieID, appendToResponse, US_LOCALE);

        return Observable.zip(mCurrentLanguageMovieDetails, mUsLanguageMovieDetails, new BiFunction<MovieDetails, MovieDetails, MovieDetails>() {
                                    @Override
                                    public MovieDetails apply(MovieDetails movieDetails, MovieDetails movieDetails2) throws Exception {

                                        if (EmptyUtils.isEmpty(movieDetails.getOverview()))
                                            movieDetails.setOverview(movieDetails2.getOverview());

                                        if (movieDetails.getRuntime() == 0)
                                            movieDetails.setRuntime(movieDetails2.getRuntime());

                                        return movieDetails;
                                    }
                                })
                          .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<MovieDetails> getMovieDetails(int movieID, String[] appendToResponse, String language) {
        return mMoviesMethod.getMovieDetails(movieID, appendToResponse, language)
                            .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<VideosResponse> getMovieVideos(int movieID, String language) {
        return mMoviesMethod.getMovieVideos(movieID, language)
                            .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<Boolean> isFavoriteMovie(long profileID, int serverID) {
        return mMovieRepository.isFavoriteMovie(profileID, serverID);
    }

    @Override
    public Observable<Boolean> isWantSeeMovie(long profileID, int serverID) {
        return mMovieRepository.isWantSeeMovie(profileID, serverID);
    }

    @Override
    public Observable<Boolean> isWachedMovie(long profileID, int serverID) {
        return mMovieRepository.isWachedMovie(profileID, serverID);
    }

    @Override
    public Observable<Boolean> isDontWantSeeMovie(long profileID, int serverID) {
        return mMovieRepository.isDontWantSeeMovie(profileID, serverID);
    }

    @Override
    public Observable<Long> saveMovie(MovieDB movie) {
        return mMovieRepository.saveMovie(movie)
                               .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<Integer> deleteMovieByServerID(long serverID, long profileID) {
        return mMovieRepository.deleteMovieByServerID(serverID, profileID)
                               .subscribeOn(Schedulers.io());
    }


}
