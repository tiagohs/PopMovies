package br.com.tiagohs.popmovies.interceptor;

import com.android.volley.VolleyError;

import br.com.tiagohs.popmovies.model.person.PersonFind;
import br.com.tiagohs.popmovies.model.response.GenericListResponse;
import br.com.tiagohs.popmovies.util.enumerations.Sort;

public interface PersonInterceptor {

    interface onPersonListener {

        void onPersonRequestSucess(GenericListResponse<PersonFind> personsResponse);
        void onPersonRequestError(VolleyError error);
    }

    void getPersons(int page,  String tag, Sort sort);
}
