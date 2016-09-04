package br.com.tiagohs.popmovies.view;

import java.util.List;

import br.com.tiagohs.popmovies.model.movie.Movie;


public interface ListMovieView {

    void hideDialogProgress();
    void showDialogProgress();
    void atualizarView(int currentPage, int totalPages, List<Movie> listMovies);
    void onError(String msg);
}
