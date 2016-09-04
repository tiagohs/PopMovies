package br.com.tiagohs.popmovies.presenter;

import br.com.tiagohs.popmovies.view.MoviesByGenreView;

public interface MoviesByGenrePresenter extends BasePresenter<MoviesByGenreView> {

    void getMoviesByGenre(int genreID, int currentPage);
}
