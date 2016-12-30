package br.com.tiagohs.popmovies.interceptor;

import com.android.volley.VolleyError;

import br.com.tiagohs.popmovies.model.movie.MovieDetails;
import br.com.tiagohs.popmovies.model.response.ImageResponse;

/**
 * Created by Tiago on 29/12/2016.
 */

public interface MovieDetailsInterceptor {

    interface onMovieDetailsListener {

        void onMovieDetailsRequestSucess(MovieDetails imageResponse);
        void onMovieDetailsRequestError(VolleyError error);
    }

    void getMovieDetails(int movieID, String[] parameters, String tag);
}
