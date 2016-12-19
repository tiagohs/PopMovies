package br.com.tiagohs.popmovies.interceptor;

import com.android.volley.VolleyError;

import br.com.tiagohs.popmovies.model.person.PersonFind;
import br.com.tiagohs.popmovies.model.response.GenericListResponse;
import br.com.tiagohs.popmovies.server.ResponseListener;
import br.com.tiagohs.popmovies.server.methods.PersonsServer;
import br.com.tiagohs.popmovies.util.enumerations.Sort;

public class PersonInterceptorImpl implements PersonInterceptor,
                                              ResponseListener<GenericListResponse<PersonFind>> {
    private Sort mSort;
    private PersonsServer mPersonsServer;
    private PersonInterceptor.onPersonListener mOnPersonListener;

    public PersonInterceptorImpl(onPersonListener onPersonListener) {
        mOnPersonListener = onPersonListener;
        mPersonsServer = new PersonsServer();
    }

    @Override
    public void getPersons(int page, String tag, Sort sort) {
        if (mSort == null) mSort = sort;

        mPersonsServer.getPersons(page, tag, this);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        mOnPersonListener.onPersonRequestError(error);
    }

    @Override
    public void onResponse(GenericListResponse<PersonFind> response) {
        mOnPersonListener.onPersonRequestSucess(response);
    }

}
