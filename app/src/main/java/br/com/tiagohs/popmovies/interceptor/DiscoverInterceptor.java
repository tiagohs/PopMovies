package br.com.tiagohs.popmovies.interceptor;

import com.android.volley.VolleyError;

import java.util.Map;

import br.com.tiagohs.popmovies.model.response.MovieResponse;

public interface DiscoverInterceptor {

    interface onDiscoverListener {

        void onDiscoverRequestSucess(MovieResponse moviesResponse);
        void onDiscoverRequestError(VolleyError error);
    }

    void getMovies(int currentPage, String tag, Map<String, String> parameters);
}
