package br.com.tiagohs.popmovies.ui.view;

public interface IView {

    void setProgressVisibility(int visibityState);

    boolean isInternetConnected();
    void onError(int msgID);
    boolean isAdded();

    void onErrorNoConnection();
    void onErrorInServer();
    void onErrorUnexpected();
}
