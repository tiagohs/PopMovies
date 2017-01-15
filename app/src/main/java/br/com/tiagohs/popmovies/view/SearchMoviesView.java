package br.com.tiagohs.popmovies.view;

import android.widget.ImageView;

import java.util.List;

import br.com.tiagohs.popmovies.model.movie.Movie;

/**
 * Created by Tiago on 14/01/2017.
 */

public interface SearchMoviesView extends BaseView {

    void onMovieSelected(int movieID, ImageView posterMovie);

    void setListMovies(List<Movie> listMovies, boolean hasMorePages);
    void addAllMovies(List<Movie> listMovies, boolean hasMorePages);

    void setNenhumFilmeEncontradoVisibility(int visibility);
    void setupRecyclerView();
    void updateAdapter();
}
