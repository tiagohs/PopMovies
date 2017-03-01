package br.com.tiagohs.popmovies.ui.interceptor;

import javax.inject.Inject;

import br.com.tiagohs.popmovies.ui.contracts.SearchContract;
import br.com.tiagohs.popmovies.database.repository.MovieRepository;
import br.com.tiagohs.popmovies.model.db.MovieDB;
import br.com.tiagohs.popmovies.model.movie.Movie;
import br.com.tiagohs.popmovies.model.movie.MovieDetails;
import br.com.tiagohs.popmovies.model.person.PersonFind;
import br.com.tiagohs.popmovies.model.response.GenericListResponse;
import br.com.tiagohs.popmovies.server.methods.MoviesMethod;
import br.com.tiagohs.popmovies.server.methods.PersonsMethod;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class SearchInterceptor implements SearchContract.SearchInterceptor {

    private MoviesMethod mMoviesMethod;
    private PersonsMethod mPersonsMethod;
    private MovieRepository mMovieRepository;

    @Inject
    public SearchInterceptor(MoviesMethod moviesMethod, PersonsMethod personsMethod, MovieRepository movieRepository) {
        mMoviesMethod = moviesMethod;
        mPersonsMethod = personsMethod;
        mMovieRepository = movieRepository;
    }

    @Override
    public Observable<GenericListResponse<Movie>> searchMovies(String query, Boolean includeAdult, Integer searchYear, Integer primaryReleaseYear, Integer currentPage) {
        return mMoviesMethod.searchMovies(query, includeAdult, searchYear, primaryReleaseYear, currentPage)
                            .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<GenericListResponse<PersonFind>> searchPerson(String query, Boolean includeAdult, Integer currentPage) {
        return mPersonsMethod.searchPerson(query, includeAdult, currentPage)
                             .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<MovieDetails> getMovieDetails(int movieID, String[] appendToResponse) {
        return mMoviesMethod.getMovieDetails(movieID, appendToResponse)
                            .subscribeOn(Schedulers.io());
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

    @Override
    public Observable<Movie> findMovieByServerID(int serverID, long profileID) {
        return mMovieRepository.findMovieByServerID(serverID, profileID)
                               .subscribeOn(Schedulers.io());
    }
}
