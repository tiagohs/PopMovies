package br.com.tiagohs.popmovies.view.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.HashMap;
import java.util.Map;

import br.com.tiagohs.popmovies.view.fragment.ListMoviesFragment;


public class ListMovieAdapter extends FragmentStatePagerAdapter {
    private static final int TAB_SIZE = 2;
    private static final int TAB_POPULARES = 0;
    private static final int TAB_FAVORITOS = 1;

    private Map<Integer, Fragment> mTabList;

    public ListMovieAdapter(FragmentManager fm) {
        super(fm);

        mTabList = new HashMap<>();
    }

    @Override
    public Fragment getItem(int tabSelecionada) {
        Fragment fragmentSelecionado = null;

        if (mTabList.containsKey(tabSelecionada))
            return mTabList.get(tabSelecionada);

        switch(tabSelecionada) {
            case TAB_POPULARES:
                fragmentSelecionado = ListMoviesFragment.newInstance(ListMoviesFragment.Sort.POPULARES);
                break;
            case TAB_FAVORITOS:
                fragmentSelecionado = ListMoviesFragment.newInstance(ListMoviesFragment.Sort.FAVORITOS);
                break;
            default:
                fragmentSelecionado = ListMoviesFragment.newInstance(ListMoviesFragment.Sort.POPULARES);
        }

        mTabList.put(tabSelecionada, fragmentSelecionado);
        return fragmentSelecionado;
    }

    @Override
    public int getCount() {
        return TAB_SIZE;
    }

}
