package br.com.tiagohs.popmovies.database;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Tiago on 28/02/2017.
 */

public class DatabaseManager {

    private int mOpenCounter;

    private static DatabaseManager instance;
    private static PopMoviesDB mDatabaseHelper;
    private SQLiteDatabase mDatabase;

    public static synchronized void initializeInstance(PopMoviesDB helper) {
        if (instance == null) {
            instance = new DatabaseManager();
            mDatabaseHelper = helper;
        }
    }

    public static synchronized DatabaseManager getInstance() {
        if (instance == null) {
            throw new IllegalStateException(DatabaseManager.class.getSimpleName() +
                    " is not initialized, call initializeInstance(..) method first.");
        }

        return instance;
    }

    public synchronized SQLiteDatabase openDatabase() {
        mOpenCounter++;
        if(mOpenCounter == 1) {
            mDatabase = mDatabaseHelper.getWritableDatabase();
        }
        return mDatabase;
    }

    public synchronized void closeDatabase() {
        mOpenCounter--;
        if(mOpenCounter == 0) {
            mDatabase.close();

        }
    }
}
