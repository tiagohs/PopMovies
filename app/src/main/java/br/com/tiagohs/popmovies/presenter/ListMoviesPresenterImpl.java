package br.com.tiagohs.popmovies.presenter;

import android.util.Log;

import com.android.volley.VolleyError;

import br.com.tiagohs.popmovies.model.MovieResponse;
import br.com.tiagohs.popmovies.server.PopMovieServer;
import br.com.tiagohs.popmovies.server.ResponseListener;
import br.com.tiagohs.popmovies.view.ListMovieView;

public class ListMoviesPresenterImpl implements ListMoviesPresenter, ResponseListener<MovieResponse> {
    private static final String TAG = ListMoviesPresenterImpl.class.getSimpleName();

    private ListMovieView mListMovieView;
    private PopMovieServer mPopMovieServer;

    public ListMoviesPresenterImpl() {
        mPopMovieServer = new PopMovieServer();
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
        mListMovieView.atualizarView(movies.getPage(), movies.getTotal_pages(), movies.getResults());
        mListMovieView.hideProgress();
    }

}
