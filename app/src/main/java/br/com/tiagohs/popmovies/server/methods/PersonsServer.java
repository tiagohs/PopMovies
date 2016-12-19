package br.com.tiagohs.popmovies.server.methods;

import com.fasterxml.jackson.core.type.TypeReference;

import java.util.HashMap;
import java.util.Map;

import br.com.tiagohs.popmovies.model.credits.MediaBasic;
import br.com.tiagohs.popmovies.model.person.PersonFind;
import br.com.tiagohs.popmovies.model.person.PersonInfo;
import br.com.tiagohs.popmovies.model.response.GenericListResponse;
import br.com.tiagohs.popmovies.model.response.PersonMoviesResponse;
import br.com.tiagohs.popmovies.server.PopMovieServer;
import br.com.tiagohs.popmovies.server.ResponseListener;
import br.com.tiagohs.popmovies.server.UrlBuilder;
import br.com.tiagohs.popmovies.util.LocaleUtils;
import br.com.tiagohs.popmovies.util.enumerations.Method;
import br.com.tiagohs.popmovies.util.enumerations.Param;
import br.com.tiagohs.popmovies.util.enumerations.SearchType;
import br.com.tiagohs.popmovies.util.enumerations.SubMethod;

public class PersonsServer extends PopMovieServer {

    public void getPersons(int currentPage, String tag, ResponseListener<GenericListResponse<PersonFind>> listener) {
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

        execute(METHOD_REQUEST_GET, null, listener, tag);
    }

    public void getPersonMoviesCredits(int personID, int currentPage, String tag, ResponseListener<PersonMoviesResponse> listener) {
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

        execute(METHOD_REQUEST_GET, null, listener, tag);
    }

    public void getPersonDetails(int personID, String[] appendToResponse, String tag, String language, ResponseListener<PersonInfo> listener) {
        mParameters = new HashMap<>();

        mParameters.put(Param.API_KEY.getParam(), KEY);
        mParameters.put(Param.LANGUAGE.getParam(), language);

        mTypeToken = new TypeReference<PersonInfo>(){};
        mUrl = new UrlBuilder().addBaseUrl(BASE_URL_TMDB_MOVIES)
                .addMethod(Method.PERSON)
                .addId(personID)
                .addParameters(mParameters)
                .addAppendToResponse(appendToResponse)
                .build();

        execute(METHOD_REQUEST_GET, null, listener, tag);
    }

    public void getPersonMovies(int personID, int currentPage, Map<String, String> parameters, String tag, ResponseListener<PersonMoviesResponse> listener) {

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

        execute(METHOD_REQUEST_GET, null, listener, tag);
    }

    public void searchPerson(String query,
                             Boolean includeAdult,
                             SearchType searchType,
                             Integer currentPage,
                             String tag,
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

        execute(METHOD_REQUEST_GET, null, listener, tag);
    }
}
