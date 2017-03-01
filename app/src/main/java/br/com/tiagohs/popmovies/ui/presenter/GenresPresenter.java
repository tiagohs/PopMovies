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

public class GenresPresenter extends BasePresenter<GenresContract.GenresView, GenresContract.GenresInterceptor> implements GenresContract.GenresPresenter {
    private static final String TAG = GenresPresenter.class.getSimpleName();

    @Inject
    public GenresPresenter(GenresInterceptor genresInterceptor, CompositeDisposable subscribes) {
        super(genresInterceptor, subscribes);
    }

    @Override
    public void getGenres() {
        mView.setProgressVisibility(View.VISIBLE);

        if (mView.isInternetConnected()) {
            mInterceptor.getGenres()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(onObserver());
        } else
            mView.onErrorNoConnection();

    }

    private Observer<List<Genre>> onObserver() {
        return new Observer<List<Genre>>() {
            @Override
            public void onSubscribe(Disposable d) {
                mSubscribers.add(d);
            }

            @Override
            public void onNext(List<Genre> genres) {
                mView.updateView(genres);
                mView.setProgressVisibility(View.GONE);
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
