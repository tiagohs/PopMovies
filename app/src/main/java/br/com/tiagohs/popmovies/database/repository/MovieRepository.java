package br.com.tiagohs.popmovies.database.repository;

import java.util.List;

import br.com.tiagohs.popmovies.model.db.MovieDB;
import br.com.tiagohs.popmovies.model.movie.Movie;
import io.reactivex.Observable;

public interface MovieRepository {

    Observable<Long> saveMovie(MovieDB movie);

    Observable<Integer> deleteMovieByID(long id, long profileID);
    Observable<Integer> deleteMovieByServerID(long serverID, long profileID);

    Observable<Movie> findMovieByServerID(int serverID, long profileID);

    Observable<Boolean> isFavoriteMovie(long profileID, int serverID);
    Observable<Boolean> isWantSeeMovie(long profileID, int serverID);
    Observable<Boolean> isWachedMovie(long profileID, int serverID);
    Observable<Boolean> isDontWantSeeMovie(long profileID, int serverID);

    Observable<List<MovieDB>> findAllMoviesDB(long profileID);
    Observable<List<MovieDB>> findAllMoviesWatched(long profileID);
    Observable<List<MovieDB>> findAllMoviesWantSee(long profileID);
    Observable<List<MovieDB>> findAllMoviesDontWantSee(long profileID);
    Observable<List<MovieDB>> findAllFavoritesMovies(long profileID);

    Observable<List<MovieDB>> findAllMoviesWatched(long profileID, int page);
    Observable<List<MovieDB>> findAllMoviesWantSee(long profileID, int page);
    Observable<List<MovieDB>> findAllMoviesDontWantSee(long profileID, int page);
    Observable<List<MovieDB>> findAllFavoritesMovies(long profileID, int page);
}
