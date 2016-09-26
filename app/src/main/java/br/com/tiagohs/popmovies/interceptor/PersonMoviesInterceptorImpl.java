package br.com.tiagohs.popmovies.interceptor;

import com.android.volley.VolleyError;

import java.util.HashMap;
import java.util.Map;

import br.com.tiagohs.popmovies.model.response.PersonMoviesResponse;
import br.com.tiagohs.popmovies.server.PopMovieServer;
import br.com.tiagohs.popmovies.server.ResponseListener;
import br.com.tiagohs.popmovies.util.enumerations.Sort;

/**
 * Created by Tiago Henrique on 12/09/2016.
 */
public class PersonMoviesInterceptorImpl implements PersonMoviesInterceptor, ResponseListener<PersonMoviesResponse> {

    private PopMovieServer mPopMovieServer;
    private onPersonMoviesListener mOnPersonMoviesListener;

    public PersonMoviesInterceptorImpl(onPersonMoviesListener onPersonMoviesListener) {
        mPopMovieServer = PopMovieServer.getInstance();
        mOnPersonMoviesListener = onPersonMoviesListener;
    }

    @Override
    public void getPersonMovies(Sort type, int personID, int currentPage, Map<String, String> parameters) {

        switch(type) {
            case PERSON_MOVIES_CARRER:
                mPopMovieServer.getPersonMovies(personID, ++currentPage, new HashMap<String, String>(), this);
                break;
            case PERSON_CONHECIDO_POR:
                mPopMovieServer.getPersonMoviesCredits(personID, ++currentPage, this);
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
