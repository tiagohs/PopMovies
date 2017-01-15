package br.com.tiagohs.popmovies.interceptor;

import com.android.volley.VolleyError;

import br.com.tiagohs.popmovies.model.credits.MediaBasic;
import br.com.tiagohs.popmovies.model.person.PersonFind;
import br.com.tiagohs.popmovies.model.response.GenericListResponse;
import br.com.tiagohs.popmovies.server.ResponseListener;
import br.com.tiagohs.popmovies.server.methods.PersonsServer;
import br.com.tiagohs.popmovies.util.enumerations.SearchType;

public class SearchPersonInterceptorImpl implements SearchPersonInterceptor, ResponseListener<GenericListResponse<PersonFind>> {
    private PersonsServer mPersonsServer;
    private onSearchPersonListener mListener;

    public SearchPersonInterceptorImpl(onSearchPersonListener listener) {
        mPersonsServer = new PersonsServer();
        mListener = listener;
    }

    @Override
    public void searchPersons(String query, Boolean includeAdult, SearchType searchType, String tag, Integer currentPage) {
        mPersonsServer.searchPerson(query, includeAdult, searchType, currentPage, tag, this);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        mListener.onSearchPersonRequestError(error);
    }

    @Override
    public void onResponse(GenericListResponse<PersonFind> response) {
        mListener.onSearchPersonRequestSucess(response);
    }
}
