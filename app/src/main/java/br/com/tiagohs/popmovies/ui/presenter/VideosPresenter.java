package br.com.tiagohs.popmovies.ui.presenter;

import org.apache.commons.collections4.ListUtils;

import java.util.ArrayList;
import java.util.List;

import br.com.tiagohs.popmovies.ui.contracts.VideosContract;
import br.com.tiagohs.popmovies.model.media.Translation;
import br.com.tiagohs.popmovies.model.media.Video;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class VideosPresenter implements VideosContract.VideosPresenter {
    private static final String TAG = VideosPresenter.class.getSimpleName();

    private VideosContract.VideosView mVideosView;
    private VideosContract.VideoInterceptor mInterceptor;

    private CompositeDisposable mSubscribes;

    private List<List<Video>> mVideos;

    private List<Video> mFinalVideos;

    private int mCurrentPage;
    private int mTotalPages;

    public VideosPresenter(VideosContract.VideoInterceptor videoInterceptor, CompositeDisposable compositeDisposable) {
        mCurrentPage = 0;
        mFinalVideos = new ArrayList<>();

        mInterceptor = videoInterceptor;
        mSubscribes = compositeDisposable;
    }

    @Override
    public void getVideos(int movieID, List<Translation> translations) {

        if (mVideosView.isInternetConnected()) {
            List<Observable<List<Video>>> observables = new ArrayList<>();
            int numMaxTranslation = translations.size() < 20 ? translations.size() : 20;

            for (int cont = 0; cont < numMaxTranslation; cont++) {
                observables.add(mInterceptor.getMovieVideos(movieID, translations.get(cont).getLanguage() + "-" + translations.get(cont).getCountry()));
            }

            Observable.fromIterable(observables)
                    .subscribeOn(Schedulers.computation())
                    .flatMap(new Function<Observable<List<Video>>, ObservableSource<List<Video>>>() {
                        @Override
                        public ObservableSource<List<Video>> apply(Observable<List<Video>> videosResponseObservable) throws Exception {
                            return videosResponseObservable.observeOn(AndroidSchedulers.mainThread());
                        }
                    })
                    .subscribe(onObserverVideos());
        } else {
            noConnectionError();
        }

    }

    private Observer<List<Video>> onObserverVideos() {
        return new Observer<List<Video>>() {
            @Override
            public void onSubscribe(Disposable d) {
                mSubscribes.add(d);
            }

            @Override
            public void onNext(List<Video> videos) {

                synchronized (VideosPresenter.class) {
                    mFinalVideos.addAll(videos);
                }

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                mVideosView.onErrorInServer();
            }

            @Override
            public void onComplete() {
                mVideos = ListUtils.partition(mFinalVideos, 12);
                mTotalPages = mVideos.size();

                if (mTotalPages > 0)
                    mVideosView.onUpdateUI(mVideos.get(mCurrentPage++), mCurrentPage < mTotalPages);
                else
                    mVideosView.onUpdateUI(mFinalVideos, mCurrentPage < mTotalPages);
            }
        };
    }

    private void noConnectionError() {
        if (mVideosView.isAdded()) {
            mVideosView.onErrorNoConnection();
        }
    }

    @Override
    public void onBindView(VideosContract.VideosView view) {
        mVideosView = view;
    }

    @Override
    public void onUnbindView() {
        mSubscribes.clear();
        mVideosView = null;
    }

    public void getVideosByPage() {
        mVideosView.onUpdateUI(mVideos.get(mCurrentPage++), mCurrentPage < mTotalPages);
    }

}
