package br.com.tiagohs.popmovies.view.fragment;

import android.view.View;

import br.com.tiagohs.popmovies.R;

/**
 * Created by Tiago on 18/12/2016.
 */

public class PerfilEstatisticasFragment extends BaseFragment {

    public static PerfilEstatisticasFragment newInstance() {
        PerfilEstatisticasFragment perfilEstatisticasFragment = new PerfilEstatisticasFragment();
        return perfilEstatisticasFragment;
    }

    @Override
    protected int getViewID() {
        return R.layout.fragment_perfil_estatisticas;
    }

    @Override
    protected View.OnClickListener onSnackbarClickListener() {
        return null;
    }
}
