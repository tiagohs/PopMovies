package br.com.tiagohs.popmovies.database.repository;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import br.com.tiagohs.popmovies.database.DatabaseManager;
import br.com.tiagohs.popmovies.database.PopMoviesContract;
import br.com.tiagohs.popmovies.database.SQLHelper;
import br.com.tiagohs.popmovies.model.db.MovieDB;
import br.com.tiagohs.popmovies.model.movie.Movie;
import br.com.tiagohs.popmovies.util.EmptyUtils;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class MovieRepositoryImpl implements MovieRepository  {
    private static final String TAG = MovieRepositoryImpl.class.getSimpleName();
    private static final int MAX_MOVIES_BY_PAGE = 12;

    private GenreRepository mGenerRepository;
    private SimpleDateFormat mDateFormat;

    private DatabaseManager mDatabaseManager;

    @Inject
    public MovieRepositoryImpl(GenreRepository generRepository, DatabaseManager databaseManager) {
        this.mGenerRepository = generRepository;
        this.mDatabaseManager = databaseManager;

        mDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    public Observable<Long> saveMovie(final MovieDB movie) {

        return Observable.create(new ObservableOnSubscribe<Long>() {
            @Override
            public void subscribe(ObservableEmitter<Long> subscriber) throws Exception {
                SQLiteDatabase db = null;
                long movieID = 0;

                try {
                    ContentValues values = getMoviesContentValues(movie);

                    Movie movieDatabase = findMovieDatabase(SQLHelper.MovieSQL.WHERE_MOVIE_BY_SERVER_ID,
                                                            new String[]{String.valueOf(movie.getIdServer()), String.valueOf(movie.getProfileID())});

                    db = mDatabaseManager.openDatabase();

                    if (EmptyUtils.isNotNull(movieDatabase)) {
                        db.update(PopMoviesContract.MoviesEntry.TABLE_NAME, values, SQLHelper.MovieSQL.WHERE_MOVIE_BY_SERVER_ID, new String[]{String.valueOf(movie.getIdServer()), String.valueOf(movie.getProfileID())});
                    } else {
                        movieID = db.insert(PopMoviesContract.MoviesEntry.TABLE_NAME, "", values);
                    }

                    subscriber.onNext(movieID);
                    subscriber.onComplete();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    subscriber.onError(ex);
                } finally {
                    mDatabaseManager.closeDatabase();
                }
            }
        }).flatMap(new Function<Long, ObservableSource<Long>>() {
            @Override
            public ObservableSource<Long> apply(Long aLong) throws Exception {
                return mGenerRepository.saveGenres(movie.getGenres(), movie.getIdServer());
            }
        });

    }

    private Observable<Integer> deleteMovie(final long id, final long profileID, final String where) {

        return Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> subscriber) throws Exception {
                SQLiteDatabase db = mDatabaseManager.openDatabase();

                try {
                    int idReturn = db.delete(PopMoviesContract.MoviesEntry.TABLE_NAME, where, new String[]{String.valueOf(id), String.valueOf(profileID)});

                    subscriber.onNext(idReturn);
                    subscriber.onComplete();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    subscriber.onError(ex);
                } finally {
                    mDatabaseManager.closeDatabase();
                }
            }
        }).subscribeOn(Schedulers.io());

    }

    public Observable<Integer> deleteMovieByID(long id, long profileID) {
        return deleteMovie(id, profileID, SQLHelper.MovieSQL.WHERE_MOVIE_BY_ID);
    }

    public Observable<Integer> deleteMovieByServerID(long serverID, long profileID) {
        return deleteMovie(serverID, profileID, SQLHelper.MovieSQL.WHERE_MOVIE_BY_SERVER_ID);
    }

    private Observable<Movie> findMovie(final String where, final String[] values) {
        return Observable.create(new ObservableOnSubscribe<Movie>() {
            @Override
            public void subscribe(ObservableEmitter<Movie> observableEmitter) {
                Movie movie = findMovieDatabase(where, values);

                if (EmptyUtils.isNotNull(movie))
                    observableEmitter.onNext(movie);

                observableEmitter.onComplete();
            }
        });
    }

    private Movie findMovieDatabase(final String where, final String[] values) {
        SQLiteDatabase db = mDatabaseManager.openDatabase();

        try {
            Cursor c = db.query(PopMoviesContract.MoviesEntry.TABLE_NAME, null, where, values, null, null, null);
            if (c.moveToFirst()) {
                return getMovieByCursor(c);
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

    public Observable<Movie> findMovieByServerID(int serverID, long profileID) {
        return findMovie(SQLHelper.MovieSQL.WHERE_MOVIE_BY_SERVER_ID, new String[]{String.valueOf(serverID), String.valueOf(profileID)});
    }

    public Observable<Boolean> isFavoriteMovie(long profileID, int serverID) {

        return findMovie(SQLHelper.MovieSQL.WHERE_IS_FAVORITE_MOVIE, new String[]{String.valueOf(serverID), String.valueOf(profileID)})
                        .map(new Function<Movie, Boolean>() {
                            @Override
                            public Boolean apply(Movie movie) throws Exception {
                                return EmptyUtils.isNotNull(movie);
                            }
                        });
    }

    public Observable<Boolean> isWantSeeMovie(long profileID, int serverID) {
        return findMovie(SQLHelper.MovieSQL.WHERE_IS_WANT_SEE_MOVIE, new String[]{String.valueOf(serverID), String.valueOf(profileID)})
                .map(new Function<Movie, Boolean>() {
                    @Override
                    public Boolean apply(Movie movie) throws Exception {
                        return EmptyUtils.isNotNull(movie);
                    }
                });
    }

    public Observable<Boolean> isWachedMovie(long profileID, int serverID) {
        return findMovie(SQLHelper.MovieSQL.WHERE_IS_WATCHED_MOVIE, new String[]{String.valueOf(serverID), String.valueOf(profileID)})
                .map(new Function<Movie, Boolean>() {
                    @Override
                    public Boolean apply(Movie movie) throws Exception {
                        return EmptyUtils.isNotNull(movie);
                    }
                });
    }

    public Observable<Boolean> isDontWantSeeMovie(long profileID, int serverID) {
        return findMovie(SQLHelper.MovieSQL.WHERE_IS_DONT_WANT_SEE_MOVIE, new String[]{String.valueOf(serverID), String.valueOf(profileID)})
                .map(new Function<Movie, Boolean>() {
                    @Override
                    public Boolean apply(Movie movie) throws Exception {
                        return EmptyUtils.isNotNull(movie);
                    }
                });
    }

    public List<Movie> findAllMovies(long profileID) {
        SQLiteDatabase db = mDatabaseManager.openDatabase();

        try {
            return movieCursorToList(db.query(PopMoviesContract.MoviesEntry.TABLE_NAME, null, SQLHelper.MovieSQL.WHERE_ALL_MOVIE, new String[]{String.valueOf(profileID)}, null, null, null));
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            mDatabaseManager.closeDatabase();
        }

        return null;
    }

    private Observable<List<MovieDB>> findAll(final String[] values, final String where) {
        return Observable.create(new ObservableOnSubscribe<List<MovieDB>>() {
            @Override
            public void subscribe(ObservableEmitter<List<MovieDB>> observableEmitter) {
                observableEmitter.onNext(findAllDatabase(values, where));
                observableEmitter.onComplete();
            }
        });
    }

    private List<MovieDB> findAllDatabase(final String[] values, final String where) {
        SQLiteDatabase db = mDatabaseManager.openDatabase();

        try {
            return movieDBCursorToList(db.query(PopMoviesContract.MoviesEntry.TABLE_NAME, null, where, values, null, null, null));
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            mDatabaseManager.closeDatabase();
        }

        return null;
    }

    public Observable<List<MovieDB>> findAllByPage(final String[] values, final String where) {
        return Observable.create(new ObservableOnSubscribe<List<MovieDB>>() {
            @Override
            public void subscribe(ObservableEmitter<List<MovieDB>> observableEmitter) {
                observableEmitter.onNext(findAllByPageDatabase(values, where));
                observableEmitter.onComplete();
            }
        });
    }

    private List<MovieDB> findAllByPageDatabase(final String[] values, final String where) {
        SQLiteDatabase db = mDatabaseManager.openDatabase();

        try {
            return movieDBCursorToList(db.rawQuery(where, values));
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            mDatabaseManager.closeDatabase();
        }

        return new ArrayList<>();
    }

    public Observable<List<MovieDB>> findAllMoviesDB(long profileID, int page) {
        return findAllByPage(new String[]{String.valueOf(profileID), String.valueOf((page - 1) * MAX_MOVIES_BY_PAGE), String.valueOf(MAX_MOVIES_BY_PAGE)}, SQLHelper.MovieSQL.SELECT_ALL_MOVIES_WITH_PAGES);
    }

    public Observable<List<MovieDB>> findAllMoviesDB(long profileID) {
        return findAll(new String[]{String.valueOf(profileID)}, SQLHelper.MovieSQL.WHERE_ALL_MOVIE);
    }

    public Observable<List<MovieDB>> findAllMoviesWatched(long profileID, int page) {
        return findAllByPage(new String[]{String.valueOf(profileID), String.valueOf((page - 1) * MAX_MOVIES_BY_PAGE), String.valueOf(MAX_MOVIES_BY_PAGE)}, SQLHelper.MovieSQL.SELECT_ALL_MOVIES_WATCHED_WITH_PAGES);
    }

    public Observable<List<MovieDB>> findAllMoviesWatched(long profileID) {
        return findAll(new String[]{String.valueOf(profileID)}, SQLHelper.MovieSQL.WHERE_ALL_MOVIES_WATCHED);
    }

    public Observable<List<MovieDB>> findAllMoviesWantSee(long profileID, int page) {
        return findAllByPage(new String[]{String.valueOf(profileID), String.valueOf((page - 1) * MAX_MOVIES_BY_PAGE), String.valueOf(MAX_MOVIES_BY_PAGE)}, SQLHelper.MovieSQL.SELECT_ALL_MOVIES_WANT_SEE_WITH_PAGES);
    }

    public Observable<List<MovieDB>> findAllMoviesWantSee(long profileID) {
        return findAll(new String[]{String.valueOf(profileID)}, SQLHelper.MovieSQL.WHERE_ALL_MOVIE_WANT_SEE);
    }

    public Observable<List<MovieDB>> findAllMoviesDontWantSee(long profileID, int page) {
        return findAllByPage(new String[]{String.valueOf(profileID), String.valueOf((page - 1) * MAX_MOVIES_BY_PAGE), String.valueOf(MAX_MOVIES_BY_PAGE)}, SQLHelper.MovieSQL.SELECT_ALL_MOVIES_DONT_WANT_SEE_WITH_PAGES);
    }

    public Observable<List<MovieDB>> findAllMoviesDontWantSee(long profileID) {
        return findAll(new String[]{String.valueOf(profileID)}, SQLHelper.MovieSQL.WHERE_ALL_MOVIE_DONT_WANT_SEE);
    }

    public Observable<List<MovieDB>> findAllFavoritesMovies(long profileID, int page) {
        return findAllByPage(new String[]{String.valueOf(profileID), String.valueOf((page - 1) * MAX_MOVIES_BY_PAGE), String.valueOf(MAX_MOVIES_BY_PAGE)}, SQLHelper.MovieSQL.SELECT_ALL_MOVIES_FAVORITE_WITH_PAGES);
    }

    public Observable<List<MovieDB>> findAllFavoritesMovies(long profileID) {
        return findAll(new String[]{String.valueOf(profileID)}, SQLHelper.MovieSQL.WHERE_ALL_FAVORITE_MOVIE);
    }

    private List<MovieDB> movieDBCursorToList(Cursor c) {
        List<MovieDB> movies = new ArrayList<>();

        if (c.moveToFirst()) {
            do {
                movies.add(getMovieDBByCursor(c));
            } while (c.moveToNext());
        }

        return movies;
    }

    private Movie getMovieByCursor(Cursor c) {
        Movie movie = new Movie();

        movie.setId(c.getInt(c.getColumnIndex(PopMoviesContract.MoviesEntry.COLUMN_ID_SERVER)));
        movie.setPosterPath(c.getString(c.getColumnIndex(PopMoviesContract.MoviesEntry.COLUMN_POSTER_PATH)));
        movie.setFavorite(!c.getString(c.getColumnIndex(PopMoviesContract.MoviesEntry.COLUMN_FAVORITE)).equals("0"));
        movie.setTitle(c.getString(c.getColumnIndex(PopMoviesContract.MoviesEntry.COLUMN_TITLE)));
        movie.setStatusDB(c.getInt(c.getColumnIndex(PopMoviesContract.MoviesEntry.COLUMN_STATUS)));
        movie.setVoteAverage(c.getString(c.getColumnIndex(PopMoviesContract.MoviesEntry.COLUMN_VOTES)));
        movie.setReleaseDate(c.getString(c.getColumnIndex(PopMoviesContract.MoviesEntry.COLUMN_RELEASE_DATE)));
        movie.setGenreIDs(mGenerRepository.findAllGenreIDDatabase(movie.getId()));

        return movie;
    }

    private MovieDB getMovieDBByCursor(Cursor c) {
        MovieDB movie = new MovieDB();

        try {
            movie.setId(c.getInt(c.getColumnIndex(PopMoviesContract.MoviesEntry._ID)));
            movie.setIdServer(c.getInt(c.getColumnIndex(PopMoviesContract.MoviesEntry.COLUMN_ID_SERVER)));
            movie.setPosterPath(c.getString(c.getColumnIndex(PopMoviesContract.MoviesEntry.COLUMN_POSTER_PATH)));
            movie.setStatus(c.getInt(c.getColumnIndex(PopMoviesContract.MoviesEntry.COLUMN_STATUS)));
            movie.setFavorite(!c.getString(c.getColumnIndex(PopMoviesContract.MoviesEntry.COLUMN_FAVORITE)).equals("0"));
            movie.setTitle(c.getString(c.getColumnIndex(PopMoviesContract.MoviesEntry.COLUMN_TITLE)));
            movie.setVote(c.getDouble(c.getColumnIndex(PopMoviesContract.MoviesEntry.COLUMN_VOTES)));
            movie.setRuntime(c.getInt(c.getColumnIndex(PopMoviesContract.MoviesEntry.COLUMN_RUNTIME)));
            movie.setProfileID(c.getInt(c.getColumnIndex(PopMoviesContract.MoviesEntry.COLUMN_PROFILE_FORER_ID)));

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(mDateFormat.parse(c.getString(c.getColumnIndex(PopMoviesContract.MoviesEntry.COLUMN_DATE_SAVED))));
            movie.setDateSaved(calendar);

            calendar.setTime(mDateFormat.parse(c.getString(c.getColumnIndex(PopMoviesContract.MoviesEntry.COLUMN_RELEASE_DATE))));
            movie.setReleaseDate(calendar);

            movie.setReleaseYear(c.getInt(c.getColumnIndex(PopMoviesContract.MoviesEntry.COLUMN_RELEASE_YEAR)));

            movie.setGenres(mGenerRepository.findAllGenreDBDatabase(movie.getIdServer()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return movie;
    }


    private List<Movie> movieCursorToList(Cursor c) {
        List<Movie> movies = new ArrayList<>();

        if (c.moveToFirst()) {
            do {
                movies.add(getMovieByCursor(c));
            } while (c.moveToNext());
        }

        return movies;
    }

    private ContentValues getMoviesContentValues(MovieDB movie) {
        ContentValues values = new ContentValues();

        values.put(PopMoviesContract.MoviesEntry.COLUMN_ID_SERVER, movie.getIdServer());
        values.put(PopMoviesContract.MoviesEntry.COLUMN_POSTER_PATH, movie.getPosterPath());
        values.put(PopMoviesContract.MoviesEntry.COLUMN_STATUS, movie.getStatus());
        values.put(PopMoviesContract.MoviesEntry.COLUMN_VOTES, movie.getVote());
        values.put(PopMoviesContract.MoviesEntry.COLUMN_TITLE, movie.getTitle());
        values.put(PopMoviesContract.MoviesEntry.COLUMN_RELEASE_DATE, mDateFormat.format(movie.getReleaseDate().getTime()));
        values.put(PopMoviesContract.MoviesEntry.COLUMN_RELEASE_YEAR, movie.getReleaseYear());
        values.put(PopMoviesContract.MoviesEntry.COLUMN_FAVORITE, movie.isFavorite());
        values.put(PopMoviesContract.MoviesEntry.COLUMN_DATE_SAVED, mDateFormat.format(movie.getDateSaved().getTime()));
        values.put(PopMoviesContract.MoviesEntry.COLUMN_PROFILE_FORER_ID, movie.getProfileID());
        values.put(PopMoviesContract.MoviesEntry.COLUMN_RUNTIME, movie.getRuntime());

        return values;
    }

}
