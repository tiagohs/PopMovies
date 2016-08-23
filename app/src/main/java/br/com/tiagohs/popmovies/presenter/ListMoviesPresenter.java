package br.com.tiagohs.popmovies.presenter;

import br.com.tiagohs.popmovies.view.ListMovieView;

/**
 * Created by Tiago Henrique on 20/08/2016.
 */
public interface ListMoviesPresenter {

    void setView(ListMovieView view);
    void getMovies(int currentPage);
}
