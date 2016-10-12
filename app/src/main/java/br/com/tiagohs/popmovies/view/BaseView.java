package br.com.tiagohs.popmovies.view;

public interface BaseView {

    void setProgressVisibility(int visibityState);

    boolean isInternetConnected();
    void onError(String msg);
    boolean isAdded();
}
