package br.com.tiagohs.popmovies.view.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import br.com.tiagohs.popmovies.model.Movie;

/**
 * Created by Tiago Henrique on 22/08/2016.
 */
public class MovieDetailsAdapter extends FragmentStatePagerAdapter {
    private static final int TAB_RESUMO = 0;
    private static final int TAB_TECNICO = 1;
    private static final int TAB_REVIEWS = 2;

    private Movie mMovie;
    private String[] mTabsNames;

    public MovieDetailsAdapter(FragmentManager fm, Movie movie, String[] tabNames) {
        super(fm);

        this.mMovie = movie;
        this.mTabsNames = tabNames;
    }

    @Override
    public Fragment getItem(int tabSelecionada) {

        switch (tabSelecionada) {
            case TAB_RESUMO:
                return MovieDetailsOverviewFragment.newInstance(mMovie);
            case TAB_TECNICO:
                return MovieDetailsOverviewFragment.newInstance(mMovie);
            case TAB_REVIEWS:
                return MovieDetailsOverviewFragment.newInstance(mMovie);
            default:
                return MovieDetailsOverviewFragment.newInstance(mMovie);
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
}
