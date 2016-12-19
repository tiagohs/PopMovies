package br.com.tiagohs.popmovies.presenter;

import android.content.Context;

import br.com.tiagohs.popmovies.view.MovieDetailsView;
import br.com.tiagohs.popmovies.view.PerfilView;

/**
 * Created by Tiago on 18/12/2016.
 */

public interface PerfilPresenter extends BasePresenter<PerfilView> {

    void setContext(Context context);
    void initUpdates(String tag);
}
