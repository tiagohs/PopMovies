package br.com.tiagohs.popmovies.database.repository;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.com.tiagohs.popmovies.database.DatabaseManager;
import br.com.tiagohs.popmovies.database.PopMoviesContract;
import br.com.tiagohs.popmovies.database.SQLHelper;
import br.com.tiagohs.popmovies.model.db.GenreDB;
import br.com.tiagohs.popmovies.util.EmptyUtils;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.schedulers.Schedulers;

public class GenreRepositoryImpl implements GenreRepository {
    private static final String TAG = GenreRepositoryImpl.class.getSimpleName();

    private DatabaseManager mDatabaseManager;

    @Inject
    public GenreRepositoryImpl(DatabaseManager databaseManager) {
        this.mDatabaseManager = databaseManager;
    }

    public Observable<Long> saveGenres(List<GenreDB> genres, long movieID) {
        Observable<Long> observable = null;

        for (GenreDB genre : genres) {
            observable = saveGenre(genre, movieID)
                        .subscribeOn(Schedulers.io());
        }

        return observable;
    }

    public Observable<Long> saveGenre(final GenreDB genre, final long movieID) {
        return Observable.create(new ObservableOnSubscribe<Long>() {
            @Override
            public void subscribe(ObservableEmitter<Long> subscribe) throws Exception {
                SQLiteDatabase db = mDatabaseManager.openDatabase();

                long genreID = 0;

                try {
                    ContentValues values = getGenresContentValues(genre, movieID);

                    boolean genreJaExistente = EmptyUtils.isNotNull(findDatabase(SQLHelper.GenreSQL.WHERE_USER_BY_GENRE_ID, new String[]{String.valueOf(genre.getGenrerID()), String.valueOf(movieID)}));

                    if (genreJaExistente)
                        db.update(PopMoviesContract.GenreEntry.TABLE_NAME, values, SQLHelper.GenreSQL.WHERE_USER_BY_GENRE_ID, new String[]{String.valueOf(genreID), String.valueOf(movieID)});
                    else
                        genreID = db.insert(PopMoviesContract.GenreEntry.TABLE_NAME, "", values);

                    subscribe.onNext(genreID);
                    subscribe.onComplete();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    subscribe.onError(ex);
                } finally {
                    mDatabaseManager.closeDatabase();
                }

            }
        });
    }

    private Observable<GenreDB> find(final String where, final String[] values) {
        return Observable.create(new ObservableOnSubscribe<GenreDB>() {
            @Override
            public void subscribe(ObservableEmitter<GenreDB> observableEmitter) throws Exception {
                GenreDB genre = findDatabase(where, values);

                if (EmptyUtils.isNotNull(genre))
                    observableEmitter.onNext(genre);

                observableEmitter.onComplete();
            }
        });
    }

    public GenreDB findDatabase(String where, String[] values) {
        SQLiteDatabase db = mDatabaseManager.openDatabase();

        try {
            Cursor c = db.query(PopMoviesContract.GenreEntry.TABLE_NAME, null, where, values, null, null, null);
            if (c.moveToFirst()) {
                return getGenreDBByCursor(c);
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

    public Observable<GenreDB> findGenreByGenreID(int genreID, long movieID) {
        return find(SQLHelper.GenreSQL.WHERE_USER_BY_GENRE_ID, new String[]{String.valueOf(genreID), String.valueOf(movieID)});
    }

    public Observable<List<GenreDB>> findAllGenreDB(final long movieID) {
        return Observable.create(new ObservableOnSubscribe<List<GenreDB>>() {
            @Override
            public void subscribe(ObservableEmitter<List<GenreDB>> observableEmitter) throws Exception {
                observableEmitter.onNext(findAllGenreDBDatabase(movieID));

                observableEmitter.onComplete();
            }
        });
    }

    public List<GenreDB> findAllGenreDBDatabase(long movieID) {
        SQLiteDatabase db = mDatabaseManager.openDatabase();

        try {
            return genreDBCursorToList(db.query(PopMoviesContract.GenreEntry.TABLE_NAME, null, SQLHelper.GenreSQL.WHERE_ALL_GENRE, new String[]{String.valueOf(movieID)}, null, null, null));
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            mDatabaseManager.closeDatabase();
        }

        return null;
    }

    public Observable<List<Integer>> findAllGenreID(final long movieID) {
        return Observable.create(new ObservableOnSubscribe<List<Integer>>() {
            @Override
            public void subscribe(ObservableEmitter<List<Integer>> observableEmitter) throws Exception {
                observableEmitter.onNext(findAllGenreIDDatabase(movieID));

                observableEmitter.onComplete();
            }
        });
    }

    public List<Integer> findAllGenreIDDatabase(long movieID) {
        SQLiteDatabase db = mDatabaseManager.openDatabase();

        List<Integer> genresID = new ArrayList<>();
        Integer id = null;

        try {
            Cursor c = db.query(PopMoviesContract.GenreEntry.TABLE_NAME, null, SQLHelper.GenreSQL.WHERE_ALL_GENRE, new String[]{String.valueOf(movieID)}, null, null, null);

            if (c.moveToFirst()) {
                do {
                    id = c.getInt(c.getColumnIndex(PopMoviesContract.GenreEntry.COLUMN_GENRER_ID));
                    if (id != -1)
                        genresID.add(id);
                } while (c.moveToNext());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            mDatabaseManager.closeDatabase();
        }

        return genresID;
    }

    private List<GenreDB> genreDBCursorToList(Cursor c) {
        List<GenreDB> genres = new ArrayList<>();

        if (c.moveToFirst()) {
            do {
                genres.add(getGenreDBByCursor(c));
            } while (c.moveToNext());
        }

        return genres;
    }

    private GenreDB getGenreDBByCursor(Cursor c) {
        GenreDB genre = new GenreDB();

        genre.setId(c.getInt(c.getColumnIndex(PopMoviesContract.GenreEntry._ID)));
        genre.setGenrerID(c.getInt(c.getColumnIndex(PopMoviesContract.GenreEntry.COLUMN_GENRER_ID)));
        genre.setGenrerName(c.getString(c.getColumnIndex(PopMoviesContract.GenreEntry.COLUMN_GENRER_NAME)));

        return genre;
    }

    private ContentValues getGenresContentValues(GenreDB genre, long movieID) {
        ContentValues values = new ContentValues();

        values.put(PopMoviesContract.GenreEntry.COLUMN_GENRER_ID, genre.getGenrerID());
        values.put(PopMoviesContract.GenreEntry.COLUMN_MOVIE_FORER_ID_SERVER, movieID);
        values.put(PopMoviesContract.GenreEntry.COLUMN_GENRER_NAME, genre.getGenrerName());

        return values;
    }
}
