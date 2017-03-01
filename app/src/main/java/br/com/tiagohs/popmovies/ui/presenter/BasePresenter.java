package br.com.tiagohs.popmovies.ui.presenter;

import android.view.View;

import br.com.tiagohs.popmovies.ui.view.IView;
import br.com.tiagohs.popmovies.util.EmptyUtils;
import io.reactivex.disposables.CompositeDisposable;

public abstract class BasePresenter<V extends IView, I> implements IPresenter<V> {

    protected V mView;
    protected I mInterceptor;

    protected CompositeDisposable mSubscribers;

    protected BasePresenter(I interceptor, CompositeDisposable compositeDisposable) {
        mSubscribers = compositeDisposable;
        mInterceptor = interceptor;
    }

    protected BasePresenter() {}

    @Override
    public void onBindView(V view) {
        mView = view;
    }

    @Override
    public void onUnbindView() {
        if (EmptyUtils.isNotNull(mView))
            mView = null;

        if (EmptyUtils.isNotNull(mSubscribers))
            mSubscribers.clear();

    }

    protected void noConnectionError() {

        if (mView.isAdded()) {
            mView.onErrorNoConnection();
            mView.setProgressVisibility(View.GONE);
        }

    }

}
