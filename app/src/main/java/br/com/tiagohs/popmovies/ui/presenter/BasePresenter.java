package br.com.tiagohs.popmovies.ui.presenter;

public interface BasePresenter<T> {

    void onBindView(T view);
    void onUnbindView();
}
