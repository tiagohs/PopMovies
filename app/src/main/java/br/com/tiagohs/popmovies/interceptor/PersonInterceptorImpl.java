package br.com.tiagohs.popmovies.interceptor;

import com.android.volley.VolleyError;

import br.com.tiagohs.popmovies.model.person.PersonFind;
import br.com.tiagohs.popmovies.model.response.GenericListResponse;
import br.com.tiagohs.popmovies.server.PopMovieServer;
import br.com.tiagohs.popmovies.server.ResponseListener;
import br.com.tiagohs.popmovies.util.enumerations.Sort;

public class PersonInterceptorImpl implements PersonInterceptor,
                                              ResponseListener<GenericListResponse<PersonFind>> {
    private Sort mSort;
    private PopMovieServer mPopMovieServer;
    private PersonInterceptor.onPersonListener mOnPersonListener;

    public PersonInterceptorImpl(onPersonListener onPersonListener) {
        mOnPersonListener = onPersonListener;
        mPopMovieServer = PopMovieServer.getInstance();
    }

    @Override
    public void getPersons(int page, Sort sort) {
        if (mSort == null) mSort = sort;

        mPopMovieServer.getPersons(page, this);
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
