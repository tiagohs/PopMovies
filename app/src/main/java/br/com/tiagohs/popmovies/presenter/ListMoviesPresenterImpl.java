package br.com.tiagohs.popmovies.presenter;

import android.util.Log;

import com.android.volley.VolleyError;

import br.com.tiagohs.popmovies.model.response.MovieResponse;
import br.com.tiagohs.popmovies.server.PopMovieServer;
import br.com.tiagohs.popmovies.view.ListMovieView;

public class ListMoviesPresenterImpl implements ListMoviesPresenter {
    private static final String TAG = ListMoviesPresenterImpl.class.getSimpleName();

    private ListMovieView mListMovieView;
    private PopMovieServer mPopMovieServer;

    public ListMoviesPresenterImpl() {
        mPopMovieServer = PopMovieServer.getInstance();
    }

    @Override
    public void setView(ListMovieView view) {
        this.mListMovieView = view;
    }

    public void getMovies(int currentPage) {
        mPopMovieServer.getPopularMovies(currentPage, this);
        Log.i(TAG, "Há Conexão com a Internet, continuando.");
        mListMovieView.showProgress();
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        mListMovieView.onError("Algo deu errado.");
        mListMovieView.hideProgress();
    }

    @Override
    public void onResponse(MovieResponse movies) {
        mListMovieView.atualizarView(movies.getPage(), movies.getTotalPages(), movies.getResults());
        mListMovieView.hideProgress();
    }

}
