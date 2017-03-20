package br.com.tiagohs.popmovies.database.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.com.tiagohs.popmovies.database.DatabaseManager;
import br.com.tiagohs.popmovies.database.PopMoviesContract;
import br.com.tiagohs.popmovies.database.SQLHelper;
import br.com.tiagohs.popmovies.model.db.UserDB;
import br.com.tiagohs.popmovies.util.EmptyUtils;
import br.com.tiagohs.popmovies.util.PrefsUtils;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.schedulers.Schedulers;

public class UserRepositoryImpl implements UserRepository {
    private static final String TAG = UserRepositoryImpl.class.getSimpleName();

    private DatabaseManager mDatabaseManager;

    @Inject
    public UserRepositoryImpl(DatabaseManager databaseManager) {
        this.mDatabaseManager = databaseManager;
    }

    public Observable<Long> saveUser(final UserDB user, final Context context) {
        return Observable.create(new ObservableOnSubscribe<Long>() {
            @Override
            public void subscribe(ObservableEmitter<Long> subscriber) throws Exception {
                SQLiteDatabase db = null;
                long userID = 0;

                try {
                    ContentValues values = getUserContentValues(user);

                    boolean userJaExistente = EmptyUtils.isNotNull(findUserDatabase(SQLHelper.UserSQL.WHERE_USER_BY_USERNAME, new String[]{user.getUsername(), String.valueOf(user.getProfileID())}));
                    db = mDatabaseManager.openDatabase();

                    if (userJaExistente)
                        db.update(PopMoviesContract.UserEntry.TABLE_NAME, values, SQLHelper.UserSQL.WHERE_USER_BY_USERNAME, new String[]{user.getUsername(), String.valueOf(user.getProfileID())});
                    else {
                        userID = db.insert(PopMoviesContract.UserEntry.TABLE_NAME, "", values);

                        if (userID != -1)
                            user.setUserID((int) userID);
                    }

                    PrefsUtils.setCurrentUser(user, context);

                    subscriber.onNext(userID);
                    subscriber.onComplete();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    subscriber.onError(ex);
                } finally {
                    mDatabaseManager.closeDatabase();
                }
            }
        }).subscribeOn(Schedulers.io());

    }

    private Observable<Integer> deleteUser(final String[] values, final String where) {
        return Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> subscriber) throws Exception {
                SQLiteDatabase db = mDatabaseManager.openDatabase();

                try {
                    int idReturn = db.delete(PopMoviesContract.UserEntry.TABLE_NAME, where, values);
                    subscriber.onNext(idReturn);
                    subscriber.onComplete();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    subscriber.onError(ex);
                } finally {
                    mDatabaseManager.closeDatabase();
                }
            }
        }).subscribeOn(Schedulers.io());
    }

    public Observable<Integer> deleteUserByID(long id, long profileID) {
        return deleteUser(new String[]{String.valueOf(id), String.valueOf(profileID)}, SQLHelper.UserSQL.WHERE_USER_BY_ID);
    }

    public Observable<Integer> deleteUserByUsername(String username, long profileID) {
        return deleteUser(new String[]{username, String.valueOf(profileID)}, SQLHelper.UserSQL.WHERE_USER_BY_USERNAME);
    }

    public Observable<UserDB> findUserByUsername(final String username, final long profileID) {
        return Observable.create(new ObservableOnSubscribe<UserDB>() {
            @Override
            public void subscribe(ObservableEmitter<UserDB> observableEmitter) {

                UserDB user = findUserDatabase(SQLHelper.UserSQL.WHERE_USER_BY_USERNAME, new String[]{username, String.valueOf(profileID)});

                if (EmptyUtils.isNotNull(user))
                    observableEmitter.onNext(user);

                observableEmitter.onComplete();
            }
        });
    }

    @Override
    public UserDB findUserDatabase(String username, long profileID) {
        return findUserDatabase(SQLHelper.UserSQL.WHERE_USER_BY_USERNAME, new String[]{username, String.valueOf(profileID)});
    }

    public UserDB findUserDatabase(final String where, final String[] values) {
        SQLiteDatabase db = mDatabaseManager.openDatabase();

        try {
            Cursor c = db.query(PopMoviesContract.UserEntry.TABLE_NAME, null, where, values, null, null, null);
            if (c.moveToFirst()) {
                return getUserByCursor(c);
            } else {
                return null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            mDatabaseManager.closeDatabase();
        }

        return null;
    }

    public Observable<List<UserDB>> findAllUsers() {
        return Observable.create(new ObservableOnSubscribe<List<UserDB>>() {
            @Override
            public void subscribe(ObservableEmitter<List<UserDB>> observableEmitter) {
                observableEmitter.onNext(findAllUsersDatabase());
                observableEmitter.onComplete();
            }
        });
    }

    public List<UserDB> findAllUsersDatabase() {
        SQLiteDatabase db = mDatabaseManager.openDatabase();

        try {
            return movieCursorToList(db.query(PopMoviesContract.MoviesEntry.TABLE_NAME, null, null, null, null, null, null));
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            mDatabaseManager.closeDatabase();
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
        user.setTypePhoto(c.getInt(c.getColumnIndex(PopMoviesContract.UserEntry.COLUMN_PICTURE_TYPE)));
        user.setProfileID(c.getInt(c.getColumnIndex(PopMoviesContract.UserEntry.COLUMN_PROFILE_ID)));
        user.setLocalPicture(c.getString(c.getColumnIndex(PopMoviesContract.UserEntry.COLUMN_PICTURE_LOCAL_PATH)));

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
        values.put(PopMoviesContract.UserEntry.COLUMN_PROFILE_ID, user.getProfileID());
        values.put(PopMoviesContract.UserEntry.COLUMN_PICTURE_TYPE, user.getTypePhoto());
        values.put(PopMoviesContract.UserEntry.COLUMN_PICTURE_LOCAL_PATH, user.getLocalPicture());

        return values;
    }

}
