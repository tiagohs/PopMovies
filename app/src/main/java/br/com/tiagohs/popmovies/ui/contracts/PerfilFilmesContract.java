package br.com.tiagohs.popmovies.ui.contracts;

import br.com.tiagohs.popmovies.ui.presenter.IPresenter;
import br.com.tiagohs.popmovies.ui.view.IView;
import io.reactivex.Observable;

public class PerfilFilmesContract {

    public interface PerfilFilmesPresenter extends IPresenter<PerfilFilmesView> {

        void updateMoviesContainers(long profileID);
    }

    public interface PerfilFilmesView extends IView {

        void hasMoviesWantSee(boolean hasMoviesWantSee);
        void hasMoviesDontWantSee(boolean hasMoviesDontWantSee);
        void hasMoviesFavorite(boolean hasMoviesFavorite);
        void hasMoviesWatched(boolean hasMoviesWatched);

    }

    public interface PerfilFilmesInterceptor {

        Observable<Boolean> hasMoviesWatched(long profileID);
        Observable<Boolean> hasMoviesWantSee(long profileID);
        Observable<Boolean> hasMoviesDontWantSee(long profileID);
        Observable<Boolean> hasMoviesFavorite(long profileID);

    }
}
