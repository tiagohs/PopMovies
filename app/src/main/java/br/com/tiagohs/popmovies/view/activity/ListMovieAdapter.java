package br.com.tiagohs.popmovies.view.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import br.com.tiagohs.popmovies.view.fragment.ListMoviesFragment;


public class ListMovieAdapter extends FragmentStatePagerAdapter {
    private static final int TAB_POPULARES = 0;
    private static final int TAB_LANCAMENTOS = 1;
    private static final int TAB_FAVORITOS = 2;

    private String[] mTabNames;

    public ListMovieAdapter(FragmentManager fm, String[] tabNames) {
        super(fm);

        this.mTabNames = tabNames;
    }

    @Override
    public Fragment getItem(int tabSelecionada) {

        switch(tabSelecionada) {
            case TAB_POPULARES:
                return ListMoviesFragment.newInstance(ListMoviesFragment.Sort.POPULARES);
            case TAB_LANCAMENTOS:
                return ListMoviesFragment.newInstance(ListMoviesFragment.Sort.LANCAMENTOS);
            case TAB_FAVORITOS:
                return ListMoviesFragment.newInstance(ListMoviesFragment.Sort.FAVORITOS);
            default:
                return ListMoviesFragment.newInstance(ListMoviesFragment.Sort.POPULARES);
        }
    }

    @Override
    public int getCount() {
        return mTabNames.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTabNames[position];
    }
}
