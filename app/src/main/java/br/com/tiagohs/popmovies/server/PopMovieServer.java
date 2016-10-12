package br.com.tiagohs.popmovies.server;

import com.android.volley.Request;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.HashMap;
import java.util.Map;

import br.com.tiagohs.popmovies.App;
import br.com.tiagohs.popmovies.BuildConfig;
import br.com.tiagohs.popmovies.model.credits.MediaBasic;
import br.com.tiagohs.popmovies.model.movie.Movie;
import br.com.tiagohs.popmovies.model.movie.MovieDetails;
import br.com.tiagohs.popmovies.model.person.PersonFind;
import br.com.tiagohs.popmovies.model.person.PersonInfo;
import br.com.tiagohs.popmovies.model.response.GenericListResponse;
import br.com.tiagohs.popmovies.model.response.GenresResponse;
import br.com.tiagohs.popmovies.model.response.ImageResponse;
import br.com.tiagohs.popmovies.model.response.MovieResponse;
import br.com.tiagohs.popmovies.model.response.PersonMoviesResponse;
import br.com.tiagohs.popmovies.model.response.RankingResponse;
import br.com.tiagohs.popmovies.model.response.VideosResponse;
import br.com.tiagohs.popmovies.model.review.Review;
import br.com.tiagohs.popmovies.util.LocaleUtils;
import br.com.tiagohs.popmovies.util.enumerations.Method;
import br.com.tiagohs.popmovies.util.enumerations.Param;
import br.com.tiagohs.popmovies.util.enumerations.SearchType;
import br.com.tiagohs.popmovies.util.enumerations.SubMethod;

public class PopMovieServer {

    private static final String KEY = BuildConfig.MOVIEDB_API;
    private static PopMovieServer instance;
    private static final int METHOD_REQUEST_POST = Request.Method.POST;
    private static final int METHOD_REQUEST_GET = Request.Method.GET;

    private static final String BASE_URL_TMDB_MOVIES = "http://api.themoviedb.org/3/";
    private static final String BASE_URL_OMDB_MOVIES = "http://www.omdbapi.com/";

    private Map<String, String> mParameters;
    private TypeReference mTypeToken;

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

        return instance;
    }

    public void getPopularMovies(int currentPage,
                                 ResponseListener<MovieResponse> listener) {
        mParameters = new HashMap<>();

        mParameters.put(Param.API_KEY.getParam(), KEY);
        mParameters.put(Param.LANGUAGE.getParam(), LocaleUtils.getLocaleLanguageAndCountry());
        mParameters.put(Param.PAGE.getParam(), String.valueOf(currentPage));

        mTypeToken = new TypeReference<MovieResponse>(){};
        mUrl = new UrlBuilder().addBaseUrl(BASE_URL_TMDB_MOVIES)
                .addMethod(Method.MOVIE)
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
        mParameters.put(Param.LANGUAGE.getParam(), LocaleUtils.getLocaleLanguageAndCountry());

        mTypeToken = new TypeReference<MovieDetails>(){};
        mUrl = new UrlBuilder().addBaseUrl(BASE_URL_TMDB_MOVIES)
                .addMethod(Method.MOVIE)
                .addId(movieID)
                .addParameters(mParameters)
                .addAppendToResponse(appendToResponse)
                .build();

        execute(METHOD_REQUEST_GET, null, listener);
    }

    public void getMovieDetails(int movieID,
                                String[] appendToResponse,
                                String language, ResponseListener<MovieDetails> listener) {
        mParameters = new HashMap<>();

        mParameters.put(Param.API_KEY.getParam(), KEY);
        mParameters.put(Param.LANGUAGE.getParam(), language);

        mTypeToken = new TypeReference<MovieDetails>(){};
        mUrl = new UrlBuilder().addBaseUrl(BASE_URL_TMDB_MOVIES)
                .addMethod(Method.MOVIE)
                .addId(movieID)
                .addParameters(mParameters)
                .addAppendToResponse(appendToResponse)
                .build();

        execute(METHOD_REQUEST_GET, null, listener);
    }

    public void getMovieRankings(String imdbID, ResponseListener<RankingResponse> listener) {
        mParameters = new HashMap<>();

        mParameters.put(Param.OMDB_QUERY.getParam(), String.valueOf(imdbID));
        mParameters.put(Param.OMDB_TOMATOES.getParam(), "true");

        mTypeToken = new TypeReference<RankingResponse>(){};
        mUrl = new UrlBuilder().addBaseUrl(BASE_URL_OMDB_MOVIES)
                .addParameters(mParameters)
                .build();

        execute(METHOD_REQUEST_GET, null, listener);
    }

    public void getMovieSimilars(int movieID, int currentPage, ResponseListener<GenericListResponse<Movie>> listener) {
        mParameters = new HashMap<>();

        mParameters.put(Param.API_KEY.getParam(), KEY);
        mParameters.put(Param.PAGE.getParam(), String.valueOf(currentPage));
        mParameters.put(Param.LANGUAGE.getParam(), LocaleUtils.getLocaleLanguageAndCountry());

        mTypeToken = new TypeReference<GenericListResponse<Movie>>(){};
        mUrl = new UrlBuilder().addBaseUrl(BASE_URL_TMDB_MOVIES)
                .addMethod(Method.MOVIE)
                .addIdBySubMethod(movieID)
                .addSubMethod(SubMethod.SIMILAR)
                .addParameters(mParameters)
                .build();

        execute(METHOD_REQUEST_GET, null, listener);
    }

    public void getMovieImagens(int movieID,
                                ResponseListener<ImageResponse> listener) {
        mParameters = new HashMap<>();

        mParameters.put(Param.API_KEY.getParam(), KEY);
        mParameters.put(Param.INCLUDE_IMAGE_LANGUAGE.getParam(), "en,pt,es,it");

        mTypeToken = new TypeReference<ImageResponse>(){};
        mUrl = new UrlBuilder().addBaseUrl(BASE_URL_TMDB_MOVIES)
                .addMethod(Method.MOVIE)
                .addIdBySubMethod(movieID)
                .addSubMethod(SubMethod.IMAGES)
                .addParameters(mParameters)
                .build();

        execute(METHOD_REQUEST_GET, null, listener);
    }

    public void getMovieVideos(int movieID, String language,
                               ResponseListener<VideosResponse> listener) {
        mParameters = new HashMap<>();

        mParameters.put(Param.API_KEY.getParam(), KEY);
        mParameters.put(Param.LANGUAGE.getParam(), language);

        mTypeToken = new TypeReference<VideosResponse>(){};
        mUrl = new UrlBuilder().addBaseUrl(BASE_URL_TMDB_MOVIES)
                .addMethod(Method.MOVIE)
                .addIdBySubMethod(movieID)
                .addSubMethod(SubMethod.VIDEOS)
                .addParameters(mParameters)
                .build();

        execute(METHOD_REQUEST_GET, null, listener);
    }

    public void getMovieReviews(int movieID, int currentPage, String language,
                               ResponseListener<GenericListResponse<Review>> listener) {
        mParameters = new HashMap<>();

        mParameters.put(Param.API_KEY.getParam(), KEY);
        mParameters.put(Param.PAGE.getParam(), String.valueOf(currentPage));
        mParameters.put(Param.LANGUAGE.getParam(), language);

        mTypeToken = new TypeReference<GenericListResponse<Review>>(){};
        mUrl = new UrlBuilder().addBaseUrl(BASE_URL_TMDB_MOVIES)
                .addMethod(Method.MOVIE)
                .addIdBySubMethod(movieID)
                .addSubMethod(SubMethod.REVIEWS)
                .addParameters(mParameters)
                .build();

        execute(METHOD_REQUEST_GET, null, listener);
    }

    public void getPersons(int currentPage, ResponseListener<GenericListResponse<PersonFind>> listener) {
        mParameters = new HashMap<>();

        mParameters.put(Param.API_KEY.getParam(), KEY);
        mParameters.put(Param.LANGUAGE.getParam(), LocaleUtils.getLocaleLanguageAndCountry());
        mParameters.put(Param.PAGE.getParam(), String.valueOf(currentPage));

        mTypeToken = new TypeReference<GenericListResponse<PersonFind>>(){};
        mUrl = new UrlBuilder().addBaseUrl(BASE_URL_TMDB_MOVIES)
                .addMethod(Method.PERSON)
                .addSubMethod(SubMethod.POPULAR)
                .addParameters(mParameters)
                .build();

        execute(METHOD_REQUEST_GET, null, listener);
    }

    public void getPersonMoviesCredits(int personID, int currentPage, ResponseListener<PersonMoviesResponse> listener) {
        mParameters = new HashMap<>();

        mParameters.put(Param.API_KEY.getParam(), KEY);
        mParameters.put(Param.PAGE.getParam(), String.valueOf(currentPage));
        mParameters.put(Param.LANGUAGE.getParam(), LocaleUtils.getLocaleLanguageAndCountry());

        mTypeToken = new TypeReference<PersonMoviesResponse>(){};
        mUrl = new UrlBuilder().addBaseUrl(BASE_URL_TMDB_MOVIES)
                .addMethod(Method.PERSON)
                .addIdBySubMethod(personID)
                .addSubMethod(SubMethod.MOVIE_CREDITS)
                .addParameters(mParameters)
                .build();

        execute(METHOD_REQUEST_GET, null, listener);
    }

    public void getPersonDetails(int personID, String[] appendToResponse, ResponseListener<PersonInfo> listener) {
        mParameters = new HashMap<>();

        mParameters.put(Param.API_KEY.getParam(), KEY);
        mParameters.put(Param.LANGUAGE.getParam(), LocaleUtils.getLocaleLanguageAndCountry());

        mTypeToken = new TypeReference<PersonInfo>(){};
        mUrl = new UrlBuilder().addBaseUrl(BASE_URL_TMDB_MOVIES)
                .addMethod(Method.PERSON)
                .addId(personID)
                .addParameters(mParameters)
                .addAppendToResponse(appendToResponse)
                .build();

        execute(METHOD_REQUEST_GET, null, listener);
    }

    public void getPersonMovies(int personID, int currentPage, Map<String, String> parameters, ResponseListener<PersonMoviesResponse> listener) {

        parameters.put(Param.API_KEY.getParam(), KEY);
        parameters.put(Param.LANGUAGE.getParam(), LocaleUtils.getLocaleLanguageAndCountry());
        parameters.put(Param.PAGE.getParam(), String.valueOf(currentPage));

        mTypeToken = new TypeReference<PersonMoviesResponse>(){};
        mUrl = new UrlBuilder().addBaseUrl(BASE_URL_TMDB_MOVIES)
                .addMethod(Method.PERSON)
                .addIdBySubMethod(personID)
                .addSubMethod(SubMethod.MOVIE_CREDITS)
                .addParameters(parameters)
                .build();

        execute(METHOD_REQUEST_GET, null, listener);
    }

    public void getGenres(ResponseListener<GenresResponse> listener) {
        mParameters = new HashMap<>();

        mParameters.put(Param.API_KEY.getParam(), KEY);
        mParameters.put(Param.LANGUAGE.getParam(), LocaleUtils.getLocaleLanguageAndCountry());

        mTypeToken = new TypeReference<GenresResponse>(){};
        mUrl = new UrlBuilder().addBaseUrl(BASE_URL_TMDB_MOVIES)
                .addMethod(Method.GENRE)
                .addMethod(Method.MOVIE)
                .addSubMethod(SubMethod.LIST)
                .addParameters(mParameters)
                .build();

        execute(METHOD_REQUEST_GET, null, listener);
    }

    public void getMoviesByGenres(int genreID, int currentPage, ResponseListener<GenericListResponse<Movie>> listener) {
        mParameters = new HashMap<>();

        mParameters.put(Param.API_KEY.getParam(), KEY);
        mParameters.put(Param.LANGUAGE.getParam(), LocaleUtils.getLocaleLanguageAndCountry());
        mParameters.put(Param.PAGE.getParam(), String.valueOf(currentPage));
        mParameters.put(Param.INCLUDE_ALL_MOVIES.getParam(), "true");
        mParameters.put(Param.INCLUDE_ADULT.getParam(), "true");

        mTypeToken = new TypeReference<GenericListResponse<Movie>>(){};
        mUrl = new UrlBuilder().addBaseUrl(BASE_URL_TMDB_MOVIES)
                .addMethod(Method.GENRE)
                .addIdBySubMethod(genreID)
                .addSubMethod(SubMethod.MOVIES)
                .addParameters(mParameters)
                .build();

        execute(METHOD_REQUEST_GET, null, listener);
    }

    public void getMoviesByKeywords(int keywordID, int currentPage, Map<String, String> parameters, ResponseListener<GenericListResponse<Movie>> listener) {

        parameters.put(Param.API_KEY.getParam(), KEY);
        parameters.put(Param.LANGUAGE.getParam(), LocaleUtils.getLocaleLanguageAndCountry());
        parameters.put(Param.PAGE.getParam(), String.valueOf(currentPage));

        mTypeToken = new TypeReference<GenericListResponse<Movie>>(){};
        mUrl = new UrlBuilder().addBaseUrl(BASE_URL_TMDB_MOVIES)
                .addMethod(Method.KEYWORD)
                .addIdBySubMethod(keywordID)
                .addSubMethod(SubMethod.MOVIES)
                .addParameters(parameters)
                .build();

        execute(METHOD_REQUEST_GET, null, listener);
    }

    public void getMoviesDiscover(int currentPage, Map<String, String> parameters, ResponseListener<MovieResponse> listener) {
        if (parameters == null) parameters = new HashMap<>();

        parameters.put(Param.API_KEY.getParam(), KEY);
        parameters.put(Param.LANGUAGE.getParam(), LocaleUtils.getLocaleLanguageAndCountry());
        parameters.put(Param.PAGE.getParam(), String.valueOf(currentPage));

        mTypeToken = new TypeReference<MovieResponse>(){};
        mUrl = new UrlBuilder().addBaseUrl(BASE_URL_TMDB_MOVIES)
                .addMethod(Method.DISCOVER)
                .addSubMethod(SubMethod.MOVIE)
                .addParameters(parameters)
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
        mParameters.put(Param.LANGUAGE.getParam(),  LocaleUtils.getLocaleLanguageAndCountry());
        mParameters.put(Param.QUERY.getParam(), query);
        mParameters.put(Param.PAGE.getParam(), String.valueOf(currentPage));
        mParameters.put(Param.ADULT.getParam(), includeAdult ? String.valueOf(true) : String.valueOf(false));
        mParameters.put(Param.PRIMARY_RELEASE_YEAR.getParam(), String.valueOf(primaryReleaseYear));
        mParameters.put(Param.YEAR.getParam(), String.valueOf(searchYear));

        mTypeToken = new TypeReference<GenericListResponse<Movie>>(){};
        mUrl = new UrlBuilder().addBaseUrl(BASE_URL_TMDB_MOVIES)
                .addMethod(Method.SEARCH)
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
        mParameters.put(Param.LANGUAGE.getParam(), LocaleUtils.getLocaleLanguageAndCountry());
        mParameters.put(Param.QUERY.getParam(), query);
        mParameters.put(Param.PAGE.getParam(), String.valueOf(currentPage));
        mParameters.put(Param.ADULT.getParam(), includeAdult ? String.valueOf(true) : String.valueOf(false));
        mParameters.put(Param.SEARCH_TYPE.getParam(), searchType != null ? "" : searchType.getPropertyString());

        mTypeToken = new TypeReference<GenericListResponse<MediaBasic>>(){};
        mUrl = new UrlBuilder().addBaseUrl(BASE_URL_TMDB_MOVIES)
                .addMethod(Method.SEARCH)
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
