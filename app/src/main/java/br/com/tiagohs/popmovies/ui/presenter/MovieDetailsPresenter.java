package br.com.tiagohs.popmovies.ui.presenter;

import android.view.View;

import java.util.Calendar;

import br.com.tiagohs.popmovies.model.db.MovieDB;
import br.com.tiagohs.popmovies.model.movie.MovieDetails;
import br.com.tiagohs.popmovies.model.response.VideosResponse;
import br.com.tiagohs.popmovies.ui.contracts.MovieDetailsContract;
import br.com.tiagohs.popmovies.util.DTOUtils;
import br.com.tiagohs.popmovies.util.DateUtils;
import br.com.tiagohs.popmovies.util.MovieUtils;
import br.com.tiagohs.popmovies.util.enumerations.SubMethod;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class MovieDetailsPresenter extends BasePresenter<MovieDetailsContract.MovieDetailsView, MovieDetailsContract.MovieDetailsInterceptor> implements MovieDetailsContract.MovieDetailsPresenter {
    private static final String TAG = MovieDetailsPresenter.class.getSimpleName();


    private long mProfileID;

    private String[] mMovieParameters = new String[]{SubMethod.CREDITS.getValue(), SubMethod.RELEASE_DATES.getValue(),
            SubMethod.SIMILAR.getValue(), SubMethod.KEYWORDS.getValue(),
            SubMethod.VIDEOS.getValue(), SubMethod.TRANSLATIONS.getValue()};

    public MovieDetailsPresenter(MovieDetailsContract.MovieDetailsInterceptor movieDetailsInterceptor, CompositeDisposable compositeDisposable) {
        super(movieDetailsInterceptor, compositeDisposable);
    }

    public void setProfileID(long profileID) {
        this.mProfileID = profileID;
    }

    @Override
    public void getMovieDetails(int movieID) {
        mView.setProgressVisibility(View.VISIBLE);

        if (mView.isInternetConnected()) {
            mInterceptor.getMovieDetails(movieID, mMovieParameters)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(onMovieDetailsObserver());

            mView.setTabsVisibility(View.GONE);
        } else
            noConnectionError();

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
                e.printStackTrace();
                mView.onErrorInServer();
            }

            @Override
            public void onComplete() {}
        };
    }

    public void onMovieDetailsRequestSucess(final MovieDetails movieDetails) {

        if (mView.isAdded()) {
            if (movieDetails.getVideos().isEmpty())
                getVideos(movieDetails);
            try {
                mSubscribers.add(mInterceptor.isFavoriteMovie(mProfileID, movieDetails.getId())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(onObserverFavorite(movieDetails)));

                mSubscribers.add(mInterceptor.isWachedMovie(mProfileID, movieDetails.getId())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(onObserverWached()));

                mSubscribers.add(mInterceptor.isWantSeeMovie(mProfileID, movieDetails.getId())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(onObserverUsersOption(movieDetails, MovieDB.STATUS_WANT_SEE)));

                mSubscribers.add(mInterceptor.isWantSeeMovie(mProfileID, movieDetails.getId())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(onObserverUsersOption(movieDetails, MovieDB.STATUS_DONT_WANT_SEE)));
            } catch (Exception ex) {}

            if (movieDetails.getRuntime() == 0)
                mView.setDuracaoMovieVisibility(View.GONE);

            mView.setupDirectorsRecyclerView(DTOUtils.createDirectorsItemsListDTO(movieDetails.getCrew()));
            mView.updateUI(movieDetails);
            mView.setupTabs();
            mView.setProgressVisibility(View.GONE);
            mView.setTabsVisibility(View.VISIBLE);
        }

    }

    private Consumer<Boolean> onObserverFavorite(final MovieDetails movieDetails) {
        return new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                movieDetails.setFavorite(aBoolean);
            }
        };
    }

    private Consumer<Boolean> onObserverWached() {
        return new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                if (aBoolean)
                    mView.setJaAssistido();
            }
        };
    }

    private Consumer<Boolean> onObserverUsersOption(final MovieDetails movieDetails, final int status) {
        return new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                if (aBoolean)
                    movieDetails.setStatusDB(status);
            }
        };
    }

    @Override
    public void onClickJaAssisti(MovieDetails movie, boolean buttonStage) {

        if (buttonStage) {
            mSubscribers.add(mInterceptor.saveMovie(new MovieDB(movie.getId(), MovieDB.STATUS_WATCHED, movie.getRuntime(), movie.getPosterPath(),
                                    movie.getTitle(), movie.isFavorite(), movie.getVoteCount(), mProfileID,
                                    Calendar.getInstance(), DateUtils.formateStringToCalendar(movie.getReleaseDate()),
                                    DateUtils.getYearByDate(movie.getReleaseDate()), MovieUtils.genreToGenreDB(movie.getGenres())))
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe());

        } else {
            mSubscribers.add(mInterceptor.deleteMovieByServerID(movie.getId(), mProfileID)
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe());
        }

    }

    public void getVideos(MovieDetails movie) {

        if (mView.isInternetConnected()) {
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
                    mView.updateVideos(videosResponse);
                else
                    mView.setPlayButtonVisibility(View.GONE);
            }

            @Override
            public void onError(Throwable e) {
                mView.onErrorInServer();
            }

            @Override
            public void onComplete() {}
        };
    }
}
