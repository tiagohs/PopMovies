package br.com.tiagohs.popmovies.interceptor;

import com.android.volley.VolleyError;

import br.com.tiagohs.popmovies.model.movie.Movie;
import br.com.tiagohs.popmovies.model.response.GenericListResponse;

/**
 * Created by Tiago Henrique on 01/09/2016.
 */
public interface SearchMoviesInterceptor {

    interface onSearchMoviesListener {

        void onSearchMoviesRequestSucess(GenericListResponse<Movie> imageResponse);
        void onSearchMoviesRequestError(VolleyError error);
    }

    void searchMovies(String query,
                      Boolean includeAdult,
                      Integer searchYear,
                      Integer primaryReleaseYear,
                      Integer currentPage);
}
