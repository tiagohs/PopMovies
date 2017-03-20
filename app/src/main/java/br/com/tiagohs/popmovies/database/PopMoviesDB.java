package br.com.tiagohs.popmovies.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PopMoviesDB extends SQLiteOpenHelper {
    private static final String TAG = PopMoviesDB.class.getSimpleName();

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "popmovies.db";

    public PopMoviesDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQLHelper.SQL_CREATE_USER_TABLE);
        sqLiteDatabase.execSQL(SQLHelper.SQL_CREATE_PROFILE_TABLE);
        sqLiteDatabase.execSQL(SQLHelper.SQL_CREATE_MOVIE_TABLE);
        sqLiteDatabase.execSQL(SQLHelper.SQL_CREATE_GENRER_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQLHelper.SQL_DELETE_USER_TABLE);
        sqLiteDatabase.execSQL(SQLHelper.SQL_DELETE_PROFILE_TABLE);
        sqLiteDatabase.execSQL(SQLHelper.SQL_DELETE_MOVIE_TABLE);
        sqLiteDatabase.execSQL(SQLHelper.SQL_DELETE_GENRE_TABLE);

        onCreate(sqLiteDatabase);

    }

}
