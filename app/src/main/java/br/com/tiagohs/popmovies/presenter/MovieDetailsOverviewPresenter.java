package br.com.tiagohs.popmovies.presenter;

import br.com.tiagohs.popmovies.view.MoviesDetailsOverviewView;

public interface MovieDetailsOverviewPresenter extends BasePresenter<MoviesDetailsOverviewView> {

    void getMoviesRankings(String imdbID);
}
