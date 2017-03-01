package br.com.tiagohs.popmovies.ui.presenter;

import javax.inject.Inject;

import br.com.tiagohs.popmovies.ui.contracts.PerfilFilmesContract;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class PerfilFilmesPresenter implements PerfilFilmesContract.PerfilFilmesPresenter {

    private PerfilFilmesContract.PerfilFilmesInterceptor mInterceptor;
    private PerfilFilmesContract.PerfilFilmesView mView;

    private CompositeDisposable mSubscribers;

    @Inject
    public PerfilFilmesPresenter(PerfilFilmesContract.PerfilFilmesInterceptor interceptor, CompositeDisposable subscribers) {
        mInterceptor = interceptor;
        mSubscribers = subscribers;
    }

    @Override
    public void updateMoviesContainers(long profileID) {
        mInterceptor.hasMoviesWatched(profileID)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Boolean>() {
                        @Override
                        public void accept(Boolean hasMoviesWatched) throws Exception {
                            mView.hasMoviesWatched(hasMoviesWatched);
                        }
                    });

        mInterceptor.hasMoviesDontWantSee(profileID)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean hasMoviesDontWantSee) throws Exception {
                        mView.hasMoviesDontWantSee(hasMoviesDontWantSee);
                    }
                });

        mInterceptor.hasMoviesFavorite(profileID)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean hasMoviesFavorite) throws Exception {
                        mView.hasMoviesFavorite(hasMoviesFavorite);
                    }
                });

        mInterceptor.hasMoviesWantSee(profileID)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean hasMoviesWantSee) throws Exception {
                        mView.hasMoviesWantSee(hasMoviesWantSee);
                    }
                });
    }

    @Override
    public void onBindView(PerfilFilmesContract.PerfilFilmesView view) {
        mView = view;
    }

    @Override
    public void onUnbindView() {
        mSubscribers.clear();
        mView = null;
    }
}
