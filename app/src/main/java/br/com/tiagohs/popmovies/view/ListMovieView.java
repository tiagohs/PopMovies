package br.com.tiagohs.popmovies.view;

import java.util.List;

import br.com.tiagohs.popmovies.model.Movie.Movie;


public interface ListMovieView {

    void hideProgress();
    void showProgress();
    void atualizarView(int currentPage, int totalPages, List<Movie> listMovies);
    void onError(String msg);
}
