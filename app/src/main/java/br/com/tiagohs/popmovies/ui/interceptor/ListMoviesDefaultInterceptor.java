package br.com.tiagohs.popmovies.ui.interceptor;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import br.com.tiagohs.popmovies.ui.contracts.ListMoviesDefaultContract;
import br.com.tiagohs.popmovies.database.repository.MovieRepository;
import br.com.tiagohs.popmovies.database.repository.ProfileRepository;
import br.com.tiagohs.popmovies.model.credits.CreditMovieBasic;
import br.com.tiagohs.popmovies.model.db.MovieDB;
import br.com.tiagohs.popmovies.model.dto.DiscoverDTO;
import br.com.tiagohs.popmovies.model.movie.Movie;
import br.com.tiagohs.popmovies.model.movie.MovieDetails;
import br.com.tiagohs.popmovies.model.response.GenericListResponse;
import br.com.tiagohs.popmovies.model.response.MovieResponse;
import br.com.tiagohs.popmovies.model.response.PersonMoviesResponse;
import br.com.tiagohs.popmovies.server.methods.MoviesMethod;
import br.com.tiagohs.popmovies.server.methods.PersonsMethod;
import br.com.tiagohs.popmovies.util.DateUtils;
import br.com.tiagohs.popmovies.util.MovieUtils;
import br.com.tiagohs.popmovies.util.enumerations.Sort;
import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class ListMoviesDefaultInterceptor implements ListMoviesDefaultContract.ListMoviesDefaultInterceptor {

    private MoviesMethod mMoviesMethod;
    private PersonsMethod mPersonsMethod;

    private MovieRepository mMovieRepository;
    private ProfileRepository mProfileRepository;

    @Inject
    public ListMoviesDefaultInterceptor(MoviesMethod moviesMethod, PersonsMethod personsMethod,
                                        MovieRepository movieRepository, ProfileRepository profileRepository) {
        mMoviesMethod = moviesMethod;
        mPersonsMethod = personsMethod;
        mMovieRepository = movieRepository;
        mProfileRepository = profileRepository;
    }

    @Override
    public Observable<GenericListResponse<Movie>> getDiscoverMovies(int currentPage, DiscoverDTO discoverDTO) {
        return mMoviesMethod.getMoviesDiscover(currentPage, discoverDTO)
                .subscribeOn(Schedulers.io())
                .map(new Function<MovieResponse, GenericListResponse<Movie>>() {
                    @Override
                    public GenericListResponse<Movie> apply(MovieResponse movieResponse) throws Exception {
                        GenericListResponse<Movie> response = new GenericListResponse<>();

                        response.setResults(movieResponse.getResults());
                        response.setPage(movieResponse.getPage());
                        response.setTotalPage(movieResponse.getTotalPages());
                        response.setTotalResults(movieResponse.getTotalResults());

                        return response;
                    }
                });
    }

    @Override
    public Observable<GenericListResponse<Movie>> getMoviesByKeywords(int keywordID, int currentPage) {
        return mMoviesMethod.getMoviesByKeywords(keywordID, currentPage)
                            .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<GenericListResponse<Movie>> getMovieSimilars(int movieID, int currentPage) {
        return mMoviesMethod.getMovieSimilars(movieID, currentPage)
                            .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<GenericListResponse<Movie>> getMoviesByGenres(int genreID, int currentPage) {
        return mMoviesMethod.getMoviesByGenres(genreID, currentPage)
                            .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<GenericListResponse<Movie>> getPersonMoviesCredits(int personID, int currentPage) {
        return mPersonsMethod.getPersonMoviesCredits(personID, currentPage)
                            .subscribeOn(Schedulers.io())
                            .map(new Function<PersonMoviesResponse, GenericListResponse<Movie>>() {
                                @Override
                                public GenericListResponse<Movie> apply(PersonMoviesResponse personMoviesResponse) throws Exception {
                                    personMoviesResponse.getMoviesByCast().addAll(personMoviesResponse.getMoviesByCrew());

                                    GenericListResponse<Movie> response = new GenericListResponse<>();
                                    response.setId(personMoviesResponse.getId());
                                    response.setResults(onConvertForListMovies(personMoviesResponse.getMoviesByCast()));
                                    response.setTotalPage(1);
                                    response.setTotalResults(1);
                                    response.setPage(1);

                                    return response;
                                }
                            });
    }

    @Override
    public Observable<MovieDetails> getMovieDetails(int movieID, String[] appendToResponse) {
        return mMoviesMethod.getMovieDetails(movieID, appendToResponse)
                            .subscribeOn(Schedulers.io());
    }

    public Observable<Long> saveMovie(MovieDetails movie, boolean isFavorite, int status, long profileID) {
        return mMovieRepository.saveMovie(new MovieDB(movie.getId(), status, movie.getRuntime(), movie.getPosterPath(),
                                                    movie.getTitle(), isFavorite, movie.getVoteCount(), profileID,
                                                    Calendar.getInstance(), DateUtils.formateStringToCalendar(movie.getReleaseDate()),
                                                    DateUtils.getYearByDate(movie.getReleaseDate()), MovieUtils.genreToGenreDB(movie.getGenres())))
                                .subscribeOn(Schedulers.io());
    }

    public Observable<Integer> deleteMovie(MovieDetails movie, long profileID) {
        return mMovieRepository.deleteMovieByServerID(movie.getId(), profileID)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<Movie> findMovieByServerID(int serverID, long profileID) {
        return mMovieRepository.findMovieByServerID(serverID, profileID)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<GenericListResponse<Movie>> getMovieDB(Sort typeList, long profileID, final int currentPage) {
        Observable<List<MovieDB>> observableDatabase = null;
        Observable<Long> observableTotal = null;

        switch (typeList) {
            case ASSISTIDOS:
                observableDatabase = mMovieRepository.findAllMoviesWatched(profileID, currentPage);
                observableTotal = mProfileRepository.getTotalMoviesWatched(profileID);
                break;
            case FAVORITE:
                observableDatabase = mMovieRepository.findAllFavoritesMovies(profileID, currentPage);
                observableTotal = mProfileRepository.getTotalFavorites(profileID);
                break;
            case QUERO_VER:
                observableDatabase = mMovieRepository.findAllMoviesWantSee(profileID, currentPage);
                observableTotal = mProfileRepository.getTotalMoviesWantSee(profileID);
                break;
            case NAO_QUERO_VER:
                observableDatabase = mMovieRepository.findAllMoviesDontWantSee(profileID, currentPage);
                observableTotal = mProfileRepository.getTotalMoviesDontWantSee(profileID);
                break;
        }

        return Observable.zip(observableDatabase, observableTotal, new BiFunction<List<MovieDB>, Long, GenericListResponse<Movie>>() {
            @Override
            public GenericListResponse<Movie> apply(List<MovieDB> movieDBs, Long aLong) throws Exception {
                GenericListResponse<Movie> genericListResponse = new GenericListResponse<Movie>();

                genericListResponse.setResults(MovieUtils.convertMovieDBToMovie(movieDBs));
                genericListResponse.setPage(currentPage);
                genericListResponse.setTotalPage((int) Math.ceil(new Double(aLong) / new Double(6)));

                return genericListResponse;
            }
        }).subscribeOn(Schedulers.io());
    }

    private List<Movie> onConvertForListMovies(List<CreditMovieBasic> listMovies) {
        List<Movie> list = new ArrayList<>();

        for (CreditMovieBasic m : listMovies) {
            Movie movie = new Movie();
            movie.setId(m.getId());
            if (list.contains(movie))
                continue;
            else {
                movie.setTitle(m.getTitle());
                movie.setPosterPath(m.getArtworkPath());
                list.add(movie);
            }
        }

        return list;
    }


}
