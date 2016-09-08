package br.com.tiagohs.popmovies.view;

public interface BaseView {

    void setProgressVisibility(int visibityState);
    void setBackgroundNoConnectionImageVisibility(int visibilityState);

    boolean isInternetConnected();
    void onError(String msg);
}
