package br.com.tiagohs.popmovies.server;

import android.content.res.Resources;
import android.util.Log;

import com.android.volley.Request;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.HashMap;
import java.util.Map;

import br.com.tiagohs.popmovies.App;
import br.com.tiagohs.popmovies.BuildConfig;
import br.com.tiagohs.popmovies.model.Movie.Movie;
import br.com.tiagohs.popmovies.model.Movie.MovieDetails;
import br.com.tiagohs.popmovies.model.credits.MediaBasic;
import br.com.tiagohs.popmovies.model.response.GenericListResponse;
import br.com.tiagohs.popmovies.model.response.ImageResponse;
import br.com.tiagohs.popmovies.model.response.MovieResponse;
import br.com.tiagohs.popmovies.model.response.VideosResponse;
import br.com.tiagohs.popmovies.util.enumerations.Method;
import br.com.tiagohs.popmovies.util.enumerations.Param;
import br.com.tiagohs.popmovies.util.enumerations.SearchType;
import br.com.tiagohs.popmovies.util.enumerations.SubMethod;
import br.com.tiagohs.popmovies.view.fragment.ListMoviesFragment;

public class PopMovieServer {
    private static final String TAG = ListMoviesFragment.class.getSimpleName();

    private static final String KEY = BuildConfig.MOVIEDB_API;
    private static PopMovieServer instance;
    private static final int METHOD_REQUEST_POST = Request.Method.POST;
    private static final int METHOD_REQUEST_GET = Request.Method.GET;

    private static final String BASE_URL_MOVIES = "http://api.themoviedb.org/3/";

    private Map<String, String> mParameters;
    private TypeReference mTypeToken;
    public static String mLanguage;
    public static String mCountry;

    private String mUrl;
    private PopMovieServer() {
    }

    public static PopMovieServer getInstance() {

        if (instance == null) {
            synchronized (PopMovieServer.class) {
                if (instance == null) {
                    instance = new PopMovieServer();
                }
            }
        }
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            mLanguage = Resources.getSystem().getConfiguration().getLocales().get(0).getLanguage();
            mCountry = Resources.getSystem().getConfiguration().getLocales().get(0).getCountry();
        } else {
            mLanguage = Resources.getSystem().getConfiguration().locale.getLanguage();
            mCountry = Resources.getSystem().getConfiguration().locale.getCountry();
        }

        return instance;
    }

    public void getPopularMovies(int currentPage,
                                 ResponseListener<MovieResponse> listener) {
        mParameters = new HashMap<>();

        mParameters.put(Param.API_KEY.getParam(), KEY);
        mParameters.put(Param.LANGUAGE.getParam(), mLanguage + "-" + mCountry);
        mParameters.put(Param.PAGE.getParam(), String.valueOf(currentPage));

        mTypeToken = new TypeReference<MovieResponse>(){};
        mUrl = new UrlBuilder().addMethod(Method.MOVIE)
                            .addSubMethod(SubMethod.POPULAR)
                            .addParameters(mParameters)
                            .build();

        execute(METHOD_REQUEST_POST, null, listener);
    }

    public void getMovieDetails(int movieID,
                                String[] appendToResponse,
                                ResponseListener<MovieDetails> listener) {
        mParameters = new HashMap<>();

        mParameters.put(Param.API_KEY.getParam(), KEY);
        mParameters.put(Param.LANGUAGE.getParam(), mLanguage + "-" + mCountry);

        mTypeToken = new TypeReference<MovieDetails>(){};
        mUrl = new UrlBuilder().addMethod(Method.MOVIE)
                .addId(movieID)
                .addParameters(mParameters)
                .addAppendToResponse(appendToResponse)
                .build();

        execute(METHOD_REQUEST_GET, null, listener);
    }

    public void getMovieImagens(int movieID,
                                ResponseListener<ImageResponse> listener) {
        mParameters = new HashMap<>();

        mParameters.put(Param.API_KEY.getParam(), KEY);

        mTypeToken = new TypeReference<ImageResponse>(){};
        mUrl = new UrlBuilder().addMethod(Method.MOVIE)
                .addIdBySubMethod(movieID)
                .addSubMethod(SubMethod.IMAGES)
                .addParameters(mParameters)
                .build();

        execute(METHOD_REQUEST_GET, null, listener);
    }

    public void getMovieVideos(int movieID,
                               ResponseListener<VideosResponse> listener) {
        mParameters = new HashMap<>();

        mParameters.put(Param.API_KEY.getParam(), KEY);

        mTypeToken = new TypeReference<VideosResponse>(){};
        mUrl = new UrlBuilder().addMethod(Method.MOVIE)
                .addIdBySubMethod(movieID)
                .addSubMethod(SubMethod.VIDEOS)
                .addParameters(mParameters)
                .build();

        execute(METHOD_REQUEST_GET, null, listener);
    }

    public void searchMovies(String query,
                             Boolean includeAdult,
                             Integer searchYear,
                             Integer primaryReleaseYear,
                             Integer currentPage,
                             ResponseListener<GenericListResponse<Movie>> listener) {
        mParameters = new HashMap<>();

        mParameters.put(Param.API_KEY.getParam(), KEY);
        mParameters.put(Param.LANGUAGE.getParam(), "en");
        mParameters.put(Param.QUERY.getParam(), query);
        mParameters.put(Param.PAGE.getParam(), String.valueOf(currentPage));
        mParameters.put(Param.ADULT.getParam(), includeAdult ? String.valueOf(true) : String.valueOf(false));
        mParameters.put(Param.PRIMARY_RELEASE_YEAR.getParam(), String.valueOf(primaryReleaseYear));
        mParameters.put(Param.YEAR.getParam(), String.valueOf(searchYear));

        mTypeToken = new TypeReference<GenericListResponse<Movie>>(){};
        mUrl = new UrlBuilder().addMethod(Method.SEARCH)
                .addSubMethod(SubMethod.MOVIE)
                .addParameters(mParameters)
                .build();

        execute(METHOD_REQUEST_GET, null, listener);
    }

    public void searchPerson(String query,
                             Boolean includeAdult,
                             SearchType searchType,
                             Integer currentPage,
                             ResponseListener<GenericListResponse<MediaBasic>> listener) {
        mParameters = new HashMap<>();

        mParameters.put(Param.API_KEY.getParam(), KEY);
        mParameters.put(Param.LANGUAGE.getParam(), "en");
        mParameters.put(Param.QUERY.getParam(), query);
        mParameters.put(Param.PAGE.getParam(), String.valueOf(currentPage));
        mParameters.put(Param.ADULT.getParam(), includeAdult ? String.valueOf(true) : String.valueOf(false));
        mParameters.put(Param.SEARCH_TYPE.getParam(), searchType != null ? "" : searchType.getPropertyString());

        mTypeToken = new TypeReference<GenericListResponse<MediaBasic>>(){};
        mUrl = new UrlBuilder().addMethod(Method.SEARCH)
                .addSubMethod(SubMethod.PERSON)
                .addParameters(mParameters)
                .build();

        execute(METHOD_REQUEST_GET, null, listener);
    }

    private void execute(int method, Map<String, String> headers, ResponseListener listener) {
        PopMovieRequest request = new PopMovieRequest(method, mUrl, headers, mParameters, mTypeToken, listener);
        App.getInstance().addToRequestQueue(request);
    }
}
