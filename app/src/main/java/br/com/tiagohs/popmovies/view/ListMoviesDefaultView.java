package br.com.tiagohs.popmovies.view;

import java.util.List;

import br.com.tiagohs.popmovies.model.dto.MovieListDTO;

/**
 * Created by Tiago Henrique on 03/09/2016.
 */
public interface ListMoviesDefaultView extends BaseView {

    void hideDialogProgress();
    void showDialogProgress();

    void setProgressVisibility(int visibityState);
    void setRecyclerViewVisibility(int visibilityState);

    void setupRecyclerView();
    void setListMovies(List<MovieListDTO> listMovies, boolean hasMorePages);
    void addAllMovies(List<MovieListDTO> listMovies, boolean hasMorePages);
    void updateAdapter();
}
