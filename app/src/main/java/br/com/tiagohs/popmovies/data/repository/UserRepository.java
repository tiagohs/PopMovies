package br.com.tiagohs.popmovies.data.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.tiagohs.popmovies.data.PopMoviesContract;
import br.com.tiagohs.popmovies.data.PopMoviesDB;
import br.com.tiagohs.popmovies.model.db.MovieDB;
import br.com.tiagohs.popmovies.model.db.UserDB;
import br.com.tiagohs.popmovies.model.movie.Movie;

import static android.R.attr.id;

public class UserRepository {
    private static final String TAG = UserRepository.class.getSimpleName();

    private PopMoviesDB mPopMoviesDB;

    public UserRepository(Context context) {
        this.mPopMoviesDB = new PopMoviesDB(context);
    }

    public long saveUser(UserDB user) {
        SQLiteDatabase db = null;

        try {
            ContentValues values = getUserContentValues(user);

            boolean userJaExistente = findUserByEmail(user.getEmail()) != null;
            db = mPopMoviesDB.getWritableDatabase();

            if (userJaExistente)
                return db.update(PopMoviesContract.UserEntry.TABLE_NAME, values, PopMoviesContract.UserEntry.COLUMN_EMAIL + "=?", new String[]{user.getEmail()});
            else
                return db.insert(PopMoviesContract.UserEntry.TABLE_NAME, "", values);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            db.close();
        }

        return 0;
    }

    private void deleteUser(long id, String where) {
        SQLiteDatabase db = mPopMoviesDB.getWritableDatabase();
        Log.i(TAG, "Delete User Chamado.");

        try {
            db.delete(PopMoviesContract.UserEntry.TABLE_NAME, where, new String[]{String.valueOf(id)});
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            db.close();
        }
    }

    public void deleteUserByID(long id) {
        deleteUser(id, PopMoviesContract.UserEntry._ID + "=?");
    }

    public void deleteUserByEmail(String email) {
        deleteUser(id, PopMoviesContract.UserEntry.COLUMN_EMAIL + "=?");
    }

    public UserDB findUserByEmail(String email) {
        SQLiteDatabase db = mPopMoviesDB.getWritableDatabase();
        Log.i(TAG, "Find User Chamado.");

        try {
            Cursor c = db.query(PopMoviesContract.UserEntry.TABLE_NAME, null, PopMoviesContract.UserEntry.COLUMN_EMAIL + " = '" + email + "'", null, null, null, null);
            if (c.moveToFirst()) {
                UserDB user = new UserDB();
                user.setUserID(c.getInt(c.getColumnIndex(PopMoviesContract.UserEntry._ID)));
                user.setNome(c.getString(c.getColumnIndex(PopMoviesContract.UserEntry.COLUMN_NAME)));
                user.setEmail(c.getString(c.getColumnIndex(PopMoviesContract.UserEntry.COLUMN_EMAIL)));
                user.setSenha(c.getString(c.getColumnIndex(PopMoviesContract.UserEntry.COLUMN_PASSWORD)));

                return user;
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

    public List<UserDB> findAllUsers() {
        SQLiteDatabase db = mPopMoviesDB.getWritableDatabase();
        Log.i(TAG, "findAll Users Chamado.");

        try {
            return movieCursorToList(db.query(PopMoviesContract.MoviesEntry.TABLE_NAME, null, null, null, null, null, null));
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            db.close();
        }

        return null;
    }

    private List<UserDB> movieCursorToList(Cursor c) {
        List<UserDB> users = new ArrayList<>();

        if (c.moveToFirst()) {
            do {
                UserDB user = new UserDB();
                user.setUserID(c.getInt(c.getColumnIndex(PopMoviesContract.UserEntry._ID)));
                user.setNome(c.getString(c.getColumnIndex(PopMoviesContract.UserEntry.COLUMN_NAME)));
                user.setEmail(c.getString(c.getColumnIndex(PopMoviesContract.UserEntry.COLUMN_EMAIL)));
                user.setSenha(c.getString(c.getColumnIndex(PopMoviesContract.UserEntry.COLUMN_PASSWORD)));

                users.add(user);
            } while (c.moveToNext());
        }

        return users;
    }

    private ContentValues getUserContentValues(UserDB user) {
        ContentValues values = new ContentValues();

        values.put(PopMoviesContract.UserEntry.COLUMN_NAME, user.getNome());
        values.put(PopMoviesContract.UserEntry.COLUMN_EMAIL, user.getEmail());
        values.put(PopMoviesContract.UserEntry.COLUMN_PASSWORD, user.getSenha());

        return values;
    }
}
