package br.com.tiagohs.popmovies.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public class PopMoviesContract {

    public static final String CONTENT_AUTHORITY = "br.com.tiagohs.popmovies";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

<<<<<<< HEAD
    public static final String PATH_USER = "Usuario";
    public static final String PATH_PROFILE = "Profile";
    public static final String PATH_MOVIE = "Filme";
=======
    public static final String PATH_MOVIE = "movie";
>>>>>>> origin/master

    public final static class MoviesEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIE).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE;

<<<<<<< HEAD
        public static final String TABLE_NAME = "Filme";

        public static final String COLUMN_ID_SERVER = "idServer";
        public static final String COLUMN_POSTER_PATH = "posterPath";
        public static final String COLUMN_TITLE = "titulo";
        public static final String COLUMN_FAVORITE = "favorito";
        public static final String COLUMN_DURATION = "duracao";
        public static final String COLUMN_VOTES = "numVotos";
        public static final String COLUMN_DATE_SAVED = "dataSalvamento";
        public static final String COLUMN_PROFILE_FORER_ID = "profile_ID";
=======
        public static final String TABLE_NAME = "movie";

        public static final String COLUMN_ID_SERVER = "id_server";
        public static final String COLUMN_POSTER_PATH = "poster_path";
        public static final String COLUMN_FAVORITE = "favorite";
        public static final String COLUMN_VOTES = "votes";
>>>>>>> origin/master

        public static Uri buildMovieUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
<<<<<<< HEAD

    public final static class UserEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_USER).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_USER;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_USER;

        public static final String TABLE_NAME = "Usuario";

        public static final String COLUMN_NAME = "nome";
        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_PASSWORD = "senha";

        public static Uri buildUserUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public final static class ProfileEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_PROFILE).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PROFILE;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PROFILE;

        public static final String TABLE_NAME = "Profile";

        public static final String COLUMN_DESCRIPTION = "descricao";
        public static final String COLUMN_FOTO_PATH = "fotoPath";
        public static final String COLUMN_USER_FORER_EMAIL = "user_email";

        public static Uri buildProfileUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
=======
>>>>>>> origin/master
}
