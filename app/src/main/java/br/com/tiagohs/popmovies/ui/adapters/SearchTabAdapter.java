package br.com.tiagohs.popmovies.ui.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import br.com.tiagohs.popmovies.ui.view.fragment.SearchMoviesFragment;
import br.com.tiagohs.popmovies.ui.view.fragment.SearchPersonsFragment;

/**
 * Created by Tiago on 14/01/2017.
 */

public class SearchTabAdapter extends FragmentPagerAdapter {
    private static final int TAB_MOVIE = 0;
    private static final int TAB_PERSON = 1;

    private String[] mTabsNames;

    private Fragment mSearchMovieFragment;
    private Fragment mSearchPersonFragment;

    public SearchTabAdapter(FragmentManager fm, String[] tabNames) {
        super(fm);

        mTabsNames = tabNames;
        mSearchMovieFragment = SearchMoviesFragment.newInstace();
    }

    @Override
    public Fragment getItem(int tabSelecionada) {

        switch (tabSelecionada) {
            case TAB_MOVIE:
                return mSearchMovieFragment;
            case TAB_PERSON:
                mSearchPersonFragment = SearchPersonsFragment.newInstace();
                return mSearchPersonFragment;
            default:
                mSearchMovieFragment = SearchMoviesFragment.newInstace();
                return mSearchMovieFragment;
        }

    }

    @Override
    public int getCount() {
        return mTabsNames.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTabsNames[position];
    }

    public Fragment getCurrentFragment(int position) {
        if (position == TAB_MOVIE)
            return mSearchMovieFragment;
        else
            return mSearchPersonFragment;
    }
}
