package br.com.tiagohs.popmovies.database;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public class PopMoviesContract {

    public static final String CONTENT_AUTHORITY = "br.com.tiagohs.popmovies";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_USER = "Usuario";
    public static final String PATH_PROFILE = "Profile";
    public static final String PATH_MOVIE = "Filme";


    public final static class MoviesEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIE).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE;

        public static final String TABLE_NAME = "Filme";

        public static final String COLUMN_ID_SERVER = "id_server";
        public static final String COLUMN_POSTER_PATH = "poster_path";
        public static final String COLUMN_STATUS = "status";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_FAVORITE = "favorite";
        public static final String COLUMN_RELEASE_DATE = "release_date";
        public static final String COLUMN_RELEASE_YEAR = "release_year";
        public static final String COLUMN_RUNTIME = "runtime";
        public static final String COLUMN_VOTES = "num_votes";
        public static final String COLUMN_DATE_SAVED = "date_saved";
        public static final String COLUMN_PROFILE_FORER_ID = "profile_ID";

        public static Uri buildMovieUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public final static class GenreEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIE).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE;

        public static final String TABLE_NAME = "Genrer";

        public static final String COLUMN_GENRER_ID = "genrer_id";
        public static final String COLUMN_GENRER_NAME = "genrer_name";
        public static final String COLUMN_MOVIE_FORER_ID_SERVER = "movie_id_server";

        public static Uri buildGenrerUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public final static class UserEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_USER).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_USER;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_USER;

        public static final String TABLE_NAME = "Usuario";

        public static final String COLUMN_NAME = "nome";
        public static final String COLUMN_PICTURE_PATH = "fotoPath";
        public static final String COLUMN_PICTURE_LOCAL_PATH = "picture_local";
        public static final String COLUMN_PICTURE_TYPE = "picture_type";
        public static final String COLUMN_TOKEN = "userToken";
        public static final String COLUMN_TYPE_LOGIN = "typeLogin";
        public static final String COLUMN_USERNAME = "username";
        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_PASSWORD = "password";
        public static final String COLUMN_PROFILE_ID = "profile_id";

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

        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_BIRTHDAY = "birthday";
        public static final String COLUMN_COUNTRY = "country";
        public static final String COLUMN_GENRER = "genrer";
        public static final String COLUMN_USER_FORER_USERNAME = "user_username";

        public static Uri buildProfileUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

}
