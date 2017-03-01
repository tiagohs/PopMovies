package br.com.tiagohs.popmovies.ui.presenter;

import android.view.View;

import java.util.List;

import br.com.tiagohs.popmovies.ui.contracts.ListPersonsDefaultContract;
import br.com.tiagohs.popmovies.model.person.PersonFind;
import br.com.tiagohs.popmovies.model.response.GenericListResponse;
import br.com.tiagohs.popmovies.util.DTOUtils;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class ListPersonsDefaultPresenter implements ListPersonsDefaultContract.ListPersonsDefaultPresenter {

    private ListPersonsDefaultContract.ListPersonsDefaultView mView;
    private ListPersonsDefaultContract.ListPersonsDefaultInterceptor mInterceptor;

    private int mCurrentPage;
    private int mTotalPages;

    private CompositeDisposable mSubscribes;

    public ListPersonsDefaultPresenter(ListPersonsDefaultContract.ListPersonsDefaultInterceptor listPersonsDefaultInterceptor, CompositeDisposable subscribes) {
        mInterceptor = listPersonsDefaultInterceptor;
        mSubscribes = subscribes;
    }

    @Override
    public void onBindView(ListPersonsDefaultContract.ListPersonsDefaultView view) {
        mView = view;
    }

    @Override
    public void onUnbindView() {
        mView = null;
        mSubscribes.clear();
    }

    public void getPersons() {

        if (mView.isInternetConnected()) {
            mInterceptor.getPersons(++mCurrentPage)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(onObserve());
        } else
            noConnectionError();

    }

    private void noConnectionError() {

        if (mView.isAdded())
            mView.onErrorNoConnection();

        mView.setProgressVisibility(View.GONE);

        if (mCurrentPage == 0) {
            mView.setRecyclerViewVisibility(View.GONE);
        }
    }

    public Observer<GenericListResponse<PersonFind>> onObserve() {
        return new Observer<GenericListResponse<PersonFind>>() {
            @Override
            public void onSubscribe(Disposable d) {
                mSubscribes.add(d);
            }

            @Override
            public void onNext(GenericListResponse<PersonFind> personsResponse) {
                mCurrentPage = personsResponse.getPage();
                mTotalPages = personsResponse.getTotalPage();

                onPersonsSucess(personsResponse.getResults());
            }

            @Override
            public void onError(Throwable e) {
                mView.onErrorInServer();
            }

            @Override
            public void onComplete() {

            }
        };

    }

    private void onPersonsSucess(List<PersonFind> persons) {

        if (mView.isAdded()) {
            if (isFirstPage()) {
                mView.setListMovies(DTOUtils.createPersonListDTO(persons), hasMorePages());
                mView.setupRecyclerView();
            } else {
                mView.addAllMovies(DTOUtils.createPersonListDTO(persons), hasMorePages());
                mView.updateAdapter();
            }

            mView.setProgressVisibility(View.GONE);
            mView.setRecyclerViewVisibility(View.VISIBLE);
        }
    }

    private boolean isFirstPage() {
        return mCurrentPage == 1;
    }

    private boolean hasMorePages() {
        return mCurrentPage < mTotalPages;
    }

}
