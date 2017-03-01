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

public class VideosPresenter extends BasePresenter<VideosContract.VideosView, VideosContract.VideoInterceptor> implements VideosContract.VideosPresenter {
    private static final String TAG = VideosPresenter.class.getSimpleName();

    private static final int NUM_MAX_VIDEOS_BY_PAGE = 12;
    private static final int NUM_MAX_LANGUAGES = 20;

    private List<List<Video>> mVideos;

    private List<Video> mFinalVideos;

    private int mCurrentPage;
    private int mTotalPages;

    public VideosPresenter(VideosContract.VideoInterceptor videoInterceptor, CompositeDisposable compositeDisposable) {
        super(videoInterceptor, compositeDisposable);

        mCurrentPage = 0;
        mFinalVideos = new ArrayList<>();
    }

    @Override
    public void getVideos(int movieID, List<Translation> translations) {

        if (mView.isInternetConnected()) {
            List<Observable<List<Video>>> observables = new ArrayList<>();
            int numMaxTranslation = translations.size() < NUM_MAX_LANGUAGES ? translations.size() : NUM_MAX_LANGUAGES;

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
                mSubscribers.add(d);
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
                mView.onErrorInServer();
            }

            @Override
            public void onComplete() {
                mVideos = ListUtils.partition(mFinalVideos, NUM_MAX_VIDEOS_BY_PAGE);
                mTotalPages = mVideos.size();

                if (mTotalPages > 0)
                    mView.onUpdateUI(mVideos.get(mCurrentPage++), mCurrentPage < mTotalPages);
                else
                    mView.onUpdateUI(mFinalVideos, mCurrentPage < mTotalPages);
            }
        };
    }

    public void getVideosByPage() {
        mView.onUpdateUI(mVideos.get(mCurrentPage++), mCurrentPage < mTotalPages);
    }

}
