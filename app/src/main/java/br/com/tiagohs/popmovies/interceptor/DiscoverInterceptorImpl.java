package br.com.tiagohs.popmovies.interceptor;

import com.android.volley.VolleyError;

import java.util.Map;

import br.com.tiagohs.popmovies.model.response.MovieResponse;
import br.com.tiagohs.popmovies.server.ResponseListener;
import br.com.tiagohs.popmovies.server.methods.MoviesServer;

public class DiscoverInterceptorImpl implements DiscoverInterceptor, ResponseListener<MovieResponse> {
    private MoviesServer mMoviesServer;
    private DiscoverInterceptor.onDiscoverListener mOnDiscoverListener;

    public DiscoverInterceptorImpl(DiscoverInterceptor.onDiscoverListener onEmBreveListener) {
        mMoviesServer = new MoviesServer();
        this.mOnDiscoverListener = onEmBreveListener;
    }

    @Override
    public void getMovies(int currentPage, String tag, Map<String, String> parameters) {
        mMoviesServer.getMoviesDiscover(currentPage, parameters, tag, this);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        mOnDiscoverListener.onDiscoverRequestError(error);
    }

    @Override
    public void onResponse(MovieResponse response) {
        mOnDiscoverListener.onDiscoverRequestSucess(response);
    }
}
