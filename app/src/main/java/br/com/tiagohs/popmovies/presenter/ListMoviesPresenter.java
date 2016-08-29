package br.com.tiagohs.popmovies.presenter;

import br.com.tiagohs.popmovies.model.response.MovieResponse;
import br.com.tiagohs.popmovies.server.ResponseListener;
import br.com.tiagohs.popmovies.view.ListMovieView;

public interface ListMoviesPresenter extends BasePresenter<ListMovieView>, ResponseListener<MovieResponse> {

    void getMovies(int currentPage);
}
