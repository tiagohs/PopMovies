package br.com.tiagohs.popmovies.interceptor;


import com.android.volley.VolleyError;

import br.com.tiagohs.popmovies.model.movie.Movie;
import br.com.tiagohs.popmovies.model.response.GenericListResponse;
import br.com.tiagohs.popmovies.server.ResponseListener;
import br.com.tiagohs.popmovies.server.methods.MoviesServer;

public class SearchMoviesInterceptorImpl implements SearchMoviesInterceptor, ResponseListener<GenericListResponse<Movie>> {
    private MoviesServer mMoviesServer;
    private onSearchMoviesListener mListener;

    private Integer mYear;

    private int mCurrentPage;
    private int mTotalPages;

    public SearchMoviesInterceptorImpl(onSearchMoviesListener listener) {
        mMoviesServer = new MoviesServer();
        mListener = listener;
    }

    @Override
    public void searchMovies(String query, Boolean includeAdult, Integer searchYear, Integer primaryReleaseYear, String tag, Integer currentPage) {
        if (mYear == null)
            mYear = searchYear;

        mMoviesServer.searchMovies(query, includeAdult, searchYear, primaryReleaseYear, currentPage, tag, this);
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
