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
import java.util.Date;
import java.util.List;

import br.com.tiagohs.popmovies.data.PopMoviesContract;
import br.com.tiagohs.popmovies.data.PopMoviesDB;
import br.com.tiagohs.popmovies.model.db.MovieDB;
import br.com.tiagohs.popmovies.model.movie.Movie;

import static android.R.attr.id;

public class MovieRepository {
    private static final String TAG = MovieRepository.class.getSimpleName();

    private PopMoviesDB mPopMoviesDB;
    private SimpleDateFormat mDateFormat;

    public MovieRepository(Context context) {
        this.mPopMoviesDB = new PopMoviesDB(context);
        mDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    public long saveMovie(MovieDB movie) {
        SQLiteDatabase db = null;

        try {
            ContentValues values = getMoviesContentValues(movie);

            boolean movieJaExistente = findMovieByServerID((int) movie.getIdServer(), movie.getProfileID()) != null;
            db = mPopMoviesDB.getWritableDatabase();

            if (movieJaExistente)
                return db.update(PopMoviesContract.MoviesEntry.TABLE_NAME, values, PopMoviesContract.MoviesEntry.COLUMN_ID_SERVER + "=?", new String[]{String.valueOf(movie.getIdServer())});
            else
                return db.insert(PopMoviesContract.MoviesEntry.TABLE_NAME, "", values);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            db.close();
        }

        return 0;
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
        deleteMovie(id, profileID, PopMoviesContract.MoviesEntry._ID + " = ? AND " + PopMoviesContract.MoviesEntry.COLUMN_PROFILE_FORER_ID + " = ?");
    }

    public void deleteMovieByServerID(long serverID, long profileID) {
        deleteMovie(serverID, profileID, PopMoviesContract.MoviesEntry.COLUMN_ID_SERVER + " = ? AND " + PopMoviesContract.MoviesEntry.COLUMN_PROFILE_FORER_ID + " = ?");
    }

    public Movie findMovieByServerID(int serverID, long profileID) {
        SQLiteDatabase db = mPopMoviesDB.getWritableDatabase();
        Log.i(TAG, "Find Movie Chamado.");

        try {
            Cursor c = db.query(PopMoviesContract.MoviesEntry.TABLE_NAME, null, PopMoviesContract.MoviesEntry.COLUMN_ID_SERVER + " = ? AND " + PopMoviesContract.MoviesEntry.COLUMN_PROFILE_FORER_ID + " = ?", new String[]{String.valueOf(serverID), String.valueOf(profileID)}, null, null, null);
            if (c.moveToFirst()) {
                Movie movie = new Movie();
                movie.setId(c.getInt(c.getColumnIndex(PopMoviesContract.MoviesEntry.COLUMN_ID_SERVER)));
                movie.setPosterPath(c.getString(c.getColumnIndex(PopMoviesContract.MoviesEntry.COLUMN_POSTER_PATH)));
                movie.setFavorite(c.getString(c.getColumnIndex(PopMoviesContract.MoviesEntry.COLUMN_FAVORITE)).equals("0") ? false : true);
                movie.setTitle(c.getString(c.getColumnIndex(PopMoviesContract.MoviesEntry.COLUMN_TITLE)));
                movie.setVoteAverage(c.getString(c.getColumnIndex(PopMoviesContract.MoviesEntry.COLUMN_VOTES)));

                return movie;
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

    public List<Movie> findAllMovies(long profileID) {
        SQLiteDatabase db = mPopMoviesDB.getWritableDatabase();
        Log.i(TAG, "findAll Movie Chamado.");

        try {
            return movieCursorToList(db.query(PopMoviesContract.MoviesEntry.TABLE_NAME, null, PopMoviesContract.MoviesEntry.COLUMN_PROFILE_FORER_ID + " = ?", new String[]{String.valueOf(profileID)}, null, null, null));
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            db.close();
        }

        return null;
    }

    public List<MovieDB> findAllMoviesDB(long profileID) {
        SQLiteDatabase db = mPopMoviesDB.getWritableDatabase();
        Log.i(TAG, "findAll MovieDB Chamado.");

        try {
            return movieDBCursorToList(db.query(PopMoviesContract.MoviesEntry.TABLE_NAME, null, PopMoviesContract.MoviesEntry.COLUMN_PROFILE_FORER_ID + " = ?", new String[]{String.valueOf(profileID)}, null, null, null));
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            db.close();
        }

        return null;
    }

    public List<MovieDB> findAllFavoritesMovies(long profileID) {
        SQLiteDatabase db = mPopMoviesDB.getWritableDatabase();
        Log.i(TAG, "findAll Favorites Chamado.");

        try {
            return movieDBCursorToList(db.query(PopMoviesContract.MoviesEntry.TABLE_NAME, null, PopMoviesContract.MoviesEntry.COLUMN_PROFILE_FORER_ID + " = ? AND " + PopMoviesContract.MoviesEntry.COLUMN_FAVORITE + " = ?", new String[]{String.valueOf(profileID), String.valueOf(1)}, null, null, null));
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            db.close();
        }

        return null;
    }

    private List<MovieDB> movieDBCursorToList(Cursor c) {
        List<MovieDB> movies = new ArrayList<>();

        try {
            if (c.moveToFirst()) {
                do {
                    MovieDB movie = new MovieDB();
                    movie.setIdServer(c.getInt(c.getColumnIndex(PopMoviesContract.MoviesEntry.COLUMN_ID_SERVER)));
                    movie.setPosterPath(c.getString(c.getColumnIndex(PopMoviesContract.MoviesEntry.COLUMN_POSTER_PATH)));
                    movie.setFavorite(Boolean.parseBoolean(c.getString(c.getColumnIndex(PopMoviesContract.MoviesEntry.COLUMN_FAVORITE))));
                    movie.setTitle(c.getString(c.getColumnIndex(PopMoviesContract.MoviesEntry.COLUMN_TITLE)));
                    movie.setVote(c.getDouble(c.getColumnIndex(PopMoviesContract.MoviesEntry.COLUMN_VOTES)));
                    movie.setDuracao(c.getInt(c.getColumnIndex(PopMoviesContract.MoviesEntry.COLUMN_DURATION)));
                    movie.setProfileID(c.getInt(c.getColumnIndex(PopMoviesContract.MoviesEntry.COLUMN_PROFILE_FORER_ID)));

                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(mDateFormat.parse(c.getString(c.getColumnIndex(PopMoviesContract.MoviesEntry.COLUMN_DATE_SAVED))));
                    movie.setDateSaved(calendar);

                    movies.add(movie);
                } while (c.moveToNext());
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return movies;
    }

    private List<Movie> movieCursorToList(Cursor c) {
        List<Movie> movies = new ArrayList<>();

        if (c.moveToFirst()) {
            do {
                Movie movie = new Movie();
                movie.setId(c.getInt(c.getColumnIndex(PopMoviesContract.MoviesEntry.COLUMN_ID_SERVER)));
                movie.setPosterPath(c.getString(c.getColumnIndex(PopMoviesContract.MoviesEntry.COLUMN_POSTER_PATH)));
                movie.setFavorite(Boolean.parseBoolean(c.getString(c.getColumnIndex(PopMoviesContract.MoviesEntry.COLUMN_FAVORITE))));
                movie.setTitle(c.getString(c.getColumnIndex(PopMoviesContract.MoviesEntry.COLUMN_TITLE)));
                movie.setVoteAverage(c.getString(c.getColumnIndex(PopMoviesContract.MoviesEntry.COLUMN_VOTES)));

                movies.add(movie);
            } while (c.moveToNext());
        }

        return movies;
    }

    private ContentValues getMoviesContentValues(MovieDB movie) {
        ContentValues values = new ContentValues();

        values.put(PopMoviesContract.MoviesEntry.COLUMN_ID_SERVER, movie.getIdServer());
        values.put(PopMoviesContract.MoviesEntry.COLUMN_POSTER_PATH, movie.getPosterPath());
        values.put(PopMoviesContract.MoviesEntry.COLUMN_VOTES, movie.getVote());
        values.put(PopMoviesContract.MoviesEntry.COLUMN_TITLE, movie.getTitle());
        values.put(PopMoviesContract.MoviesEntry.COLUMN_FAVORITE, movie.isFavorite());
        values.put(PopMoviesContract.MoviesEntry.COLUMN_DATE_SAVED, mDateFormat.format(movie.getDateSaved().getTime()));
        values.put(PopMoviesContract.MoviesEntry.COLUMN_PROFILE_FORER_ID, movie.getProfileID());
        values.put(PopMoviesContract.MoviesEntry.COLUMN_DURATION, movie.getDuracao());

        return values;
    }

}
