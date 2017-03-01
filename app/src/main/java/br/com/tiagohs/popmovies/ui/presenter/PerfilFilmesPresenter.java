package br.com.tiagohs.popmovies.ui.presenter;

import javax.inject.Inject;

import br.com.tiagohs.popmovies.ui.contracts.PerfilFilmesContract;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class PerfilFilmesPresenter extends BasePresenter<PerfilFilmesContract.PerfilFilmesView, PerfilFilmesContract.PerfilFilmesInterceptor> implements PerfilFilmesContract.PerfilFilmesPresenter {

    @Inject
    public PerfilFilmesPresenter(PerfilFilmesContract.PerfilFilmesInterceptor interceptor, CompositeDisposable subscribers) {
        super(interceptor, subscribers);
    }

    @Override
    public void updateMoviesContainers(long profileID) {

        try {
            mSubscribers.add(mInterceptor.hasMoviesWatched(profileID)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Boolean>() {
                        @Override
                        public void accept(Boolean hasMoviesWatched) throws Exception {
                            mView.hasMoviesWatched(hasMoviesWatched);
                        }
                    }));

            mSubscribers.add(mInterceptor.hasMoviesDontWantSee(profileID)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Boolean>() {
                        @Override
                        public void accept(Boolean hasMoviesDontWantSee) throws Exception {
                            mView.hasMoviesDontWantSee(hasMoviesDontWantSee);
                        }
                    }));

            mSubscribers.add(mInterceptor.hasMoviesFavorite(profileID)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Boolean>() {
                        @Override
                        public void accept(Boolean hasMoviesFavorite) throws Exception {
                            mView.hasMoviesFavorite(hasMoviesFavorite);
                        }
                    }));

            mSubscribers.add(mInterceptor.hasMoviesWantSee(profileID)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Boolean>() {
                        @Override
                        public void accept(Boolean hasMoviesWantSee) throws Exception {
                            mView.hasMoviesWantSee(hasMoviesWantSee);
                        }
                    }));
        } catch (Exception ex) {

        }
    }

}
