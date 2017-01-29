package br.com.tiagohs.popmovies.data.repository;

import java.util.List;

import br.com.tiagohs.popmovies.model.db.MovieDB;
import br.com.tiagohs.popmovies.model.movie.Movie;

public interface MovieRepository {

    long saveMovie(MovieDB movie);

    void deleteMovieByID(long id, long profileID);
    void deleteMovieByServerID(long serverID, long profileID);

    Movie findMovieByServerID(int serverID, long profileID);

    boolean isFavoriteMovie(long profileID, int serverID);
    boolean isWantSeeMovie(long profileID, int serverID);
    boolean isWachedMovie(long profileID, int serverID);
    boolean isDontWantSeeMovie(long profileID, int serverID);

    List<Movie> findAllMovies(long profileID);
    List<MovieDB> findAllMoviesDB(long profileID);
    List<MovieDB> findAllMoviesWatched(long profileID);
    List<MovieDB> findAllMoviesWantSee(long profileID);
    List<MovieDB> findAllMoviesDontWantSee(long profileID);
    List<MovieDB> findAllFavoritesMovies(long profileID);
}
