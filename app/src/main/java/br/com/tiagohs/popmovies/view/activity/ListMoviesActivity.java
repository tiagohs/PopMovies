package br.com.tiagohs.popmovies.view.activity;

import android.app.ActivityOptions;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import br.com.tiagohs.popmovies.R;
import br.com.tiagohs.popmovies.view.adapters.ListMovieAdapter;
import br.com.tiagohs.popmovies.view.fragment.ListMoviesFragment;
import butterknife.BindView;

public class ListMoviesActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, ListMoviesFragment.Callbacks {
    private static final String TAG = ListMoviesActivity.class.getSimpleName();

    public static final String POSTER_MOVIE_TRANSACTION = "br.com.tiagohs.popmovies.poster_movie";

    @BindView(R.id.tab_home)
    TabLayout mTabHome;

    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    @BindView(R.id.nav_view)
    NavigationView mNavigationView;

    @BindView(R.id.pager)
    ViewPager mViewPager;

    private final int[] tabIcons = new int[]{R.drawable.ic_tab_home,
                                             R.drawable.ic_tab_favorite};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        configurarDrawerLayout();
        mNavigationView.setNavigationItemSelectedListener(this);

        mViewPager.setAdapter(new ListMovieAdapter(getSupportFragmentManager()));
    }

    @Override
    protected void onStart() {
        super.onStart();

        setupTabs();
    }

    private void setupTabs() {
        mTabHome.setupWithViewPager(mViewPager);
        mTabHome.getTabAt(0).setIcon(tabIcons[0]);
        mTabHome.getTabAt(1).setIcon(tabIcons[1]);
    }

    @Override
    protected int getActivityBaseViewID() {
        return R.layout.activity_main;
    }

    private void configurarDrawerLayout() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {
            case R.id.action_procurar:
                startActivity(SearchActivity.newIntent(this));
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        return false;
    }

    @Override
    public void onMovieSelected(int movieID, ImageView posterMovie) {

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions transitionActivityOptions = ActivityOptions
                                                            .makeSceneTransitionAnimation(ListMoviesActivity.this, posterMovie, getString(R.string.poster_movie));
            startActivity(MovieDetailActivity.newIntent(this, movieID), transitionActivityOptions.toBundle());
        } else {
            startActivity(MovieDetailActivity.newIntent(this, movieID));
        }

    }

}
