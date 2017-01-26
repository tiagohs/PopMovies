package br.com.tiagohs.popmovies.view;

public interface BaseView {

    void setProgressVisibility(int visibityState);

    boolean isInternetConnected();
    void onError(int msgID);
    boolean isAdded();
}
