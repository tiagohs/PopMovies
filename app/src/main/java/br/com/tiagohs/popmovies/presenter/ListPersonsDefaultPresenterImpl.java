package br.com.tiagohs.popmovies.presenter;

import android.app.Activity;
import android.view.View;

import com.android.volley.VolleyError;

import br.com.tiagohs.popmovies.App;
import br.com.tiagohs.popmovies.R;
import br.com.tiagohs.popmovies.interceptor.PersonInterceptor;
import br.com.tiagohs.popmovies.interceptor.PersonInterceptorImpl;
import br.com.tiagohs.popmovies.model.person.PersonFind;
import br.com.tiagohs.popmovies.model.response.GenericListResponse;
import br.com.tiagohs.popmovies.util.DTOUtils;
import br.com.tiagohs.popmovies.util.enumerations.Sort;
import br.com.tiagohs.popmovies.view.ListPersonsDefaultView;

public class ListPersonsDefaultPresenterImpl implements ListPersonsDefaultPresenter, PersonInterceptor.onPersonListener {

    private ListPersonsDefaultView mView;
    private PersonInterceptor mPersonInterceptor;

    private int mCurrentPage;
    private int mTotalPages;
    private String mTag;

    public ListPersonsDefaultPresenterImpl() {
        mPersonInterceptor = new PersonInterceptorImpl(this);
    }

    @Override
    public void setView(ListPersonsDefaultView view) {
        mView = view;
    }

    public void getPersons(Sort personSort, String tag) {
        mTag = tag;

        if (mView.isInternetConnected())
            mPersonInterceptor.getPersons(++mCurrentPage, mTag, personSort);

    }

    @Override
    public void onPersonRequestSucess(GenericListResponse<PersonFind> personsResponse) {
        mCurrentPage = personsResponse.getPage();
        mTotalPages = personsResponse.getTotalPage();

        if (isFirstPage()) {
            mView.setListMovies(DTOUtils.createPersonListDTO(personsResponse.getResults()), hasMorePages());
            mView.setupRecyclerView();
        } else {
            mView.addAllMovies(DTOUtils.createPersonListDTO(personsResponse.getResults()), hasMorePages());
            mView.updateAdapter();
        }

        mView.setProgressVisibility(View.GONE);
        mView.setRecyclerViewVisibility(View.VISIBLE);
    }

    private boolean isFirstPage() {
        return mCurrentPage == 1;
    }

    private boolean hasMorePages() {
        return mCurrentPage < mTotalPages;
    }

    @Override
    public void onPersonRequestError(VolleyError error) {
        if (mView.isAdded())
            mView.onErrorInServer();
    }
}