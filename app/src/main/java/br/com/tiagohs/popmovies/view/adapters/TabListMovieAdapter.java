package br.com.tiagohs.popmovies.view.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.HashMap;
import java.util.Map;

import br.com.tiagohs.popmovies.view.fragment.ListMoviesFragment;


public class TabListMovieAdapter extends FragmentStatePagerAdapter {
    private static final int TAB_SIZE = 2;
    private static final int TAB_POPULARES = 0;
    private static final int TAB_FAVORITOS = 1;

    private Map<Integer, Fragment> mTabList;

    public TabListMovieAdapter(FragmentManager fm) {
        super(fm);

        mTabList = new HashMap<>();
    }

    @Override
    public Fragment getItem(int tabSelecionada) {

        switch(tabSelecionada) {
            case TAB_POPULARES:
                return ListMoviesFragment.newInstance(ListMoviesFragment.Sort.POPULARES);
            case TAB_FAVORITOS:
                return ListMoviesFragment.newInstance(ListMoviesFragment.Sort.FAVORITOS);

            default:
                return ListMoviesFragment.newInstance(ListMoviesFragment.Sort.POPULARES);
        }
    }

    @Override
    public int getCount() {
        return TAB_SIZE;
    }

}
