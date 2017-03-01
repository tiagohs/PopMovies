package br.com.tiagohs.popmovies.ui.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import br.com.tiagohs.popmovies.model.movie.MovieDetails;
import br.com.tiagohs.popmovies.ui.view.fragment.MovieDetailsMidiaFragment;
import br.com.tiagohs.popmovies.ui.view.fragment.MovieDetailsOverviewFragment;


public class MovieDetailsAdapter extends FragmentPagerAdapter {
    private static final int TAB_RESUMO = 0;
    private static final int TAB_MIDIA = 1;

    private MovieDetails mMovie;
    private String[] mTabsNames;

    public MovieDetailsAdapter(FragmentManager fm, MovieDetails movie, String[] tabNames) {
        super(fm);

        this.mMovie = movie;
        this.mTabsNames = tabNames;
    }

    @Override
    public Fragment getItem(int tabSelecionada) {

        switch (tabSelecionada) {
            case TAB_RESUMO:
                return MovieDetailsOverviewFragment.newInstance(mMovie);

            case TAB_MIDIA:
                return MovieDetailsMidiaFragment.newInstance(mMovie);

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
