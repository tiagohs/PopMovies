package br.com.tiagohs.popmovies.interceptor;

import com.android.volley.VolleyError;

import br.com.tiagohs.popmovies.model.credits.MediaBasic;
import br.com.tiagohs.popmovies.model.response.GenericListResponse;
import br.com.tiagohs.popmovies.util.enumerations.SearchType;

public interface SearchPersonInterceptor {

    interface onSearchPersonListener {

        void onSearchPersonRequestSucess(GenericListResponse<MediaBasic> imageResponse);
        void onSearchPersonRequestError(VolleyError error);
    }

    void searchPersons(String query,
                       Boolean includeAdult,
                       SearchType searchType,
                       Integer currentPage);
}
