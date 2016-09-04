package br.com.tiagohs.popmovies.presenter;

import br.com.tiagohs.popmovies.view.GenresView;

public interface GenresPresenter extends BasePresenter<GenresView> {

    void getGenres();
}
