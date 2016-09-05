package br.com.tiagohs.popmovies.view;

import java.util.List;

import br.com.tiagohs.popmovies.model.dto.MovieListDTO;

/**
 * Created by Tiago Henrique on 03/09/2016.
 */
public interface MoviesByGenreView {

    void atualizarView(int currentPage, int totalPages, List<MovieListDTO> listMovies);
    void onError(String msg);

    void showProgressBar();
    void hideProgressBar();
}
