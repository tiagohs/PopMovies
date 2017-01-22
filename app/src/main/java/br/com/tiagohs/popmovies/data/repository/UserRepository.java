package br.com.tiagohs.popmovies.data.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.tiagohs.popmovies.data.PopMoviesContract;
import br.com.tiagohs.popmovies.data.PopMoviesDB;
import br.com.tiagohs.popmovies.data.SQLHelper;
import br.com.tiagohs.popmovies.model.db.MovieDB;
import br.com.tiagohs.popmovies.model.db.UserDB;
import br.com.tiagohs.popmovies.model.movie.Movie;
import br.com.tiagohs.popmovies.util.PrefsUtils;

import static android.R.attr.id;

public class UserRepository {
    private static final String TAG = UserRepository.class.getSimpleName();

    private PopMoviesDB mPopMoviesDB;

    public UserRepository(Context context) {
        this.mPopMoviesDB = new PopMoviesDB(context);
    }

    public long saveUser(UserDB user, Context context) {
        SQLiteDatabase db = null;
        Log.i(TAG, "Save User Chamado.");
        long userID = 0;

        try {
            ContentValues values = getUserContentValues(user);

            boolean userJaExistente = findUserByUsername(user.getUsername(), user.getProfileID()) != null;
            db = mPopMoviesDB.getWritableDatabase();

            if (userJaExistente)
                db.update(PopMoviesContract.UserEntry.TABLE_NAME, values, SQLHelper.UserSQL.WHERE_USER_BY_USERNAME, new String[]{user.getUsername(), String.valueOf(user.getProfileID())});
            else {
                userID = db.insert(PopMoviesContract.UserEntry.TABLE_NAME, "", values);
                user.setUserID((int) userID);
            }

            PrefsUtils.setCurrentUser(user, context);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            db.close();
        }

        return userID;
    }

    private void deleteUser(String[] values, String where) {
        SQLiteDatabase db = mPopMoviesDB.getWritableDatabase();
        Log.i(TAG, "Delete User Chamado.");

        try {
            db.delete(PopMoviesContract.UserEntry.TABLE_NAME, where, values);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            db.close();
        }
    }

    public void deleteUserByID(long id, long profileID) {
        deleteUser(new String[]{String.valueOf(id), String.valueOf(profileID)}, SQLHelper.UserSQL.WHERE_USER_BY_ID);
    }

    public void deleteUserByUsername(String username, long profileID) {
        deleteUser(new String[]{username, String.valueOf(profileID)}, SQLHelper.UserSQL.WHERE_USER_BY_USERNAME);
    }

    public UserDB findUserByUsername(String username, long profileID) {
        SQLiteDatabase db = mPopMoviesDB.getWritableDatabase();
        Log.i(TAG, "Find User Chamado.");

        try {
            Cursor c = db.query(PopMoviesContract.UserEntry.TABLE_NAME, null, SQLHelper.UserSQL.WHERE_USER_BY_USERNAME, new String[]{username, String.valueOf(profileID)}, null, null, null);
            if (c.moveToFirst()) {
                return getUserByCursor(c);
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
                users.add(getUserByCursor(c));
            } while (c.moveToNext());
        }

        return users;
    }

    private UserDB getUserByCursor(Cursor c) {
        UserDB user = new UserDB();

        user.setUserID(c.getInt(c.getColumnIndex(PopMoviesContract.UserEntry._ID)));
        user.setNome(c.getString(c.getColumnIndex(PopMoviesContract.UserEntry.COLUMN_NAME)));
        user.setPicturePath(c.getString(c.getColumnIndex(PopMoviesContract.UserEntry.COLUMN_PICTURE_PATH)));
        user.setToken(c.getString(c.getColumnIndex(PopMoviesContract.UserEntry.COLUMN_TOKEN)));
        user.setTypeLogin(c.getInt(c.getColumnIndex(PopMoviesContract.UserEntry.COLUMN_TYPE_LOGIN)));
        user.setUsername(c.getString(c.getColumnIndex(PopMoviesContract.UserEntry.COLUMN_USERNAME)));
        user.setEmail(c.getString(c.getColumnIndex(PopMoviesContract.UserEntry.COLUMN_EMAIL)));
        user.setSenha(c.getString(c.getColumnIndex(PopMoviesContract.UserEntry.COLUMN_PASSWORD)));
        user.setTypePhoto(c.getInt(c.getColumnIndex(PopMoviesContract.UserEntry.COLUMN_PICTURE_TYPE)));
        user.setProfileID(c.getInt(c.getColumnIndex(PopMoviesContract.UserEntry.COLUMN_PROFILE_ID)));
        user.setSenha(c.getString(c.getColumnIndex(PopMoviesContract.UserEntry.COLUMN_PICTURE_LOCAL_PATH)));

        return user;
    }

    private ContentValues getUserContentValues(UserDB user) {
        ContentValues values = new ContentValues();

        values.put(PopMoviesContract.UserEntry.COLUMN_NAME, user.getNome());
        values.put(PopMoviesContract.UserEntry.COLUMN_PICTURE_PATH, user.getPicturePath());
        values.put(PopMoviesContract.UserEntry.COLUMN_TOKEN, user.getToken());
        values.put(PopMoviesContract.UserEntry.COLUMN_TYPE_LOGIN, user.getTypeLogin());
        values.put(PopMoviesContract.UserEntry.COLUMN_USERNAME, user.getUsername());
        values.put(PopMoviesContract.UserEntry.COLUMN_EMAIL, user.getEmail());
        values.put(PopMoviesContract.UserEntry.COLUMN_PASSWORD, user.getSenha());
        values.put(PopMoviesContract.UserEntry.COLUMN_PROFILE_ID, user.getProfileID());
        values.put(PopMoviesContract.UserEntry.COLUMN_PICTURE_TYPE, user.getTypePhoto());

        return values;
    }

}
