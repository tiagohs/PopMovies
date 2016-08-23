package br.com.tiagohs.popmovies.server;

import android.content.res.Resources;

import com.android.volley.Request;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.Map;

import br.com.tiagohs.popmovies.App;
import br.com.tiagohs.popmovies.BuildConfig;
import br.com.tiagohs.popmovies.model.MovieCastCrewResponse;
import br.com.tiagohs.popmovies.model.MovieResponse;
import br.com.tiagohs.popmovies.model.MovieReviewResponse;
import br.com.tiagohs.popmovies.util.ServerUtils;
import br.com.tiagohs.popmovies.view.fragment.ListMoviesFragment;

public class PopMovieServer {
    private static final String TAG = ListMoviesFragment.class.getSimpleName();
    private static final String KEY = BuildConfig.MOVIEDB_API;

    private static final int METHOD_REQUEST_POST = Request.Method.POST;
    private static final int METHOD_REQUEST_GET = Request.Method.GET;

    private static final String BASE_URL_MOVIES = "http://api.themoviedb.org/3/movie/";
    private static final String GET_MOVIES_POPULAR = "popular";
    private static final String GET_MOVIE_REVIEWS = "reviews";
    private static final String GET_MOVIE_CAST_CREW = "credits";

    private static final String PARAM_KEY = "api_key";
    private static final String PARAM_PAGE = "page";
    private static final String PARAM_LANGUAGE = "language";

    private Map<String, String> params;
    private TypeToken typeToken;
    private String language;
    private String url;

    public PopMovieServer() {

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                language = Resources.getSystem().getConfiguration().getLocales().get(0).getLanguage();
            } else {
                language = Resources.getSystem().getConfiguration().locale.getLanguage();
           }
    }

    public void getPopularMovies(int currentPage, ResponseListener<MovieResponse> listener) {
        params = new HashMap<>();

        params.put(PARAM_KEY, KEY);
        params.put(PARAM_LANGUAGE, language);
        params.put(PARAM_PAGE, String.valueOf(currentPage));

        typeToken = new TypeToken<MovieResponse>(){};
        url = ServerUtils.getUrl(BASE_URL_MOVIES + GET_MOVIES_POPULAR, params);

        execute(METHOD_REQUEST_POST, null, listener);
    }

    public void getMovieReviews(ResponseListener<MovieResponse> listener) {
        params = new HashMap<>();

        params.put(PARAM_KEY, KEY);
        params.put(PARAM_LANGUAGE, language);

        typeToken = new TypeToken<MovieReviewResponse>(){};
        url = ServerUtils.getUrl(BASE_URL_MOVIES + GET_MOVIE_REVIEWS, params);
    }

    public void getMovieCast(ResponseListener<MovieResponse> listener) {
        params = new HashMap<>();

        params.put(PARAM_KEY, KEY);
        params.put(PARAM_LANGUAGE, language);

        typeToken = new TypeToken<MovieCastCrewResponse>(){};
        url = ServerUtils.getUrl(BASE_URL_MOVIES + GET_MOVIE_CAST_CREW, params);
    }

    private void execute(int method, Map<String, String> headers, ResponseListener listener) {
        PopMovieRequest request = new PopMovieRequest(method, url, headers, params, typeToken, listener);
        App.getInstance().addToRequestQueue(request);
    }

}
