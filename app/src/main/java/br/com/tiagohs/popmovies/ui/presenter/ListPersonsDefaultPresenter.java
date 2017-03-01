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

public class ListPersonsDefaultPresenter extends BasePresenter<ListPersonsDefaultContract.ListPersonsDefaultView, ListPersonsDefaultContract.ListPersonsDefaultInterceptor> implements ListPersonsDefaultContract.ListPersonsDefaultPresenter {

    private int mCurrentPage;
    private int mTotalPages;

    public ListPersonsDefaultPresenter(ListPersonsDefaultContract.ListPersonsDefaultInterceptor listPersonsDefaultInterceptor, CompositeDisposable subscribes) {
        super(listPersonsDefaultInterceptor, subscribes);
    }

    public void getPersons() {

        if (mView.isInternetConnected()) {
            mInterceptor.getPersons(++mCurrentPage)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(onObserve());
        } else
            noConnectionError();

    }

    public Observer<GenericListResponse<PersonFind>> onObserve() {
        return new Observer<GenericListResponse<PersonFind>>() {
            @Override
            public void onSubscribe(Disposable d) {
                mSubscribers.add(d);
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
            public void onComplete() {}
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
