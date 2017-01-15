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
import br.com.tiagohs.popmovies.data.SQLHelper;
import br.com.tiagohs.popmovies.model.db.MovieDB;
import br.com.tiagohs.popmovies.model.db.ProfileDB;
import br.com.tiagohs.popmovies.model.movie.Movie;
import br.com.tiagohs.popmovies.util.PrefsUtils;

import static android.R.attr.id;

public class ProfileRepository {
    private static final String TAG = ProfileRepository.class.getSimpleName();

    private PopMoviesDB mPopMoviesDB;
    private MovieRepository mMovieRepository;
    private UserRepository mUserRepository;

    public ProfileRepository(Context context) {
        this.mPopMoviesDB = new PopMoviesDB(context);
        this.mMovieRepository = new MovieRepository(context);
        this.mUserRepository = new UserRepository(context);
    }

    public long saveProfile(ProfileDB profile, Context context) {
        SQLiteDatabase db = null;
        long id = 0;

        try {
            ContentValues values = getProfileContentValues(profile);

            boolean userJaExistente = findProfileByUserUsername(profile.getUser().getUsername()) != null;
            db = mPopMoviesDB.getWritableDatabase();

            if (userJaExistente)
                id = db.update(PopMoviesContract.ProfileEntry.TABLE_NAME, values, SQLHelper.ProfileSQL.WHERE_PROFILE_BY_USERNAME, new String[]{profile.getUser().getUsername()});
            else
                id = db.insert(PopMoviesContract.ProfileEntry.TABLE_NAME, "", values);

            mUserRepository.saveUser(profile.getUser(), context);
            PrefsUtils.setCurrentProfile(profile, context);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            db.close();
        }

        return id;
    }

    private void deleteProfile(String value, String where) {
        SQLiteDatabase db = mPopMoviesDB.getWritableDatabase();
        Log.i(TAG, "Delete Profile Chamado.");

        try {
            db.delete(PopMoviesContract.ProfileEntry.TABLE_NAME, where, new String[]{value});
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            db.close();
        }
    }

    public void deleteProfileByID(long id) {
        deleteProfile(String.valueOf(id), SQLHelper.ProfileSQL.WHERE_PROFILE_BY_ID);
    }

    public void deleteProfileByUsername(String username) {
        deleteProfile(username, SQLHelper.ProfileSQL.WHERE_PROFILE_BY_USERNAME);
    }

    public ProfileDB findProfileByUserUsername(String username) {
        SQLiteDatabase db = mPopMoviesDB.getWritableDatabase();
        Log.i(TAG, "Find Profile Chamado.");

        try {
            Cursor c = db.query(PopMoviesContract.ProfileEntry.TABLE_NAME, null, SQLHelper.ProfileSQL.WHERE_PROFILE_BY_USERNAME, new String[]{username}, null, null, null);
            if (c.moveToFirst()) {

                return getProfileByCursor(c, username);
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

    public long getTotal(String sql, String[] values) {
        SQLiteDatabase db = mPopMoviesDB.getWritableDatabase();
        Log.i(TAG, "Total Hour Chamado.");
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
            db.close();
        }

        return total;
    }

    public long getTotalHoursWatched(long profileID) {
        return getTotal(SQLHelper.ProfileSQL.SQL_TOTAL_HOURS_WATCHED, new String[]{String.valueOf(profileID)});
    }

    public long getTotalMoviesWached(long profileID) {
        return getTotal(SQLHelper.ProfileSQL.SQL_TOTAL_MOVIES_WATCHED, new String[]{String.valueOf(profileID)});
    }

    public long getTotalMoviesByGenrer(int genreID, String username) {
        return getTotal(SQLHelper.ProfileSQL.SQL_TOTAL_MOVIES_BY_GENRER, new String[]{String.valueOf(genreID), username});
    }

    public ProfileDB getProfileByCursor(Cursor c, String username) {
        ProfileDB profile = new ProfileDB();

        profile.setProfileID(c.getLong(c.getColumnIndex(PopMoviesContract.ProfileEntry._ID)));
        profile.setDescricao(c.getString(c.getColumnIndex(PopMoviesContract.ProfileEntry.COLUMN_DESCRIPTION)));
        profile.setProfileID(c.getInt(c.getColumnIndex(PopMoviesContract.ProfileEntry._ID)));

        profile.setFilmesAssistidos(mMovieRepository.findAllMoviesWatched(profile.getProfileID()));
        profile.setFilmesQueroVer(mMovieRepository.findAllMoviesWantSee(profile.getProfileID()));
        profile.setFilmesFavoritos(mMovieRepository.findAllFavoritesMovies(profile.getProfileID()));

        profile.setTotalHorasAssistidas(getTotalHoursWatched(profile.getProfileID()));
        profile.setUser(mUserRepository.findUserByUsername(username));

        return profile;
    }

    private ContentValues getProfileContentValues(ProfileDB profile) {
        ContentValues values = new ContentValues();

        values.put(PopMoviesContract.ProfileEntry.COLUMN_DESCRIPTION, profile.getDescricao());
        values.put(PopMoviesContract.ProfileEntry.COLUMN_USER_FORER_USERNAME, profile.getUser().getUsername());

        return values;
    }
}
