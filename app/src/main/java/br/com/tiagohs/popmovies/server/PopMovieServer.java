package br.com.tiagohs.popmovies.server;

import com.android.volley.Request;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.Map;

import br.com.tiagohs.popmovies.App;
import br.com.tiagohs.popmovies.BuildConfig;

public class PopMovieServer {
    protected static final String KEY = BuildConfig.MOVIEDB_API;

    protected static final int METHOD_REQUEST_POST = Request.Method.POST;
    protected static final int METHOD_REQUEST_GET = Request.Method.GET;

    protected static final String BASE_URL_TMDB_MOVIES = "http://api.themoviedb.org/3/";
    protected static final String BASE_URL_OMDB_MOVIES = "http://www.omdbapi.com/";

    protected Map<String, String> mParameters;
    protected TypeReference mTypeToken;

    protected String mUrl;

    protected void execute(int method, Map<String, String> headers, ResponseListener listener, String tag) {
        PopMovieRequest request = new PopMovieRequest(method, mUrl, headers, mParameters, mTypeToken, listener);
        App.getInstance().addToRequestQueue(request, tag);
    }
}
