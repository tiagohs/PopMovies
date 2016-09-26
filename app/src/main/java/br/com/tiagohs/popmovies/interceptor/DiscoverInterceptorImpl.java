package br.com.tiagohs.popmovies.interceptor;

import com.android.volley.VolleyError;

import java.util.Map;

import br.com.tiagohs.popmovies.model.response.MovieResponse;
import br.com.tiagohs.popmovies.server.PopMovieServer;
import br.com.tiagohs.popmovies.server.ResponseListener;

public class DiscoverInterceptorImpl implements DiscoverInterceptor, ResponseListener<MovieResponse> {
    private PopMovieServer mPopMovieServer;
    private DiscoverInterceptor.onDiscoverListener mOnDiscoverListener;

    public DiscoverInterceptorImpl(DiscoverInterceptor.onDiscoverListener onEmBreveListener) {
        mPopMovieServer = PopMovieServer.getInstance();
        this.mOnDiscoverListener = onEmBreveListener;
    }

    @Override
    public void getMovies(int currentPage, Map<String, String> parameters) {
        mPopMovieServer.getMoviesDiscover(currentPage, parameters, this);
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
