package br.com.tiagohs.popmovies.ui.contracts;

import java.util.List;

import br.com.tiagohs.popmovies.model.movie.Genre;
import br.com.tiagohs.popmovies.ui.presenter.IPresenter;
import br.com.tiagohs.popmovies.ui.view.IView;
import io.reactivex.Observable;

public class GenresContract {

    public interface GenresInterceptor {

        Observable<List<Genre>> getGenres();
    }

    public interface GenresView extends IView {

        void updateView(List<Genre> genres);
    }

    public interface GenresPresenter extends IPresenter<GenresView> {

        void getGenres();
    }
}
