package br.com.tiagohs.popmovies.data.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.tiagohs.popmovies.data.PopMoviesContract;
import br.com.tiagohs.popmovies.data.PopMoviesDB;
import br.com.tiagohs.popmovies.data.SQLHelper;
import br.com.tiagohs.popmovies.model.db.GenreDB;
import br.com.tiagohs.popmovies.model.db.MovieDB;
import br.com.tiagohs.popmovies.model.movie.Movie;

public class MovieRepository {
    private static final String TAG = MovieRepository.class.getSimpleName();

    private PopMoviesDB mPopMoviesDB;
    private GenreRepository mGenerRepository;
    private SimpleDateFormat mDateFormat;

    public MovieRepository(Context context) {
        this.mPopMoviesDB = new PopMoviesDB(context);
        this.mGenerRepository = new GenreRepository(context);
        mDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    public long saveMovie(MovieDB movie) {
        SQLiteDatabase db = null;
        long movieID = 0;

        try {
            ContentValues values = getMoviesContentValues(movie);

            boolean movieJaExistente = findMovieByServerID(movie.getIdServer(), movie.getProfileID()) != null;
            db = mPopMoviesDB.getWritableDatabase();

            if (movieJaExistente)
                movieID = db.update(PopMoviesContract.MoviesEntry.TABLE_NAME, values, SQLHelper.MovieSQL.WHERE_MOVIE_BY_SERVER_ID, new String[]{String.valueOf(movie.getIdServer()), String.valueOf(movie.getProfileID())});
            else
                movieID = db.insert(PopMoviesContract.MoviesEntry.TABLE_NAME, "", values);

            mGenerRepository.saveGenres(movie.getGenres(), movie.getIdServer());
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            db.close();
        }

        return movieID;
    }

    private void deleteMovie(long id, long profileID, String where) {
        SQLiteDatabase db = mPopMoviesDB.getWritableDatabase();
        Log.i(TAG, "Delete Movie Chamado.");

        try {
            db.delete(PopMoviesContract.MoviesEntry.TABLE_NAME, where, new String[]{String.valueOf(id), String.valueOf(profileID)});
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            db.close();
        }
    }

    public void deleteMovieByID(long id, long profileID) {
        deleteMovie(id, profileID, SQLHelper.MovieSQL.WHERE_MOVIE_BY_ID);
    }

    public void deleteMovieByServerID(long serverID, long profileID) {
        deleteMovie(serverID, profileID, SQLHelper.MovieSQL.WHERE_MOVIE_BY_SERVER_ID);
    }

    private Movie findMovie(String where, String[] values) {
        SQLiteDatabase db = mPopMoviesDB.getWritableDatabase();
        Log.i(TAG, "Find Movie Chamado.");

        try {
            Cursor c = db.query(PopMoviesContract.MoviesEntry.TABLE_NAME, null, where, values, null, null, null);
            if (c.moveToFirst()) {
                return getMovieByCursor(c);
            } else {
                return null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            db.close();
        }

        return null;
    }

    public Movie findMovieByServerID(int serverID, long profileID) {
        return findMovie(SQLHelper.MovieSQL.WHERE_MOVIE_BY_SERVER_ID, new String[]{String.valueOf(serverID), String.valueOf(profileID)});
    }

    public boolean isFavoriteMovie(long profileID, int serverID) {
        return findMovie(SQLHelper.MovieSQL.WHERE_IS_FAVORITE_MOVIE, new String[]{String.valueOf(serverID), String.valueOf(profileID)}) != null;
    }

    public boolean isWantSeeMovie(long profileID, int serverID) {
        return findMovie(SQLHelper.MovieSQL.WHERE_IS_WANT_SEE_MOVIE, new String[]{String.valueOf(serverID), String.valueOf(profileID)}) != null;
    }

    public boolean isWachedMovie(long profileID, int serverID) {
        return findMovie(SQLHelper.MovieSQL.WHERE_IS_WATCHED_MOVIE, new String[]{String.valueOf(serverID), String.valueOf(profileID)}) != null;
    }

    public List<Movie> findAllMovies(long profileID) {
        SQLiteDatabase db = mPopMoviesDB.getWritableDatabase();
        Log.i(TAG, "findAll Movie Chamado.");

        try {
            return movieCursorToList(db.query(PopMoviesContract.MoviesEntry.TABLE_NAME, null, SQLHelper.MovieSQL.WHERE_ALL_MOVIE, new String[]{String.valueOf(profileID)}, null, null, null));
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            db.close();
        }

        return null;
    }

    private List<MovieDB> findAll(String[] values, String where) {
        SQLiteDatabase db = mPopMoviesDB.getWritableDatabase();
        Log.i(TAG, "findAll MovieDB Chamado.");

        try {
            return movieDBCursorToList(db.query(PopMoviesContract.MoviesEntry.TABLE_NAME, null, where, values, null, null, null));
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            db.close();
        }

        return null;
    }

    public List<MovieDB> findAllMoviesDB(long profileID) {
        return findAll(new String[]{String.valueOf(profileID)}, SQLHelper.MovieSQL.WHERE_ALL_MOVIE);
    }

    public List<MovieDB> findAllMoviesWatched(long profileID) {
        return findAll(new String[]{String.valueOf(profileID)}, SQLHelper.MovieSQL.WHERE_ALL_MOVIES_WATCHED);
    }

    public List<MovieDB> findAllMoviesWantSee(long profileID) {
        return findAll(new String[]{String.valueOf(profileID)}, SQLHelper.MovieSQL.WHERE_ALL_MOVIE_WANT_SEE);
    }

    public List<MovieDB> findAllFavoritesMovies(long profileID) {
        return findAll(new String[]{String.valueOf(profileID)}, SQLHelper.MovieSQL.WHERE_ALL_FAVORITE_MOVIE);
    }

    private List<MovieDB> movieDBCursorToList(Cursor c) {
        List<MovieDB> movies = new ArrayList<>();

        if (c.moveToFirst()) {
            do {
                movies.add(getMovieDBByCursor(c));
            } while (c.moveToNext());
        }

        return movies;
    }

    public Movie getMovieByCursor(Cursor c) {
        Movie movie = new Movie();

        movie.setId(c.getInt(c.getColumnIndex(PopMoviesContract.MoviesEntry.COLUMN_ID_SERVER)));
        movie.setPosterPath(c.getString(c.getColumnIndex(PopMoviesContract.MoviesEntry.COLUMN_POSTER_PATH)));
        movie.setFavorite(!c.getString(c.getColumnIndex(PopMoviesContract.MoviesEntry.COLUMN_FAVORITE)).equals("0"));
        movie.setTitle(c.getString(c.getColumnIndex(PopMoviesContract.MoviesEntry.COLUMN_TITLE)));
        movie.setStatusDB(c.getInt(c.getColumnIndex(PopMoviesContract.MoviesEntry.COLUMN_STATUS)));
        movie.setVoteAverage(c.getString(c.getColumnIndex(PopMoviesContract.MoviesEntry.COLUMN_VOTES)));
        movie.setReleaseDate(c.getString(c.getColumnIndex(PopMoviesContract.MoviesEntry.COLUMN_RELEASE_DATE)));
        movie.setGenreIDs(mGenerRepository.findAllGenreID(movie.getId()));

        return movie;
    }

    public MovieDB getMovieDBByCursor(Cursor c) {
        MovieDB movie = new MovieDB();

        try {
            movie.setId(c.getInt(c.getColumnIndex(PopMoviesContract.MoviesEntry._ID)));
            movie.setIdServer(c.getInt(c.getColumnIndex(PopMoviesContract.MoviesEntry.COLUMN_ID_SERVER)));
            movie.setPosterPath(c.getString(c.getColumnIndex(PopMoviesContract.MoviesEntry.COLUMN_POSTER_PATH)));
            movie.setStatus(c.getInt(c.getColumnIndex(PopMoviesContract.MoviesEntry.COLUMN_STATUS)));
            movie.setFavorite(!c.getString(c.getColumnIndex(PopMoviesContract.MoviesEntry.COLUMN_FAVORITE)).equals("0"));
            movie.setTitle(c.getString(c.getColumnIndex(PopMoviesContract.MoviesEntry.COLUMN_TITLE)));
            movie.setVote(c.getDouble(c.getColumnIndex(PopMoviesContract.MoviesEntry.COLUMN_VOTES)));
            movie.setRuntime(c.getInt(c.getColumnIndex(PopMoviesContract.MoviesEntry.COLUMN_RUNTIME)));
            movie.setProfileID(c.getInt(c.getColumnIndex(PopMoviesContract.MoviesEntry.COLUMN_PROFILE_FORER_ID)));

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(mDateFormat.parse(c.getString(c.getColumnIndex(PopMoviesContract.MoviesEntry.COLUMN_DATE_SAVED))));
            movie.setDateSaved(calendar);

            calendar.setTime(mDateFormat.parse(c.getString(c.getColumnIndex(PopMoviesContract.MoviesEntry.COLUMN_RELEASE_DATE))));
            movie.setReleaseDate(calendar);

            movie.setReleaseYear(c.getInt(c.getColumnIndex(PopMoviesContract.MoviesEntry.COLUMN_RELEASE_YEAR)));

            movie.setGenres(mGenerRepository.findAllGenreDB(movie.getIdServer()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return movie;
    }


    private List<Movie> movieCursorToList(Cursor c) {
        List<Movie> movies = new ArrayList<>();

        if (c.moveToFirst()) {
            do {
                movies.add(getMovieByCursor(c));
            } while (c.moveToNext());
        }

        return movies;
    }

    private ContentValues getMoviesContentValues(MovieDB movie) {
        ContentValues values = new ContentValues();

        values.put(PopMoviesContract.MoviesEntry.COLUMN_ID_SERVER, movie.getIdServer());
        values.put(PopMoviesContract.MoviesEntry.COLUMN_POSTER_PATH, movie.getPosterPath());
        values.put(PopMoviesContract.MoviesEntry.COLUMN_STATUS, movie.getStatus());
        values.put(PopMoviesContract.MoviesEntry.COLUMN_VOTES, movie.getVote());
        values.put(PopMoviesContract.MoviesEntry.COLUMN_TITLE, movie.getTitle());
        values.put(PopMoviesContract.MoviesEntry.COLUMN_RELEASE_DATE, mDateFormat.format(movie.getReleaseDate().getTime()));
        values.put(PopMoviesContract.MoviesEntry.COLUMN_RELEASE_YEAR, movie.getReleaseYear());
        values.put(PopMoviesContract.MoviesEntry.COLUMN_FAVORITE, movie.isFavorite());
        values.put(PopMoviesContract.MoviesEntry.COLUMN_DATE_SAVED, mDateFormat.format(movie.getDateSaved().getTime()));
        values.put(PopMoviesContract.MoviesEntry.COLUMN_PROFILE_FORER_ID, movie.getProfileID());
        values.put(PopMoviesContract.MoviesEntry.COLUMN_RUNTIME, movie.getRuntime());

        return values;
    }

}
