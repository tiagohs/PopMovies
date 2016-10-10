package br.com.tiagohs.popmovies.view;

import android.widget.ImageView;

import java.util.List;

import br.com.tiagohs.popmovies.model.movie.Movie;

/**
 * Created by Tiago Henrique on 01/09/2016.
 */
public interface SearchView extends BaseView {

    void onMovieSelected(int movieID, ImageView posterMovie);

    void setListMovies(List<Movie> listMovies, boolean hasMorePages);
    void addAllMovies(List<Movie> listMovies, boolean hasMorePages);

    void setNenhumFilmeEncontradoVisibility(int visibility);
    void setupRecyclerView();
    void updateAdapter();
}
