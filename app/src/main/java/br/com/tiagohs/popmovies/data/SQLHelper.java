package br.com.tiagohs.popmovies.data;

public class SQLHelper {

    public static final String SQL_CREATE_MOVIE_TABLE = "CREATE TABLE IF NOT EXISTS " + PopMoviesContract.MoviesEntry.TABLE_NAME + " (" +
            PopMoviesContract.MoviesEntry.COLUMN_PROFILE_FORER_ID + " INTEGER NOT NULL, " +
            PopMoviesContract.MoviesEntry._ID + " INTEGER PRIMARY KEY," +
            PopMoviesContract.MoviesEntry.COLUMN_ID_SERVER + " INTEGER NOT NULL, " +
            PopMoviesContract.MoviesEntry.COLUMN_TITLE + " TEXT, " +
            PopMoviesContract.MoviesEntry.COLUMN_FAVORITE + " TEXT, " +
            PopMoviesContract.MoviesEntry.COLUMN_DURATION + " INTEGER, " +
            PopMoviesContract.MoviesEntry.COLUMN_VOTES + " DECIMAL, " +
            PopMoviesContract.MoviesEntry.COLUMN_POSTER_PATH + " TEXT NOT NULL, " +
            PopMoviesContract.MoviesEntry.COLUMN_DATE_SAVED + " DATETIME NOT NULL, " +
            "CONSTRAINT `fk_Filme_Profile1` " +
            "FOREIGN KEY (`" + PopMoviesContract.MoviesEntry.COLUMN_PROFILE_FORER_ID + "`)" +
            "REFERENCES `" + PopMoviesContract.ProfileEntry.TABLE_NAME + "` (`" + PopMoviesContract.ProfileEntry._ID + "`)" +
            "ON DELETE NO ACTION ON UPDATE NO ACTION );";

    public static final String SQL_CREATE_USER_TABLE = "CREATE TABLE IF NOT EXISTS " + PopMoviesContract.UserEntry.TABLE_NAME + " (" +
            PopMoviesContract.UserEntry._ID + " INTEGER PRIMARY KEY," +
            PopMoviesContract.UserEntry.COLUMN_NAME + " TEXT, " +
            PopMoviesContract.UserEntry.COLUMN_EMAIL + " TEXT UNIQUE NOT NULL, " +
            PopMoviesContract.UserEntry.COLUMN_PASSWORD + " TEXT " +
            ");";

    public static final String SQL_CREATE_PROFILE_TABLE = "CREATE TABLE IF NOT EXISTS " + PopMoviesContract.ProfileEntry.TABLE_NAME + " (" +
            PopMoviesContract.ProfileEntry.COLUMN_USER_FORER_EMAIL + " TEXT NOT NULL, " +
            PopMoviesContract.ProfileEntry._ID + " INTEGER PRIMARY KEY," +
            PopMoviesContract.ProfileEntry.COLUMN_DESCRIPTION + " TEXT, " +
            PopMoviesContract.ProfileEntry.COLUMN_FOTO_PATH + " TEXT, " +
            "CONSTRAINT `fk_Profile_Usuario` " +
            "FOREIGN KEY (`" + PopMoviesContract.ProfileEntry.COLUMN_USER_FORER_EMAIL + "`)" +
            "REFERENCES `" + PopMoviesContract.UserEntry.TABLE_NAME + "` (`" + PopMoviesContract.UserEntry._ID + "`)" +
            " ON DELETE NO ACTION ON UPDATE NO ACTION );";
}
