package br.com.tiagohs.popmovies.ui.presenter;

import br.com.tiagohs.popmovies.ui.view.IView;

public interface IPresenter<V extends IView> {

    void onBindView(V view);
    void onUnbindView();
}
