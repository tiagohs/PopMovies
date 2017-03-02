package br.com.tiagohs.popmovies.database;

import br.com.tiagohs.popmovies.model.db.MovieDB;

public class SQLHelper {

    public static final String SQL_CREATE_MOVIE_TABLE = "CREATE TABLE IF NOT EXISTS " + PopMoviesContract.MoviesEntry.TABLE_NAME + " (" +
            PopMoviesContract.MoviesEntry.COLUMN_PROFILE_FORER_ID + " INTEGER NOT NULL, " +
            PopMoviesContract.MoviesEntry._ID + " INTEGER UNIQUE PRIMARY KEY," +
            PopMoviesContract.MoviesEntry.COLUMN_ID_SERVER + " INTEGER NOT NULL, " +
            PopMoviesContract.MoviesEntry.COLUMN_TITLE + " TEXT, " +
            PopMoviesContract.MoviesEntry.COLUMN_STATUS + " TEXT, " +
            PopMoviesContract.MoviesEntry.COLUMN_FAVORITE + " TEXT, " +
            PopMoviesContract.MoviesEntry.COLUMN_RELEASE_DATE + " TEXT, " +
            PopMoviesContract.MoviesEntry.COLUMN_RELEASE_YEAR + " TEXT, " +
            PopMoviesContract.MoviesEntry.COLUMN_RUNTIME + " INTEGER, " +
            PopMoviesContract.MoviesEntry.COLUMN_VOTES + " DECIMAL, " +
            PopMoviesContract.MoviesEntry.COLUMN_POSTER_PATH + " TEXT NOT NULL, " +
            PopMoviesContract.MoviesEntry.COLUMN_DATE_SAVED + " DATETIME NOT NULL, " +
            "CONSTRAINT `fk_Filme_Profile1` " +
            "FOREIGN KEY (`" + PopMoviesContract.MoviesEntry.COLUMN_PROFILE_FORER_ID + "`)" +
            "REFERENCES `" + PopMoviesContract.ProfileEntry.TABLE_NAME + "` (`" + PopMoviesContract.ProfileEntry._ID + "`)" +
            "ON DELETE NO ACTION ON UPDATE NO ACTION );";

    public static final String SQL_CREATE_GENRER_TABLE = "CREATE TABLE IF NOT EXISTS " + PopMoviesContract.GenreEntry.TABLE_NAME + " (" +
            PopMoviesContract.GenreEntry.COLUMN_MOVIE_FORER_ID_SERVER + " TEXT NOT NULL, " +
            PopMoviesContract.GenreEntry._ID + " INTEGER PRIMARY KEY, " +
            PopMoviesContract.GenreEntry.COLUMN_GENRER_ID + " INTEGER NOT NULL, " +
            PopMoviesContract.GenreEntry.COLUMN_GENRER_NAME + " TEXT, " +
            "CONSTRAINT `fk_Genrer_Movie` " +
            "FOREIGN KEY (`" + PopMoviesContract.GenreEntry.COLUMN_MOVIE_FORER_ID_SERVER + "`)" +
            "REFERENCES `" + PopMoviesContract.MoviesEntry.TABLE_NAME + "` (`" + PopMoviesContract.GenreEntry._ID + "`)" +
            " ON DELETE NO ACTION ON UPDATE NO ACTION );";

    public static final String SQL_CREATE_USER_TABLE = "CREATE TABLE IF NOT EXISTS " + PopMoviesContract.UserEntry.TABLE_NAME + " (" +
            PopMoviesContract.UserEntry._ID + " INTEGER PRIMARY KEY," +
            PopMoviesContract.UserEntry.COLUMN_NAME + " TEXT, " +
            PopMoviesContract.UserEntry.COLUMN_TOKEN + " TEXT, " +
            PopMoviesContract.UserEntry.COLUMN_TYPE_LOGIN + " TEXT, " +
            PopMoviesContract.UserEntry.COLUMN_USERNAME + " TEXT UNIQUE, " +
            PopMoviesContract.UserEntry.COLUMN_PICTURE_PATH + " TEXT, " +
            PopMoviesContract.UserEntry.COLUMN_PICTURE_TYPE + " INTEGER, " +
            PopMoviesContract.UserEntry.COLUMN_PICTURE_LOCAL_PATH + " TEXT, " +
            PopMoviesContract.UserEntry.COLUMN_PROFILE_ID + " INTEGER, " +
            PopMoviesContract.UserEntry.COLUMN_EMAIL + " TEXT UNIQUE, " +
            PopMoviesContract.UserEntry.COLUMN_PASSWORD + " TEXT );";

    public static final String SQL_CREATE_PROFILE_TABLE = "CREATE TABLE IF NOT EXISTS " + PopMoviesContract.ProfileEntry.TABLE_NAME + " (" +
            PopMoviesContract.ProfileEntry.COLUMN_USER_FORER_USERNAME + " TEXT NOT NULL, " +
            PopMoviesContract.ProfileEntry._ID + " INTEGER PRIMARY KEY," +
            PopMoviesContract.ProfileEntry.COLUMN_DESCRIPTION + " TEXT, " +
            PopMoviesContract.ProfileEntry.COLUMN_BIRTHDAY + " TEXT, " +
            PopMoviesContract.ProfileEntry.COLUMN_COUNTRY + " TEXT, " +
            PopMoviesContract.ProfileEntry.COLUMN_GENRER + " TEXT, " +
            "CONSTRAINT `fk_Profile_Usuario` " +
            "FOREIGN KEY (`" + PopMoviesContract.ProfileEntry.COLUMN_USER_FORER_USERNAME + "`)" +
            "REFERENCES `" + PopMoviesContract.UserEntry.TABLE_NAME + "` (`" + PopMoviesContract.UserEntry._ID + "`)" +
            " ON DELETE NO ACTION ON UPDATE NO ACTION );";

    public static final String SQL_DELETE_USER_TABLE = "DROP TABLE IF EXISTS " + PopMoviesContract.UserEntry.TABLE_NAME;
    public static final String SQL_DELETE_PROFILE_TABLE = "DROP TABLE IF EXISTS " + PopMoviesContract.ProfileEntry.TABLE_NAME;
    public static final String SQL_DELETE_MOVIE_TABLE = "DROP TABLE IF EXISTS " + PopMoviesContract.MoviesEntry.TABLE_NAME;
    public static final String SQL_DELETE_GENRE_TABLE = "DROP TABLE IF EXISTS " + PopMoviesContract.GenreEntry.TABLE_NAME;

    public static class MovieSQL {
        public static final String WHERE_MOVIE_BY_SERVER_ID = PopMoviesContract.MoviesEntry.COLUMN_ID_SERVER + " = ? AND " +
                PopMoviesContract.MoviesEntry.COLUMN_PROFILE_FORER_ID + " = ?";

        public static final String WHERE_MOVIE_BY_ID = PopMoviesContract.MoviesEntry._ID + " = ? AND " +
                PopMoviesContract.MoviesEntry.COLUMN_PROFILE_FORER_ID + " = ?";

        public static final String WHERE_ALL_MOVIE = PopMoviesContract.MoviesEntry.COLUMN_PROFILE_FORER_ID + " = ?";

        public static final String WHERE_ALL_MOVIES_WATCHED = WHERE_ALL_MOVIE +
                " AND " + PopMoviesContract.MoviesEntry.COLUMN_STATUS + " = " + MovieDB.STATUS_WATCHED;

        public static final String WHERE_ALL_MOVIE_WANT_SEE = WHERE_ALL_MOVIE +
                " AND " + PopMoviesContract.MoviesEntry.COLUMN_STATUS + " = " + MovieDB.STATUS_WANT_SEE;

        public static final String WHERE_ALL_MOVIE_DONT_WANT_SEE = WHERE_ALL_MOVIE +
                " AND " + PopMoviesContract.MoviesEntry.COLUMN_STATUS + " = " + MovieDB.STATUS_DONT_WANT_SEE;

        public static final String WHERE_ALL_FAVORITE_MOVIE = WHERE_ALL_MOVIE +
                " AND " + PopMoviesContract.MoviesEntry.COLUMN_FAVORITE + " = 1";

        public static final String WHERE_IS_FAVORITE_MOVIE = WHERE_MOVIE_BY_SERVER_ID +
                " AND " + PopMoviesContract.MoviesEntry.COLUMN_FAVORITE + " = 1";

        public static final String WHERE_IS_WATCHED_MOVIE = WHERE_MOVIE_BY_SERVER_ID +
                " AND " + PopMoviesContract.MoviesEntry.COLUMN_STATUS + " = " + MovieDB.STATUS_WATCHED;

        public static final String WHERE_IS_WANT_SEE_MOVIE = WHERE_MOVIE_BY_SERVER_ID +
                " AND " + PopMoviesContract.MoviesEntry.COLUMN_STATUS + " = " + MovieDB.STATUS_WANT_SEE;

        public static final String WHERE_IS_DONT_WANT_SEE_MOVIE = WHERE_MOVIE_BY_SERVER_ID +
                " AND " + PopMoviesContract.MoviesEntry.COLUMN_STATUS + " = " + MovieDB.STATUS_DONT_WANT_SEE;

        public static final String SELECT_ALL_MOVIES_WITH_PAGES = "SELECT * FROM " + PopMoviesContract.MoviesEntry.TABLE_NAME +
                " WHERE " + WHERE_ALL_MOVIE + " LIMIT ?,? ";

        public static final String SELECT_ALL_MOVIES_WATCHED_WITH_PAGES = "SELECT * FROM " + PopMoviesContract.MoviesEntry.TABLE_NAME +
                " WHERE " + WHERE_ALL_MOVIES_WATCHED + " LIMIT ?,? ";

        public static final String SELECT_ALL_MOVIES_WANT_SEE_WITH_PAGES = "SELECT * FROM " + PopMoviesContract.MoviesEntry.TABLE_NAME +
                " WHERE " + WHERE_ALL_MOVIE_WANT_SEE + " LIMIT ?,? ";

        public static final String SELECT_ALL_MOVIES_DONT_WANT_SEE_WITH_PAGES = "SELECT * FROM " + PopMoviesContract.MoviesEntry.TABLE_NAME +
                " WHERE " + WHERE_ALL_MOVIE_DONT_WANT_SEE + " LIMIT ?,? ";

        public static final String SELECT_ALL_MOVIES_FAVORITE_WITH_PAGES = "SELECT * FROM " + PopMoviesContract.MoviesEntry.TABLE_NAME +
                " WHERE " + WHERE_ALL_FAVORITE_MOVIE + " LIMIT ?,? ";

        public static final String SORT_BY_DATE_DESC = "SELECT * FROM " + PopMoviesContract.MoviesEntry.TABLE_NAME +
                " WHERE + " + WHERE_ALL_MOVIE + " ORDER BY DATETIME(" +
                PopMoviesContract.MoviesEntry.COLUMN_DATE_SAVED + ") DESC LIMIT 1";

        public static final String SORT_BY_DATE_ASC = "SELECT * FROM " + PopMoviesContract.MoviesEntry.TABLE_NAME +
                " WHERE + " + WHERE_ALL_MOVIE + " ORDER BY DATETIME(" +
                PopMoviesContract.MoviesEntry.COLUMN_DATE_SAVED + ") ASC LIMIT 1";

        //select * from tbl_name where Attribute_name between 'yyyy-mm-dd' and 'yyyy-mm-dd';
        public static final String SORT_BETWEEN_TWO_DATES = "SELECT * FROM " + PopMoviesContract.MoviesEntry.TABLE_NAME +
                " WHERE + " + WHERE_ALL_MOVIE + " AND " +
                PopMoviesContract.MoviesEntry.COLUMN_RELEASE_DATE + "  between '?' and '?'";

        public static final String SORT_BY_YEAR_DESC = "SELECT * FROM " + PopMoviesContract.MoviesEntry.TABLE_NAME +
                " WHERE + " + WHERE_ALL_MOVIE + " ORDER BY " +
                PopMoviesContract.MoviesEntry.COLUMN_RELEASE_YEAR + ") DESC LIMIT 1";

        public static final String SORT_BY_YEAR_ASC = "SELECT * FROM " + PopMoviesContract.MoviesEntry.TABLE_NAME +
                " WHERE + " + WHERE_ALL_MOVIE + " ORDER BY " +
                PopMoviesContract.MoviesEntry.COLUMN_RELEASE_YEAR + ") ASC LIMIT 1";
    }

    public static class GenreSQL {
        public static final String WHERE_USER_BY_GENRE_ID = PopMoviesContract.GenreEntry.COLUMN_GENRER_ID + " = ? AND "
                                                          + PopMoviesContract.GenreEntry.COLUMN_MOVIE_FORER_ID_SERVER + " = ?";

        public static final String WHERE_ALL_GENRE = PopMoviesContract.GenreEntry.COLUMN_MOVIE_FORER_ID_SERVER + " = ?";
    }

    public static class UserSQL {
        public static final String WHERE_USER_BY_USERNAME = PopMoviesContract.UserEntry.COLUMN_USERNAME + " = ? AND " +
                                                            PopMoviesContract.UserEntry.COLUMN_PROFILE_ID + " = ? ";

        public static final String WHERE_USER_BY_ID = PopMoviesContract.UserEntry._ID + " = ?";

    }

    public static class ProfileSQL {
        public static final String WHERE_PROFILE_BY_USERNAME = PopMoviesContract.ProfileEntry.COLUMN_USER_FORER_USERNAME + " = ?";

        public static final String WHERE_PROFILE_BY_ID = PopMoviesContract.ProfileEntry._ID + " = ?";

        public static final String SQL_TOTAL_HOURS_WATCHED = "SELECT SUM(" + PopMoviesContract.MoviesEntry.COLUMN_RUNTIME + ") FROM " +
                PopMoviesContract.MoviesEntry.TABLE_NAME + " WHERE " + MovieSQL.WHERE_ALL_MOVIE;

        public static final String SQL_TOTAL_MOVIES = "SELECT COUNT(*) FROM " + PopMoviesContract.MoviesEntry.TABLE_NAME +
                " WHERE " + MovieSQL.WHERE_ALL_MOVIE;

        public static final String SQL_TOTAL_MOVIES_WATCHED = "SELECT COUNT(*) FROM " + PopMoviesContract.MoviesEntry.TABLE_NAME +
                " WHERE " + MovieSQL.WHERE_ALL_MOVIES_WATCHED;

        public static final String SQL_TOTAL_MOVIES_FAVORITES = "SELECT COUNT(*) FROM " + PopMoviesContract.MoviesEntry.TABLE_NAME +
                " WHERE " + MovieSQL.WHERE_ALL_FAVORITE_MOVIE;

        public static final String SQL_TOTAL_MOVIES_WANT_SEE = "SELECT COUNT(*) FROM " + PopMoviesContract.MoviesEntry.TABLE_NAME +
                " WHERE " + MovieSQL.WHERE_ALL_MOVIE_WANT_SEE;

        public static final String SQL_TOTAL_MOVIES_DONT_WANT_SEE = "SELECT COUNT(*) FROM " + PopMoviesContract.MoviesEntry.TABLE_NAME +
                " WHERE " + MovieSQL.WHERE_ALL_MOVIE_DONT_WANT_SEE;

        public static final String SQL_TOTAL_MOVIES_BY_GENRER = "SELECT COUNT(*) "
                + "FROM " + PopMoviesContract.MoviesEntry.TABLE_NAME + " m " +
                "LEFT JOIN " + PopMoviesContract.GenreEntry.TABLE_NAME + " g " +
                "ON " + "m." + PopMoviesContract.MoviesEntry.COLUMN_ID_SERVER + " = g." + PopMoviesContract.GenreEntry.COLUMN_MOVIE_FORER_ID_SERVER +
                " WHERE g."	+ PopMoviesContract.GenreEntry.COLUMN_GENRER_ID + " = ? " +
                "AND m." + PopMoviesContract.MoviesEntry.COLUMN_PROFILE_FORER_ID + " = ? " +
                " AND m." + PopMoviesContract.MoviesEntry.COLUMN_STATUS + " = " + MovieDB.STATUS_WATCHED;

        public static final String SQL_HAS_MOVIE_WATCHED = "SELECT 1 WHERE EXISTS (SELECT * FROM " + PopMoviesContract.MoviesEntry.TABLE_NAME +
                                                            " WHERE " + MovieSQL.WHERE_ALL_MOVIES_WATCHED + ")";

        public static final String SQL_HAS_MOVIE_WANT_SEE = "SELECT 1 WHERE EXISTS (SELECT * FROM " + PopMoviesContract.MoviesEntry.TABLE_NAME +
                " WHERE " + MovieSQL.WHERE_ALL_MOVIE_WANT_SEE + ")";

        public static final String SQL_HAS_MOVIE_DONT_WANT_SEE = "SELECT 1 WHERE EXISTS (SELECT * FROM " + PopMoviesContract.MoviesEntry.TABLE_NAME +
                " WHERE " + MovieSQL.WHERE_ALL_MOVIE_DONT_WANT_SEE + ")";

        public static final String SQL_HAS_MOVIE_FAVORITE = "SELECT 1 WHERE EXISTS (SELECT * FROM " + PopMoviesContract.MoviesEntry.TABLE_NAME +
                " WHERE " + MovieSQL.WHERE_ALL_FAVORITE_MOVIE + ")";
    }
}
