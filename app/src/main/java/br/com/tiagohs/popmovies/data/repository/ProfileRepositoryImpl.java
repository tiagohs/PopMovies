package br.com.tiagohs.popmovies.data.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.com.tiagohs.popmovies.data.PopMoviesContract;
import br.com.tiagohs.popmovies.data.PopMoviesDB;
import br.com.tiagohs.popmovies.data.SQLHelper;
import br.com.tiagohs.popmovies.model.db.ProfileDB;
import br.com.tiagohs.popmovies.model.dto.GenrerMoviesDTO;
import br.com.tiagohs.popmovies.util.MovieUtils;
import br.com.tiagohs.popmovies.util.PrefsUtils;

public class ProfileRepositoryImpl implements ProfileRepository {
    private static final String TAG = ProfileRepositoryImpl.class.getSimpleName();

    private PopMoviesDB mPopMoviesDB;
    private MovieRepository mMovieRepository;
    private UserRepository mUserRepository;
    private Context mContext;

    public ProfileRepositoryImpl(Context context) {
        this.mPopMoviesDB = new PopMoviesDB(context);
        this.mMovieRepository = new MovieRepositoryImpl(context);
        this.mUserRepository = new UserRepositoryImpl(context);

        this.mContext = context;
    }

    public long saveProfile(ProfileDB profile) {
        SQLiteDatabase db = null;
        long id = 0;

        try {
            ContentValues values = getProfileContentValues(profile);

            boolean userJaExistente = findProfileByUserUsername(profile.getUser().getUsername()) != null;
            db = mPopMoviesDB.getWritableDatabase();

            if (userJaExistente)
                db.update(PopMoviesContract.ProfileEntry.TABLE_NAME, values, SQLHelper.ProfileSQL.WHERE_PROFILE_BY_USERNAME, new String[]{profile.getUser().getUsername()});
            else {
                id = db.insert(PopMoviesContract.ProfileEntry.TABLE_NAME, "", values);
                if (id != -1)
                    profile.setProfileID(id);
            }

            profile.getUser().setProfileID(profile.getProfileID());

            mUserRepository.saveUser(profile.getUser(), mContext);

            PrefsUtils.setCurrentProfile(profile, mContext);
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

    private boolean hasSomeMovie(String sql, String[] values) {
        SQLiteDatabase db = mPopMoviesDB.getWritableDatabase();
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

        return total == 1;
    }

    public boolean hasMoviesWatched(long profileID) {
        return hasSomeMovie(SQLHelper.ProfileSQL.SQL_HAS_MOVIE_WATCHED, new String[]{String.valueOf(profileID)});
    }

    public boolean hasMoviesWantSee(long profileID) {
        return hasSomeMovie(SQLHelper.ProfileSQL.SQL_HAS_MOVIE_WANT_SEE, new String[]{String.valueOf(profileID)});
    }

    public boolean hasMoviesDontWantSee(long profileID) {
        return hasSomeMovie(SQLHelper.ProfileSQL.SQL_HAS_MOVIE_DONT_WANT_SEE, new String[]{String.valueOf(profileID)});
    }

    public boolean hasMoviesFavorite(long profileID) {
        return hasSomeMovie(SQLHelper.ProfileSQL.SQL_HAS_MOVIE_FAVORITE, new String[]{String.valueOf(profileID)});
    }

    public long getTotalHoursWatched(long profileID) {
        return getTotal(SQLHelper.ProfileSQL.SQL_TOTAL_HOURS_WATCHED, new String[]{String.valueOf(profileID)});
    }

    public long getTotalMovies(long profileID) {
        return getTotal(SQLHelper.ProfileSQL.SQL_TOTAL_MOVIES, new String[]{String.valueOf(profileID)});
    }

    public long getTotalMoviesWatched(long profileID) {
        return getTotal(SQLHelper.ProfileSQL.SQL_TOTAL_MOVIES_WATCHED, new String[]{String.valueOf(profileID)});
    }

    public long getTotalMoviesWantSee(long profileID) {
        return getTotal(SQLHelper.ProfileSQL.SQL_TOTAL_MOVIES_WANT_SEE, new String[]{String.valueOf(profileID)});
    }

    public long getTotalMoviesDontWantSee(long profileID) {
        return getTotal(SQLHelper.ProfileSQL.SQL_TOTAL_MOVIES_DONT_WANT_SEE, new String[]{String.valueOf(profileID)});
    }

    public long getTotalFavorites(long profileID) {
        return getTotal(SQLHelper.ProfileSQL.SQL_TOTAL_MOVIES_FAVORITES, new String[]{String.valueOf(profileID)});
    }

    public long getTotalMoviesByGenrer(int genreID, long profileID) {
        return getTotal(SQLHelper.ProfileSQL.SQL_TOTAL_MOVIES_BY_GENRER, new String[]{String.valueOf(genreID), String.valueOf(profileID)});
    }

    public List<GenrerMoviesDTO> getAllGenrersSaved(long profileID) {
        List<GenrerMoviesDTO> genres = new ArrayList<>();
        int[] genrersID = MovieUtils.getAllGenrerIDs();
        int[] namesID = MovieUtils.getAllGenrerNames();

        for (int cont = 0; cont < genrersID.length; cont++) {
            long totalMovies = getTotalMoviesByGenrer(genrersID[cont], profileID);

            if (totalMovies > 0) {
                GenrerMoviesDTO genrerDTO = new GenrerMoviesDTO(genrersID[cont], namesID[cont], totalMovies);

                if (!genres.contains(genrerDTO))
                    genres.add(genrerDTO);
            }

        }

        return genres;
    }

    public ProfileDB getProfileByCursor(Cursor c, String username) {
        ProfileDB profile = new ProfileDB();

        profile.setProfileID(c.getLong(c.getColumnIndex(PopMoviesContract.ProfileEntry._ID)));
        profile.setDescricao(c.getString(c.getColumnIndex(PopMoviesContract.ProfileEntry.COLUMN_DESCRIPTION)));

        profile.setFilmes(mMovieRepository.findAllMoviesDB(profile.getProfileID()));
        profile.setFilmesAssistidos(mMovieRepository.findAllMoviesWatched(profile.getProfileID()));
        profile.setFilmesQueroVer(mMovieRepository.findAllMoviesWantSee(profile.getProfileID()));
        profile.setFilmesNaoQueroVer(mMovieRepository.findAllMoviesDontWantSee(profile.getProfileID()));
        profile.setFilmesFavoritos(mMovieRepository.findAllFavoritesMovies(profile.getProfileID()));

        profile.setTotalHorasAssistidas(getTotalHoursWatched(profile.getProfileID()));
        profile.setUser(mUserRepository.findUserByUsername(username, profile.getProfileID()));

        return profile;
    }

    private ContentValues getProfileContentValues(ProfileDB profile) {
        ContentValues values = new ContentValues();

        values.put(PopMoviesContract.ProfileEntry.COLUMN_DESCRIPTION, profile.getDescricao());
        values.put(PopMoviesContract.ProfileEntry.COLUMN_USER_FORER_USERNAME, profile.getUser().getUsername());

        return values;
    }
}
