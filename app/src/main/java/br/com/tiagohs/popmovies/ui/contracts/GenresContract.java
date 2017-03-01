package br.com.tiagohs.popmovies.ui.contracts;

import java.util.List;

import br.com.tiagohs.popmovies.model.movie.Genre;
import br.com.tiagohs.popmovies.ui.presenter.BasePresenter;
import br.com.tiagohs.popmovies.ui.view.BaseView;
import io.reactivex.Observable;

/**
 * Created by Tiago on 25/02/2017.
 */

public class GenresContract {

    public interface GenresInterceptor {

        Observable<List<Genre>> getGenres();
    }

    public interface GenresView extends BaseView {

        void updateView(List<Genre> genres);
    }

    public interface GenresPresenter extends BasePresenter<GenresView> {

        void getGenres();
    }
}
