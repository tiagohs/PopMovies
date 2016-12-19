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
import br.com.tiagohs.popmovies.model.db.MovieDB;
import br.com.tiagohs.popmovies.model.db.ProfileDB;

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

    public long saveProfile(ProfileDB profile) {
        SQLiteDatabase db = null;

        try {
            ContentValues values = getProfileContentValues(profile);

            boolean userJaExistente = findProfileByUserEmail(profile.getUser().getEmail()) != null;
            db = mPopMoviesDB.getWritableDatabase();

            if (userJaExistente)
                return db.update(PopMoviesContract.ProfileEntry.TABLE_NAME, values, PopMoviesContract.ProfileEntry.COLUMN_USER_FORER_EMAIL + "=?", new String[]{profile.getUser().getEmail()});
            else
                return db.insert(PopMoviesContract.ProfileEntry.TABLE_NAME, "", values);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            db.close();
        }

        return 0;
    }

    private void deleteProfile(long id, String where) {
        SQLiteDatabase db = mPopMoviesDB.getWritableDatabase();
        Log.i(TAG, "Delete Profile Chamado.");

        try {
            db.delete(PopMoviesContract.ProfileEntry.TABLE_NAME, where, new String[]{String.valueOf(id)});
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            db.close();
        }
    }

    public void deleteProfileByID(long id) {
        deleteProfile(id, PopMoviesContract.ProfileEntry._ID + "=?");
    }

    public void deleteProfileByEmail(String email) {
        deleteProfile(id, PopMoviesContract.ProfileEntry.COLUMN_USER_FORER_EMAIL + "=?");
    }

    public ProfileDB findProfileByUserEmail(String email) {
        SQLiteDatabase db = mPopMoviesDB.getWritableDatabase();
        Log.i(TAG, "Find Profile Chamado.");

        try {
            Cursor c = db.query(PopMoviesContract.ProfileEntry.TABLE_NAME, null, PopMoviesContract.ProfileEntry.COLUMN_USER_FORER_EMAIL + " = '" + email + "'", null, null, null, null);
            if (c.moveToFirst()) {
                ProfileDB profile = new ProfileDB();
                profile.setProfileID(c.getInt(c.getColumnIndex(PopMoviesContract.ProfileEntry._ID)));
                profile.setDescricao(c.getString(c.getColumnIndex(PopMoviesContract.ProfileEntry.COLUMN_DESCRIPTION)));
                profile.setFotoPath(c.getString(c.getColumnIndex(PopMoviesContract.ProfileEntry.COLUMN_FOTO_PATH)));

                List<MovieDB> filmesAssistidos = mMovieRepository.findAllMoviesDB(c.getInt(c.getColumnIndex(PopMoviesContract.ProfileEntry._ID)));

                profile.setFilmesAssistidos(filmesAssistidos);
                profile.setFilmesFavoritos(findFavoritesMovies(filmesAssistidos));
                profile.setUser(mUserRepository.findUserByEmail(email));

                return profile;
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

    private List<MovieDB> findFavoritesMovies(List<MovieDB> filmesAssistidos) {
        List<MovieDB> filmesFavoritos = new ArrayList<>();

        for (MovieDB filme : filmesAssistidos) {
            if (filme.isFavorite())
                filmesFavoritos.add(filme);
        }

        return filmesFavoritos;
    }

    private ContentValues getProfileContentValues(ProfileDB profile) {
        ContentValues values = new ContentValues();

        values.put(PopMoviesContract.ProfileEntry.COLUMN_DESCRIPTION, profile.getDescricao());
        values.put(PopMoviesContract.ProfileEntry.COLUMN_FOTO_PATH, profile.getFotoPath());
        values.put(PopMoviesContract.ProfileEntry.COLUMN_USER_FORER_EMAIL, profile.getUser().getEmail());

        return values;
    }
}
