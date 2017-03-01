package br.com.tiagohs.popmovies.ui.interceptor;

import java.util.List;

import javax.inject.Inject;

import br.com.tiagohs.popmovies.ui.contracts.MovieDetailsOverviewContract;
import br.com.tiagohs.popmovies.database.repository.MovieRepository;
import br.com.tiagohs.popmovies.model.db.MovieDB;
import br.com.tiagohs.popmovies.model.movie.CollectionDetails;
import br.com.tiagohs.popmovies.model.movie.Movie;
import br.com.tiagohs.popmovies.model.response.RankingResponse;
import br.com.tiagohs.popmovies.server.methods.MoviesMethod;
import io.reactivex.Observable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class MovieDetailsOverviewInterceptor implements MovieDetailsOverviewContract.MovieDetailsOverviewInterceptor {

    private MoviesMethod mMoviesMethod;
    private MovieRepository mMovieRepository;

    @Inject
    public MovieDetailsOverviewInterceptor(MoviesMethod moviesMethod, MovieRepository movieRepository) {
        mMoviesMethod = moviesMethod;
        mMovieRepository = movieRepository;
    }

    @Override
    public Observable<RankingResponse> getMovieRankings(String imdbID) {
        return mMoviesMethod.getMovieRankings(imdbID)
                            .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<List<Movie>> getMovieCollections(int collectionID) {
        return mMoviesMethod.getMovieCollections(collectionID)
                            .subscribeOn(Schedulers.io())
                            .map(new Function<CollectionDetails, List<Movie>>() {
                                @Override
                                public List<Movie> apply(CollectionDetails collectionDetails) throws Exception {
                                    return collectionDetails.getMovies();
                                }
                            });
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
