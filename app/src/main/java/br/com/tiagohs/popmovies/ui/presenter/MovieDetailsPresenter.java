package br.com.tiagohs.popmovies.ui.presenter;

import android.view.View;

import java.util.Calendar;

import br.com.tiagohs.popmovies.ui.contracts.MovieDetailsContract;
import br.com.tiagohs.popmovies.model.db.MovieDB;
import br.com.tiagohs.popmovies.model.movie.MovieDetails;
import br.com.tiagohs.popmovies.model.response.VideosResponse;
import br.com.tiagohs.popmovies.util.DTOUtils;
import br.com.tiagohs.popmovies.util.MovieUtils;
import br.com.tiagohs.popmovies.util.ViewUtils;
import br.com.tiagohs.popmovies.util.enumerations.SubMethod;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;

public class MovieDetailsPresenter implements MovieDetailsContract.MovieDetailsPresenter {
    private static final String TAG = MovieDetailsPresenter.class.getSimpleName();

    private final String US_LOCALE = "en-US";

    private MovieDetailsContract.MovieDetailsView mMovieDetailsView;
    private MovieDetailsContract.MovieDetailsInterceptor mInterceptor;

    private CompositeDisposable mSubscribers;

    private long mProfileID;

    private String[] mMovieParameters = new String[]{SubMethod.CREDITS.getValue(), SubMethod.RELEASE_DATES.getValue(),
            SubMethod.SIMILAR.getValue(), SubMethod.KEYWORDS.getValue(),
            SubMethod.VIDEOS.getValue(), SubMethod.TRANSLATIONS.getValue()};

    public MovieDetailsPresenter(MovieDetailsContract.MovieDetailsInterceptor movieDetailsInterceptor, CompositeDisposable compositeDisposable) {
        mInterceptor = movieDetailsInterceptor;
        mSubscribers = compositeDisposable;
    }

    @Override
    public void onBindView(MovieDetailsContract.MovieDetailsView view) {
        this.mMovieDetailsView = view;
    }

    @Override
    public void onUnbindView() {
        mSubscribers.clear();
        mMovieDetailsView = null;
    }

    public void setProfileID(long profileID) {
        this.mProfileID = profileID;
    }

    @Override
    public void getMovieDetails(int movieID) {
        mMovieDetailsView.setProgressVisibility(View.VISIBLE);

        Observable<MovieDetails> mFirst = mInterceptor.getMovieDetails(movieID, mMovieParameters);
        Observable<MovieDetails> mSecond = mInterceptor.getMovieDetails(movieID, mMovieParameters, US_LOCALE);

        if (mMovieDetailsView.isInternetConnected()) {
            Observable.zip(mFirst, mSecond, onCheckEmptyValuesMovieDetails())
                      .observeOn(AndroidSchedulers.mainThread())
                      .subscribe(onMovieDetailsObserver());

            mMovieDetailsView.setTabsVisibility(View.GONE);
        } else
            noConnectionError();

    }

    private BiFunction<MovieDetails, MovieDetails, MovieDetails> onCheckEmptyValuesMovieDetails() {
        return new BiFunction<MovieDetails, MovieDetails, MovieDetails>() {
            @Override
            public MovieDetails apply(MovieDetails movieDetails, MovieDetails movieDetails2) throws Exception {

                if (ViewUtils.isEmptyValue(movieDetails.getOverview()))
                    movieDetails.setOverview(movieDetails2.getOverview());

                if (movieDetails.getRuntime() == 0)
                    movieDetails.setRuntime(movieDetails2.getRuntime());

                return movieDetails;
            }
        };
    }

    private Observer<MovieDetails> onMovieDetailsObserver() {
        return new Observer<MovieDetails>() {
            @Override
            public void onSubscribe(Disposable d) {
                mSubscribers.add(d);
            }

            @Override
            public void onNext(MovieDetails value) {
                onMovieDetailsRequestSucess(value);
            }

            @Override
            public void onError(Throwable e) {
                mMovieDetailsView.onErrorInServer();
            }

            @Override
            public void onComplete() {}
        };
    }

    public void onMovieDetailsRequestSucess(final MovieDetails movieDetails) {

        if (mMovieDetailsView.isAdded()) {
            if (movieDetails.getVideos().isEmpty())
                getVideos(movieDetails);

            mInterceptor.isFavoriteMovie(mProfileID, movieDetails.getId())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(onObserverFavorite(movieDetails));

            mInterceptor.isWachedMovie(mProfileID, movieDetails.getId())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(onObserverWached());

            mInterceptor.isWantSeeMovie(mProfileID, movieDetails.getId())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(onObserverUsersOption(movieDetails, MovieDB.STATUS_WANT_SEE));

            mInterceptor.isWantSeeMovie(mProfileID, movieDetails.getId())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(onObserverUsersOption(movieDetails, MovieDB.STATUS_DONT_WANT_SEE));

            if (movieDetails.getRuntime() == 0)
                mMovieDetailsView.setDuracaoMovieVisibility(View.GONE);

            mMovieDetailsView.setupDirectorsRecyclerView(DTOUtils.createDirectorsItemsListDTO(movieDetails.getCrew()));
            mMovieDetailsView.updateUI(movieDetails);
            mMovieDetailsView.setupTabs();
            mMovieDetailsView.setProgressVisibility(View.GONE);
            mMovieDetailsView.setTabsVisibility(View.VISIBLE);
        }

    }

    private Observer<Boolean> onObserverFavorite(final MovieDetails movieDetails) {
        return new Observer<Boolean>() {
            @Override
            public void onSubscribe(Disposable d) {
                mSubscribers.add(d);
            }

            @Override
            public void onNext(Boolean value) {
                movieDetails.setFavorite(value);
            }

            @Override
            public void onError(Throwable e) {}

            @Override
            public void onComplete() {}
        };
    }

    private Observer<Boolean> onObserverWached() {
        return new Observer<Boolean>() {
            @Override
            public void onSubscribe(Disposable d) {
                mSubscribers.add(d);
            }

            @Override
            public void onNext(Boolean value) {
                if (value)
                    mMovieDetailsView.setJaAssistido();
            }

            @Override
            public void onError(Throwable e) {}

            @Override
            public void onComplete() {}
        };
    }

    private Observer<Boolean> onObserverUsersOption(final MovieDetails movieDetails, final int status) {
        return new Observer<Boolean>() {
            @Override
            public void onSubscribe(Disposable d) {
                mSubscribers.add(d);
            }

            @Override
            public void onNext(Boolean value) {
                if (value)
                    movieDetails.setStatusDB(status);
            }

            @Override
            public void onError(Throwable e) {}

            @Override
            public void onComplete() {}
        };
    }

    @Override
    public void onClickJaAssisti(MovieDetails movie, boolean buttonStage) {

        if (buttonStage) {
            mSubscribers.add(mInterceptor.saveMovie(new MovieDB(movie.getId(), MovieDB.STATUS_WATCHED, movie.getRuntime(), movie.getPosterPath(),
                                    movie.getTitle(), movie.isFavorite(), movie.getVoteCount(), mProfileID,
                                    Calendar.getInstance(), MovieUtils.formateStringToCalendar(movie.getReleaseDate()),
                                    MovieUtils.getYearByDate(movie.getReleaseDate()), MovieUtils.genreToGenreDB(movie.getGenres())))
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe());

        } else {
            mSubscribers.add(mInterceptor.deleteMovieByServerID(movie.getId(), mProfileID)
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe());
        }

    }

    private void noConnectionError() {
        if (mMovieDetailsView.isAdded()) {
            mMovieDetailsView.onErrorNoConnection();
            mMovieDetailsView.setProgressVisibility(View.GONE);
        }

    }

    public void getVideos(MovieDetails movie) {

        if (mMovieDetailsView.isInternetConnected()) {
            mInterceptor.getMovieVideos(movie.getId(), movie.getOriginalLanguage())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(onVideosObserver());
        } else
            noConnectionError();

    }

    private Observer<VideosResponse> onVideosObserver() {
        return new Observer<VideosResponse>() {
            @Override
            public void onSubscribe(Disposable d) {
                mSubscribers.add(d);
            }

            @Override
            public void onNext(VideosResponse videosResponse) {
                if (!videosResponse.getVideos().isEmpty())
                    mMovieDetailsView.updateVideos(videosResponse);
                else
                    mMovieDetailsView.setPlayButtonVisibility(View.GONE);
            }

            @Override
            public void onError(Throwable e) {
                mMovieDetailsView.onErrorInServer();
            }

            @Override
            public void onComplete() {}
        };
    }
}
