package br.com.tiagohs.popmovies.server.methods;

import com.fasterxml.jackson.core.type.TypeReference;

import java.util.HashMap;
import java.util.Map;

import br.com.tiagohs.popmovies.model.movie.CollectionDetails;
import br.com.tiagohs.popmovies.model.movie.Movie;
import br.com.tiagohs.popmovies.model.movie.MovieDetails;
import br.com.tiagohs.popmovies.model.response.GenericListResponse;
import br.com.tiagohs.popmovies.model.response.GenresResponse;
import br.com.tiagohs.popmovies.model.response.ImageResponse;
import br.com.tiagohs.popmovies.model.response.MovieResponse;
import br.com.tiagohs.popmovies.model.response.RankingResponse;
import br.com.tiagohs.popmovies.model.response.VideosResponse;
import br.com.tiagohs.popmovies.model.review.Review;
import br.com.tiagohs.popmovies.server.PopMovieServer;
import br.com.tiagohs.popmovies.server.ResponseListener;
import br.com.tiagohs.popmovies.server.UrlBuilder;
import br.com.tiagohs.popmovies.util.LocaleUtils;
import br.com.tiagohs.popmovies.util.enumerations.Method;
import br.com.tiagohs.popmovies.util.enumerations.Param;
import br.com.tiagohs.popmovies.util.enumerations.SubMethod;

public class MoviesServer extends PopMovieServer {

    public MoviesServer() {
        super();
    }

    public void getPopularMovies(int currentPage, String tag,
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

        execute(METHOD_REQUEST_POST, null, listener, tag);
    }

    public void getMovieDetails(int movieID,
                                String[] appendToResponse, String tag,
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

        execute(METHOD_REQUEST_GET, null, listener, tag);
    }

    public void getMovieDetails(int movieID,
                                String[] appendToResponse, String tag,
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

        execute(METHOD_REQUEST_GET, null, listener, tag);
    }

    public void getMovieRankings(String imdbID, String tag, ResponseListener<RankingResponse> listener) {
        mParameters = new HashMap<>();

        mParameters.put(Param.OMDB_QUERY.getParam(), String.valueOf(imdbID));
        mParameters.put(Param.OMDB_TOMATOES.getParam(), "true");

        mTypeToken = new TypeReference<RankingResponse>(){};
        mUrl = new UrlBuilder().addBaseUrl(BASE_URL_OMDB_MOVIES)
                .addParameters(mParameters)
                .build();

        execute(METHOD_REQUEST_GET, null, listener, tag);
    }

    public void getMovieSimilars(int movieID, int currentPage, String tag, ResponseListener<GenericListResponse<Movie>> listener) {
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

        execute(METHOD_REQUEST_GET, null, listener, tag);
    }

    public void getMovieImagens(int movieID, String tag,
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

        execute(METHOD_REQUEST_GET, null, listener, tag);
    }

    public void getMovieVideos(int movieID, String language, String tag,
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

        execute(METHOD_REQUEST_GET, null, listener, tag);
    }

    public void getMovieReviews(int movieID, int currentPage, String language, String tag,
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

        execute(METHOD_REQUEST_GET, null, listener, tag);
    }

    public void getMoviesByGenres(int genreID, int currentPage, String tag, ResponseListener<GenericListResponse<Movie>> listener) {
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

        execute(METHOD_REQUEST_GET, null, listener, tag);
    }

    public void getMoviesByKeywords(int keywordID, int currentPage, String tag, Map<String, String> parameters, ResponseListener<GenericListResponse<Movie>> listener) {

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

        execute(METHOD_REQUEST_GET, null, listener, tag);
    }

    public void getMoviesDiscover(int currentPage, Map<String, String> parameters, String tag, ResponseListener<MovieResponse> listener) {
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

        execute(METHOD_REQUEST_GET, null, listener, tag);
    }

    public void getGenres(String tag, ResponseListener<GenresResponse> listener) {
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

        execute(METHOD_REQUEST_GET, null, listener, tag);
    }

    public void getMovieCollections(String tag, int collectionID, ResponseListener<CollectionDetails> listener) {
        mParameters = new HashMap<>();

        mParameters.put(Param.API_KEY.getParam(), KEY);
        mParameters.put(Param.LANGUAGE.getParam(), LocaleUtils.getLocaleLanguageAndCountry());

        mTypeToken = new TypeReference<CollectionDetails>(){};
        mUrl = new UrlBuilder().addBaseUrl(BASE_URL_TMDB_MOVIES)
                .addMethod(Method.COLLECTION)
                .addId(collectionID)
                .addParameters(mParameters)
                .build();

        execute(METHOD_REQUEST_GET, null, listener, tag);
    }

    public void searchMovies(String query,
                             Boolean includeAdult,
                             Integer searchYear,
                             Integer primaryReleaseYear,
                             Integer currentPage, String tag,
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

        execute(METHOD_REQUEST_GET, null, listener, tag);
    }

}
