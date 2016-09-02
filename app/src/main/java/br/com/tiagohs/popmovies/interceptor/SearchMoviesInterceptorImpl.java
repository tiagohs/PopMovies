package br.com.tiagohs.popmovies.interceptor;


import com.android.volley.VolleyError;

import br.com.tiagohs.popmovies.model.Movie.Movie;
import br.com.tiagohs.popmovies.model.response.GenericListResponse;
import br.com.tiagohs.popmovies.server.PopMovieServer;
import br.com.tiagohs.popmovies.server.ResponseListener;

public class SearchMoviesInterceptorImpl implements SearchMoviesInterceptor, ResponseListener<GenericListResponse<Movie>> {
    private PopMovieServer mPopMovieServer;
    private onSearchMoviesListener mListener;

    public SearchMoviesInterceptorImpl(onSearchMoviesListener listener) {
        mPopMovieServer = PopMovieServer.getInstance();
        mListener = listener;
    }

    @Override
    public void searchMovies(String query, Boolean includeAdult, Integer searchYear, Integer primaryReleaseYear, Integer currentPage) {
        mPopMovieServer.searchMovies(query, includeAdult, searchYear, primaryReleaseYear, currentPage, this);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        mListener.onSearchMoviesRequestError(error);
    }

    @Override
    public void onResponse(GenericListResponse<Movie> response) {
        mListener.onSearchMoviesRequestSucess(response);
    }
}
