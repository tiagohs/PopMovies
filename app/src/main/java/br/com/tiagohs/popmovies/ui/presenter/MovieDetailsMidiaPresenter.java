package br.com.tiagohs.popmovies.ui.presenter;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

import br.com.tiagohs.popmovies.ui.contracts.MovieDetailsMidiaContract;
import br.com.tiagohs.popmovies.model.media.Video;
import br.com.tiagohs.popmovies.model.response.ImageResponse;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;

public class MovieDetailsMidiaPresenter implements MovieDetailsMidiaContract.MovieDetailsMidiaPresenter {
    private final String US_LOCALE = "en-US";

    private MovieDetailsMidiaContract.MovieDetailsMidiaView mView;
    private MovieDetailsMidiaContract.MovieDetailsMidiaInterceptor mInterceptor;

    private List<Video> mFinalVideosReponse;
    private String mLanguageDefault;

    private CompositeDisposable mSubscribers;

    public MovieDetailsMidiaPresenter(MovieDetailsMidiaContract.MovieDetailsMidiaInterceptor interceptor, CompositeDisposable subscribers) {
        mInterceptor = interceptor;
        mSubscribers = subscribers;
    }

    @Override
    public void getVideos(int movieID, String language) {
        mLanguageDefault = language;
        mFinalVideosReponse = new ArrayList<>();

        mView.setVideosProgressVisibility(View.VISIBLE);

        Observable<List<Video>> mVideosCurrentLanguage = mInterceptor.getMovieVideos(movieID, language);
        Observable<List<Video>> mVideosOriginalLanguage = mInterceptor.getMovieVideos(movieID, US_LOCALE);

        if (mView.isInternetConnected()) {
            Observable.zip(mVideosCurrentLanguage, mVideosOriginalLanguage, new BiFunction<List<Video>, List<Video>, List<Video>>() {
                                @Override
                                public List<Video> apply(List<Video> videos, List<Video> videos2) throws Exception {
                                    videos.addAll(videos2);
                                    return videos;
                                }
                            })
                      .observeOn(AndroidSchedulers.mainThread())
                      .subscribe(onVideoObserver());
        } else
            noConnectionError();

    }

    private Observer<List<Video>> onVideoObserver() {
        return new Observer<List<Video>>() {
            @Override
            public void onSubscribe(Disposable d) {
                mSubscribers.add(d);
            }

            @Override
            public void onNext(List<Video> videos) {
                mView.setVideosSearched(true);
                mView.updateVideoUI(mFinalVideosReponse);
                mView.setVideosProgressVisibility(View.GONE);
            }

            @Override
            public void onError(Throwable e) {
                mView.onErrorGetVideos();
            }

            @Override
            public void onComplete() {}
        };
    }

    private void noConnectionError() {
        if (mView.isAdded())
            mView.onErrorNoConnection();

        mView.setProgressVisibility(View.GONE);
        mView.setVideosProgressVisibility(View.GONE);
        mView.setImagesProgressVisibility(View.GONE);
    }

    @Override
    public void getImagens(int movieID) {
        mView.setImagesProgressVisibility(View.VISIBLE);

        if (mView.isInternetConnected()) {
            mInterceptor.getMovieImagens(movieID)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(onImageObserver());
        } else
            noConnectionError();

    }

    private Observer<ImageResponse> onImageObserver() {
        return new Observer<ImageResponse>() {
            @Override
            public void onSubscribe(Disposable d) {
                mSubscribers.add(d);
            }

            @Override
            public void onNext(ImageResponse imageResponse) {
                mView.updateImageUI(imageResponse);
                mView.setImagesProgressVisibility(View.GONE);
            }

            @Override
            public void onError(Throwable e) {
                mView.onErrorGetImages();
            }

            @Override
            public void onComplete() {

            }
        };
    }

    @Override
    public void onBindView(MovieDetailsMidiaContract.MovieDetailsMidiaView view) {
        this.mView = view;
    }

    @Override
    public void onUnbindView() {
        mSubscribers.clear();
        mView = null;
    }

}
