package br.com.tiagohs.popmovies.interceptor;

import com.android.volley.VolleyError;

import br.com.tiagohs.popmovies.model.credits.MediaBasic;
import br.com.tiagohs.popmovies.model.response.GenericListResponse;
import br.com.tiagohs.popmovies.server.PopMovieServer;
import br.com.tiagohs.popmovies.server.ResponseListener;
import br.com.tiagohs.popmovies.util.enumerations.SearchType;

public class SearchPersonInterceptorImpl implements SearchPersonInterceptor, ResponseListener<GenericListResponse<MediaBasic>> {
    private PopMovieServer mPopMovieServer;
    private onSearchPersonListener mListener;

    public SearchPersonInterceptorImpl(onSearchPersonListener listener) {
        mPopMovieServer = PopMovieServer.getInstance();
        mListener = listener;
    }

    @Override
    public void searchPersons(String query, Boolean includeAdult, SearchType searchType, Integer currentPage) {
        mPopMovieServer.searchPerson(query, includeAdult, searchType, currentPage, this);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        mListener.onSearchPersonRequestError(error);
    }

    @Override
    public void onResponse(GenericListResponse<MediaBasic> response) {
        mListener.onSearchPersonRequestSucess(response);
    }
}
