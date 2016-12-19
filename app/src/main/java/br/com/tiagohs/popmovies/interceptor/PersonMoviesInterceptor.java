package br.com.tiagohs.popmovies.interceptor;

import com.android.volley.VolleyError;

import java.util.Map;

import br.com.tiagohs.popmovies.model.response.PersonMoviesResponse;
import br.com.tiagohs.popmovies.util.enumerations.Sort;

public interface PersonMoviesInterceptor {

    interface onPersonMoviesListener {

        void onPersonMoviesRequestSucess(PersonMoviesResponse moviesResponse);
        void onPersonMoviesRequestError(VolleyError error);
    }

    void getPersonMovies(Sort type, int personID, int currentPage, String tag, Map<String, String> parameters);
}
