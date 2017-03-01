package br.com.tiagohs.popmovies.ui.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import br.com.tiagohs.popmovies.ui.view.fragment.PerfilEstatisticasFragment;
import br.com.tiagohs.popmovies.ui.view.fragment.PerfilFilmesFragment;

public class TabPerfilAdapter extends FragmentPagerAdapter {
    private static final int TAB_FILMES = 0;
    private static final int TAB_ESTATISTICAS = 1;

    public TabPerfilAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int tabSelecionada) {
        switch(tabSelecionada) {
            case TAB_FILMES:
                return PerfilFilmesFragment.newInstance();
            case TAB_ESTATISTICAS:
                return PerfilEstatisticasFragment.newInstance();
            default:
                return PerfilFilmesFragment.newInstance();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
