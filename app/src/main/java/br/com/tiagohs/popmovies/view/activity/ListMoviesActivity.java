package br.com.tiagohs.popmovies.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import java.util.List;

import br.com.tiagohs.popmovies.R;
import br.com.tiagohs.popmovies.model.Movie;
import br.com.tiagohs.popmovies.model.TabFragments;
import br.com.tiagohs.popmovies.view.fragment.ListMoviesFragment;
import butterknife.BindView;

public class ListMoviesActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, ListMoviesFragment.Callbacks {
    private static final String TAG = ListMoviesActivity.class.getSimpleName();

    public static final String POSTER_MOVIE_TRANSACTION = "br.com.tiagohs.popmovies.poster_movie";

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.tab_home)
    TabLayout mTabHome;

    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    @BindView(R.id.nav_view)
    NavigationView mNavigationView;

    @BindView(R.id.pager)
    ViewPager mViewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        configurarDrawerLayout();
        mNavigationView.setNavigationItemSelectedListener(this);

        String[] tabs = new String[]{getString(R.string.tab_populares),
                                    getString(R.string.tab_lancamentos),
                                    getString(R.string.tab_favoritos)};

        mViewPager.setAdapter(new ListMovieAdapter(getSupportFragmentManager(), tabs));
        mTabHome.setupWithViewPager(mViewPager);

    }

    private void configurarDrawerLayout() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_movie, menu);
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        return false;
    }

    @Override
    public void onMovieSelected(Movie movie, ImageView posterMovie) {
        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(this, posterMovie, POSTER_MOVIE_TRANSACTION);
        startActivity(MovieDetailActivity.newIntent(this, movie), options.toBundle());
    }

    private class ViewPagerAdapter extends FragmentStatePagerAdapter {
        private List<TabFragments> mListFragments;

        public ViewPagerAdapter(FragmentManager fm, List<TabFragments> listFragments) {
            super(fm);
            this.mListFragments = listFragments;
        }

        public Fragment getItem(int num) {
            return mListFragments.get(num).getFragment();
        }

        @Override
        public int getCount() {
            return mListFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mListFragments.get(position).getTabName();
        }

    }
}
