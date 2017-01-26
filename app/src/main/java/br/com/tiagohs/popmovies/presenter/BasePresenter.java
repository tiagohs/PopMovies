package br.com.tiagohs.popmovies.presenter;

import android.app.Activity;

public interface BasePresenter<T> {

    void setView(T view);
    void onCancellRequest(Activity activity, String tag);
}
