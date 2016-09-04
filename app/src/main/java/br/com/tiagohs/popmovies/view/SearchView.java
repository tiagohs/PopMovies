package br.com.tiagohs.popmovies.view;

import android.widget.ImageView;

import java.util.List;

import br.com.tiagohs.popmovies.model.movie.Movie;

/**
 * Created by Tiago Henrique on 01/09/2016.
 */
public interface SearchView {

    void onMovieSelected(int movieID, ImageView posterMovie);
    void atualizarView(int currentPage, int totalPages, List<Movie> listMovies);

    void showProgressBar();
    void hideProgressBar();
}
