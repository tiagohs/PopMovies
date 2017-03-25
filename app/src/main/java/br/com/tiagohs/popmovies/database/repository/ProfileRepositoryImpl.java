package br.com.tiagohs.popmovies.database.repository;

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

import javax.inject.Inject;

import br.com.tiagohs.popmovies.database.DatabaseManager;
import br.com.tiagohs.popmovies.database.PopMoviesContract;
import br.com.tiagohs.popmovies.database.SQLHelper;
import br.com.tiagohs.popmovies.model.db.ProfileDB;
import br.com.tiagohs.popmovies.model.dto.GenrerMoviesDTO;
import br.com.tiagohs.popmovies.util.EmptyUtils;
import br.com.tiagohs.popmovies.util.MovieUtils;
import br.com.tiagohs.popmovies.util.PrefsUtils;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

public class ProfileRepositoryImpl implements ProfileRepository {
    private static final String TAG = ProfileRepositoryImpl.class.getSimpleName();

    private UserRepository mUserRepository;
    private Context mContext;

    private SimpleDateFormat mDateFormat;

    private DatabaseManager mDatabaseManager;

    @Inject
    public ProfileRepositoryImpl(Context context, UserRepository userRepository, DatabaseManager databaseManager) {
        this.mDatabaseManager = databaseManager;
        this.mUserRepository = userRepository;
        this.mContext = context;

        mDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    }

    public Observable<Long> saveProfile(final ProfileDB profile) {
        return Observable.create(new ObservableOnSubscribe<Long>() {
            @Override
            public void subscribe(ObservableEmitter<Long> subscriber) throws Exception {
                SQLiteDatabase db = null;
                long id = 0;

                try {
                    ContentValues values = getProfileContentValues(profile);

                    boolean userJaExistente = EmptyUtils.isNotNull(findProfileDatabase(SQLHelper.ProfileSQL.WHERE_PROFILE_BY_USERNAME, profile.getUser().getUsername()));
                    db = mDatabaseManager.openDatabase();

                    if (userJaExistente)
                        db.update(PopMoviesContract.ProfileEntry.TABLE_NAME, values, SQLHelper.ProfileSQL.WHERE_PROFILE_BY_USERNAME, new String[]{profile.getUser().getUsername()});
                    else {
                        id = db.insert(PopMoviesContract.ProfileEntry.TABLE_NAME, "", values);
                        if (id != -1)
                            profile.setProfileID(id);
                    }

                    profile.getUser().setProfileID(profile.getProfileID());

                    PrefsUtils.setCurrentProfile(profile, mContext);

                    subscriber.onNext(id);
                    subscriber.onComplete();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    subscriber.onError(ex);
                } finally {
                    mDatabaseManager.closeDatabase();
                }
            }
        }).flatMap(new Function<Long, ObservableSource<Long>>() {
            @Override
            public ObservableSource<Long> apply(Long aLong) throws Exception {
                return mUserRepository.saveUser(profile.getUser(), mContext);
            }
        });
    }

    private Observable<Integer> deleteProfile(final String value, final String where) {
        return Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> subscriber) throws Exception {
                SQLiteDatabase db = mDatabaseManager.openDatabase();

                try {
                    int idReturn = db.delete(PopMoviesContract.ProfileEntry.TABLE_NAME, where, new String[]{value});
                    subscriber.onNext(idReturn);
                    subscriber.onComplete();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    subscriber.onError(ex);
                } finally {
                    mDatabaseManager.closeDatabase();
                }
            }
        });



    }

    public Observable<Integer> deleteProfileByID(long id) {
        return deleteProfile(String.valueOf(id), SQLHelper.ProfileSQL.WHERE_PROFILE_BY_ID);
    }

    public Observable<Integer> deleteProfileByUsername(final String username) {
        return deleteProfile(username, SQLHelper.ProfileSQL.WHERE_PROFILE_BY_USERNAME);
    }

    public Observable<ProfileDB> findProfileByUserUsername(final String username) {
        return Observable.create(new ObservableOnSubscribe<ProfileDB>() {
            @Override
            public void subscribe(ObservableEmitter<ProfileDB> observableEmitter) throws Exception {
                ProfileDB profile = findProfileDatabase(SQLHelper.ProfileSQL.WHERE_PROFILE_BY_USERNAME, username);

                if (EmptyUtils.isNotNull(profile))
                    observableEmitter.onNext(profile);

                observableEmitter.onComplete();
            }
        });

    }

    public ProfileDB findProfileDatabase(String where, String username) {
        SQLiteDatabase db = mDatabaseManager.openDatabase();

        try {
            Cursor c = db.query(PopMoviesContract.ProfileEntry.TABLE_NAME, null, where, new String[]{username}, null, null, null);
            if (c.moveToFirst()) {
                return getProfileByCursor(c, username);
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

    private Observable<Long> getTotal(final String sql, final String[] values) {
        return Observable.create(new ObservableOnSubscribe<Long>() {
            @Override
            public void subscribe(ObservableEmitter<Long> observableEmitter) {
                observableEmitter.onNext(getTotalDatabase(sql, values));
                observableEmitter.onComplete();
            }
        });
    }

    public long getTotalDatabase(String sql, String[] values) {
        SQLiteDatabase db = mDatabaseManager.openDatabase();
        long total = 0;

        try {
            Cursor c = db.rawQuery(sql, values);

            if(c.moveToFirst())
                total = c.getLong(0);
            else
                total = 0;
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            mDatabaseManager.closeDatabase();
        }

        return total;
    }

    private Observable<Boolean> hasSomeMovie(final String sql, final String[] values) {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> observableEmitter) {
                observableEmitter.onNext(hasSomeMovieDatabase(sql, values));
                observableEmitter.onComplete();
            }
        });
    }

    public boolean hasSomeMovieDatabase(String sql, String[] values) {
        SQLiteDatabase db = mDatabaseManager.openDatabase();
        long total = 0;

        try {
            Cursor c = db.rawQuery(sql, values);

            if(c.moveToFirst())
                total = c.getInt(0);
            else
                total = 0;
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            mDatabaseManager.closeDatabase();
        }

        return total == 1;
    }

    public Observable<Boolean> hasMoviesWatched(long profileID) {
        return hasSomeMovie(SQLHelper.ProfileSQL.SQL_HAS_MOVIE_WATCHED, new String[]{String.valueOf(profileID)});
    }

    public Observable<Boolean> hasMoviesWantSee(long profileID) {
        return hasSomeMovie(SQLHelper.ProfileSQL.SQL_HAS_MOVIE_WANT_SEE, new String[]{String.valueOf(profileID)});
    }

    public Observable<Boolean> hasMoviesDontWantSee(long profileID) {
        return hasSomeMovie(SQLHelper.ProfileSQL.SQL_HAS_MOVIE_DONT_WANT_SEE, new String[]{String.valueOf(profileID)});
    }

    public Observable<Boolean> hasMoviesFavorite(long profileID) {
        return hasSomeMovie(SQLHelper.ProfileSQL.SQL_HAS_MOVIE_FAVORITE, new String[]{String.valueOf(profileID)});
    }

    public Observable<Long> getTotalHoursWatched(long profileID) {
        return getTotal(SQLHelper.ProfileSQL.SQL_TOTAL_HOURS_WATCHED, new String[]{String.valueOf(profileID)});
    }

    public Observable<Long> getTotalMovies(long profileID) {
        return getTotal(SQLHelper.ProfileSQL.SQL_TOTAL_MOVIES, new String[]{String.valueOf(profileID)});
    }

    public Observable<Long> getTotalMoviesWatched(long profileID) {
        return getTotal(SQLHelper.ProfileSQL.SQL_TOTAL_MOVIES_WATCHED, new String[]{String.valueOf(profileID)});
    }

    public Observable<Long> getTotalMoviesWantSee(long profileID) {
        return getTotal(SQLHelper.ProfileSQL.SQL_TOTAL_MOVIES_WANT_SEE, new String[]{String.valueOf(profileID)});
    }

    public Observable<Long> getTotalMoviesDontWantSee(long profileID) {
        return getTotal(SQLHelper.ProfileSQL.SQL_TOTAL_MOVIES_DONT_WANT_SEE, new String[]{String.valueOf(profileID)});
    }

    public Observable<Long> getTotalFavorites(long profileID) {
        return getTotal(SQLHelper.ProfileSQL.SQL_TOTAL_MOVIES_FAVORITES, new String[]{String.valueOf(profileID)});
    }

    public Observable<Long> getTotalMoviesByGenrer(int genreID, long profileID) {
        return getTotal(SQLHelper.ProfileSQL.SQL_TOTAL_MOVIES_BY_GENRER, new String[]{String.valueOf(genreID), String.valueOf(profileID)});
    }

    public Observable<List<GenrerMoviesDTO>> getAllGenrersSaved(final long profileID) {
        return Observable.create(new ObservableOnSubscribe<List<GenrerMoviesDTO>>() {
            @Override
            public void subscribe(ObservableEmitter<List<GenrerMoviesDTO>> observableEmitter) {
                List<GenrerMoviesDTO> genres = new ArrayList<>();
                int[] genrersID = MovieUtils.getAllGenrerIDs();
                int[] namesID = MovieUtils.getAllGenrerNames();

                for (int cont = 0; cont < genrersID.length; cont++) {
                    long totalMovies = getTotalDatabase(SQLHelper.ProfileSQL.SQL_TOTAL_MOVIES_BY_GENRER, new String[]{String.valueOf(genrersID[cont]), String.valueOf(profileID)});

                    if (totalMovies > 0) {
                        GenrerMoviesDTO genrerDTO = new GenrerMoviesDTO(genrersID[cont], namesID[cont], totalMovies);

                        if (!genres.contains(genrerDTO))
                            genres.add(genrerDTO);
                    }

                }

                observableEmitter.onNext(genres);
                observableEmitter.onComplete();
            }
        });
    }

    public ProfileDB getProfileByCursor(Cursor c, String username) {
        ProfileDB profile = new ProfileDB();

        try {
            profile.setProfileID(c.getLong(c.getColumnIndex(PopMoviesContract.ProfileEntry._ID)));
            profile.setDescricao(c.getString(c.getColumnIndex(PopMoviesContract.ProfileEntry.COLUMN_DESCRIPTION)));
            profile.setGenrer(c.getString(c.getColumnIndex(PopMoviesContract.ProfileEntry.COLUMN_GENRER)));
            profile.setCountry(c.getString(c.getColumnIndex(PopMoviesContract.ProfileEntry.COLUMN_COUNTRY)));

            String birthdayString = c.getString(c.getColumnIndex(PopMoviesContract.ProfileEntry.COLUMN_BIRTHDAY));
            if (EmptyUtils.isNotNull(birthdayString)) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(mDateFormat.parse(birthdayString));
                profile.setBirthday(calendar);
            }

            profile.setTotalHorasAssistidas(getTotalDatabase(SQLHelper.ProfileSQL.SQL_TOTAL_HOURS_WATCHED, new String[]{String.valueOf(profile.getProfileID())}));
            profile.setUser(mUserRepository.findUserDatabase(username, profile.getProfileID()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return profile;
    }

    private ContentValues getProfileContentValues(ProfileDB profile) {
        ContentValues values = new ContentValues();

        values.put(PopMoviesContract.ProfileEntry.COLUMN_DESCRIPTION, profile.getDescricao());
        if (EmptyUtils.isNotNull(profile.getBirthday()))
            values.put(PopMoviesContract.ProfileEntry.COLUMN_BIRTHDAY, mDateFormat.format(profile.getBirthday().getTime()));

        values.put(PopMoviesContract.ProfileEntry.COLUMN_COUNTRY, profile.getCountry());
        values.put(PopMoviesContract.ProfileEntry.COLUMN_GENRER, profile.getGenrer());
        values.put(PopMoviesContract.ProfileEntry.COLUMN_USER_FORER_USERNAME, profile.getUser().getUsername());

        return values;
    }
}
