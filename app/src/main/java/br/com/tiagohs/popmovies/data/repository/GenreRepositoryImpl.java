package br.com.tiagohs.popmovies.data.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import br.com.tiagohs.popmovies.data.PopMoviesContract;
import br.com.tiagohs.popmovies.data.PopMoviesDB;
import br.com.tiagohs.popmovies.data.SQLHelper;
import br.com.tiagohs.popmovies.model.db.GenreDB;
import br.com.tiagohs.popmovies.model.movie.Genre;

public class GenreRepositoryImpl implements GenreRepository {
    private static final String TAG = GenreRepositoryImpl.class.getSimpleName();

    private PopMoviesDB mPopMoviesDB;
    private SimpleDateFormat mDateFormat;

    public GenreRepositoryImpl(Context context) {
        this.mPopMoviesDB = new PopMoviesDB(context);
        mDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    public void saveGenres(List<GenreDB> genres, long movieID) {

        for (GenreDB genre : genres) {
            saveGenre(genre, movieID);
        }
    }

    public long saveGenre(GenreDB genre, long movieID) {
        SQLiteDatabase db = mPopMoviesDB.getWritableDatabase();

        long genreID = 0;

        try {
            ContentValues values = getGenresContentValues(genre, movieID);

            boolean genreJaExistente = findGenreByGenreID(genre.getGenrerID(), movieID) != null;

            if (genreJaExistente)
                db.update(PopMoviesContract.GenreEntry.TABLE_NAME, values, SQLHelper.GenreSQL.WHERE_USER_BY_GENRE_ID, new String[]{String.valueOf(genreID), String.valueOf(movieID)});
            else
                genreID = db.insert(PopMoviesContract.GenreEntry.TABLE_NAME, "", values);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            db.close();
        }

        return genreID;
    }

    private GenreDB find(String where, String[] values) {
        SQLiteDatabase db = mPopMoviesDB.getWritableDatabase();
        Log.i(TAG, "Find Genre Chamado.");

        try {
            Cursor c = db.query(PopMoviesContract.GenreEntry.TABLE_NAME, null, where, values, null, null, null);
            if (c.moveToFirst()) {
                return getGenreDBByCursor(c);
            } else {
                return null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public GenreDB findGenreByGenreID(int genreID, long movieID) {
        return find(SQLHelper.GenreSQL.WHERE_USER_BY_GENRE_ID, new String[]{String.valueOf(genreID), String.valueOf(movieID)});
    }

    public List<GenreDB> findAllGenreDB(long movieID) {
        SQLiteDatabase db = mPopMoviesDB.getWritableDatabase();
        Log.i(TAG, "findAll GenreDB Chamado.");

        try {
            return genreDBCursorToList(db.query(PopMoviesContract.GenreEntry.TABLE_NAME, null, SQLHelper.GenreSQL.WHERE_ALL_GENRE, new String[]{String.valueOf(movieID)}, null, null, null));
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            db.close();
        }

        return null;
    }

    public List<Integer> findAllGenreID(long movieID) {
        SQLiteDatabase db = mPopMoviesDB.getWritableDatabase();
        Log.i(TAG, "findAll GenreIDs Chamado.");

        List<Integer> genresID = new ArrayList<>();
        Integer id = null;

        try {
            Cursor c = db.query(PopMoviesContract.GenreEntry.TABLE_NAME, null, SQLHelper.GenreSQL.WHERE_ALL_GENRE, new String[]{String.valueOf(movieID)}, null, null, null);

            if (c.moveToFirst()) {
                do {
                    id = c.getInt(c.getColumnIndex(PopMoviesContract.GenreEntry.COLUMN_GENRER_ID));
                    genresID.add(id);
                } while (c.moveToNext());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            db.close();
        }

        return genresID;
    }

    private List<GenreDB> genreDBCursorToList(Cursor c) {
        List<GenreDB> genres = new ArrayList<>();

        if (c.moveToFirst()) {
            do {
                genres.add(getGenreDBByCursor(c));
            } while (c.moveToNext());
        }

        return genres;
    }

    private List<Genre> genreCursorToList(Cursor c) {
        List<Genre> genres = new ArrayList<>();

        if (c.moveToFirst()) {
            do {
                Genre genre = new Genre();

                genre.setId(c.getInt(c.getColumnIndex(PopMoviesContract.GenreEntry.COLUMN_GENRER_ID)));
                genre.setName(c.getString(c.getColumnIndex(PopMoviesContract.GenreEntry.COLUMN_GENRER_NAME)));

                genres.add(genre);
            } while (c.moveToNext());
        }

        return genres;
    }

    private GenreDB getGenreDBByCursor(Cursor c) {
        GenreDB genre = new GenreDB();

        genre.setId(c.getInt(c.getColumnIndex(PopMoviesContract.GenreEntry._ID)));
        genre.setGenrerID(c.getInt(c.getColumnIndex(PopMoviesContract.GenreEntry.COLUMN_GENRER_ID)));
        genre.setGenrerName(c.getString(c.getColumnIndex(PopMoviesContract.GenreEntry.COLUMN_GENRER_NAME)));

        return genre;
    }

    private ContentValues getGenresContentValues(GenreDB genre, long movieID) {
        ContentValues values = new ContentValues();

        values.put(PopMoviesContract.GenreEntry.COLUMN_GENRER_ID, genre.getGenrerID());
        values.put(PopMoviesContract.GenreEntry.COLUMN_MOVIE_FORER_ID_SERVER, movieID);
        values.put(PopMoviesContract.GenreEntry.COLUMN_GENRER_NAME, genre.getGenrerName());

        return values;
    }
}
