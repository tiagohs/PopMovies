package br.com.tiagohs.popmovies.presenter;

import android.content.Context;

import br.com.tiagohs.popmovies.view.PerfilEstatisticasView;

public interface PerfilEstatisticasPresenter extends BasePresenter<PerfilEstatisticasView> {

    void initUpdates();
    void setContext(Context context);
}
