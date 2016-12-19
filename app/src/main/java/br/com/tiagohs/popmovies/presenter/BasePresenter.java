package br.com.tiagohs.popmovies.presenter;

import android.app.Activity;

/**
 * Created by Tiago Henrique on 28/08/2016.
 */
public interface BasePresenter<T> {

    void setView(T view);
    void onCancellRequest(Activity activity, String tag);
}
