package br.com.tiagohs.popmovies.ui.presenter;

import android.view.View;

import java.util.List;

import javax.inject.Inject;

import br.com.tiagohs.popmovies.ui.contracts.GenresContract;
import br.com.tiagohs.popmovies.ui.interceptor.GenresInterceptor;
import br.com.tiagohs.popmovies.model.movie.Genre;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class GenresPresenter implements GenresContract.GenresPresenter {
    private static final String TAG = GenresPresenter.class.getSimpleName();

    private GenresContract.GenresView mGenresView;
    private GenresInterceptor mGenresInterceptor;

    private CompositeDisposable mSubscribers;

    @Inject
    public GenresPresenter(GenresInterceptor genresInterceptor, CompositeDisposable subscribes) {
        mGenresInterceptor = genresInterceptor;

        mSubscribers = subscribes;
    }

    @Override
    public void onBindView(GenresContract.GenresView view) {
        this.mGenresView = view;
    }

    @Override
    public void onUnbindView() {
        mSubscribers.clear();
        mGenresView = null;
    }

    @Override
    public void getGenres() {
        mGenresView.setProgressVisibility(View.VISIBLE);

        if (mGenresView.isInternetConnected()) {
            mGenresInterceptor.getGenres()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(onObserver());
        } else
            mGenresView.onErrorNoConnection();

    }

    private Observer<List<Genre>> onObserver() {
        return new Observer<List<Genre>>() {
            @Override
            public void onSubscribe(Disposable d) {
                mSubscribers.add(d);
            }

            @Override
            public void onNext(List<Genre> genres) {
                mGenresView.updateView(genres);
                mGenresView.setProgressVisibility(View.GONE);
            }

            @Override
            public void onError(Throwable e) {
                mGenresView.onErrorInServer();
            }

            @Override
            public void onComplete() {}
        };
    }

}
