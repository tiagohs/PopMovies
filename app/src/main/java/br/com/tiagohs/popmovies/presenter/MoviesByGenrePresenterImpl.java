package br.com.tiagohs.popmovies.presenter;

import com.android.volley.VolleyError;

import br.com.tiagohs.popmovies.model.movie.Movie;
import br.com.tiagohs.popmovies.model.response.GenericListResponse;
import br.com.tiagohs.popmovies.server.PopMovieServer;
import br.com.tiagohs.popmovies.server.ResponseListener;
import br.com.tiagohs.popmovies.view.MoviesByGenreView;

/**
 * Created by Tiago Henrique on 03/09/2016.
 */
public class MoviesByGenrePresenterImpl implements MoviesByGenrePresenter, ResponseListener<GenericListResponse<Movie>> {

    private MoviesByGenreView mMoviesByGenreView;
    private PopMovieServer mPopMovieServer;

    @Override
    public void setView(MoviesByGenreView view) {
        mMoviesByGenreView = view;
        mPopMovieServer = PopMovieServer.getInstance();
    }

    @Override
    public void getMoviesByGenre(int genreID, int currentPage) {
        mMoviesByGenreView.showProgressBar();
        mPopMovieServer.getMoviesByGenres(genreID, currentPage, this);
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(GenericListResponse<Movie> response) {
        mMoviesByGenreView.hideProgressBar();
        mMoviesByGenreView.atualizarView(response.getPage(), response.getTotalPage(), response.getResults());
    }
}
