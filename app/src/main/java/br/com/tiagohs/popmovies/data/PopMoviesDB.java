package br.com.tiagohs.popmovies.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.com.tiagohs.popmovies.data.PopMoviesContract.MoviesEntry;
import br.com.tiagohs.popmovies.model.db.MovieDB;

import static android.R.attr.id;

public class PopMoviesDB extends SQLiteOpenHelper {
    private static final String TAG = PopMoviesDB.class.getSimpleName();

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "popmovies.db";

    public PopMoviesDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_MOVIES_TABLE = "CREATE TABLE " + MoviesEntry.TABLE_NAME + " (" +
                MoviesEntry._ID + " INTEGER PRIMARY KEY," +
                MoviesEntry.COLUMN_ID_SERVER + " INTEGER UNIQUE NOT NULL, " +
                MoviesEntry.COLUMN_FAVORITE + " TEXT NOT NULL, " +
                MoviesEntry.COLUMN_VOTES + " DECIMAL, " +
                MoviesEntry.COLUMN_POSTER_PATH + " TEXT NOT NULL, " +
                " );";

        sqLiteDatabase.execSQL(SQL_CREATE_MOVIES_TABLE);
        Log.i(TAG, "Tabela Movies Criada com Sucesso.");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MoviesEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MoviesEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public long saveMovie(MovieDB movie) {
        long id = movie.getId();
        SQLiteDatabase db = getWritableDatabase();
        Log.i(TAG, "Save Movie Chamado.");

        try {
            ContentValues values = getMoviesContentValues(movie);

            if (id != 0)
                return db.update(MoviesEntry.TABLE_NAME, values, MoviesEntry._ID + "=?", new String[]{String.valueOf(movie.getId())});
            else
                return db.insert(MoviesEntry.TABLE_NAME, "", values);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            db.close();
        }

        return 0;
    }

    private ContentValues getMoviesContentValues(MovieDB movie) {
        ContentValues values = new ContentValues();

        values.put(MoviesEntry._ID, movie.getId());
        values.put(MoviesEntry.COLUMN_ID_SERVER, movie.getIdServer());
        values.put(MoviesEntry.COLUMN_POSTER_PATH, movie.getPosterPath());
        values.put(MoviesEntry.COLUMN_VOTES, movie.getVote());

        return values;
    }

    private void deleteMovie(long id, String where) {
        SQLiteDatabase db = getWritableDatabase();
        Log.i(TAG, "Delete Movie Chamado.");

        try {
            db.delete(MoviesEntry.TABLE_NAME, where, new String[]{String.valueOf(id)});
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            db.close();
        }
    }

    public void deleteMovieByID(long id) {
        deleteMovie(id, MoviesEntry._ID + "=?");
    }

    public void deleteMovieByServerID(long serverID) {
        deleteMovie(id, MoviesEntry.COLUMN_ID_SERVER + "=?");
    }

    public List<MovieDB> findAllMovies() {
        SQLiteDatabase db = getWritableDatabase();

        try {
            return movieCursorToList(db.query(MoviesEntry.TABLE_NAME, null, null, null, null, null, null));
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            db.close();
        }

        return null;
    }

    private List<MovieDB> movieCursorToList(Cursor c) {
        List<MovieDB> movies = new ArrayList<>();

        if (c.moveToFirst()) {
            do {
                movies.add(new MovieDB(c.getLong(c.getColumnIndex(MoviesEntry._ID)),
                                            c.getLong(c.getColumnIndex(MoviesEntry.COLUMN_ID_SERVER)),
                                            c.getString(c.getColumnIndex(MoviesEntry.COLUMN_POSTER_PATH)),
                                            Boolean.parseBoolean(c.getString(c.getColumnIndex(MoviesEntry.COLUMN_FAVORITE))),
                                            c.getDouble(c.getColumnIndex(MoviesEntry.COLUMN_VOTES))));
            } while (c.moveToNext());
        }

        return movies;
    }

    public void execSQL(String sql) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            db.execSQL(sql);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            db.close();
        }
    }

    public void execSQL(String sql, Object[] args) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            db.execSQL(sql, args);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            db.close();
        }
    }
}
