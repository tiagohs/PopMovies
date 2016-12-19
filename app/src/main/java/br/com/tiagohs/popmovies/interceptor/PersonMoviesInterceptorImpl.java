package br.com.tiagohs.popmovies.interceptor;

import com.android.volley.VolleyError;

import java.util.HashMap;
import java.util.Map;

import br.com.tiagohs.popmovies.model.response.PersonMoviesResponse;
import br.com.tiagohs.popmovies.server.ResponseListener;
import br.com.tiagohs.popmovies.server.methods.PersonsServer;
import br.com.tiagohs.popmovies.util.enumerations.Sort;

/**
 * Created by Tiago Henrique on 12/09/2016.
 */
public class PersonMoviesInterceptorImpl implements PersonMoviesInterceptor, ResponseListener<PersonMoviesResponse> {

    private PersonsServer mPersonsServer;
    private onPersonMoviesListener mOnPersonMoviesListener;

    public PersonMoviesInterceptorImpl(onPersonMoviesListener onPersonMoviesListener) {
        mPersonsServer = new PersonsServer();
        mOnPersonMoviesListener = onPersonMoviesListener;
    }

    @Override
    public void getPersonMovies(Sort type, int personID, int currentPage, String tag, Map<String, String> parameters) {

        switch(type) {
            case PERSON_MOVIES_CARRER:
                mPersonsServer.getPersonMovies(personID, ++currentPage, new HashMap<String, String>(), tag, this);
                break;
            case PERSON_CONHECIDO_POR:
                mPersonsServer.getPersonMoviesCredits(personID, ++currentPage, tag, this);
                break;
        }

    }

    @Override
    public void onErrorResponse(VolleyError error) {
        mOnPersonMoviesListener.onPersonMoviesRequestError(error);
    }

    @Override
    public void onResponse(PersonMoviesResponse response) {
        mOnPersonMoviesListener.onPersonMoviesRequestSucess(response);
    }
}
