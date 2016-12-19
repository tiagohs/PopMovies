package br.com.tiagohs.popmovies.view.activity;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import br.com.tiagohs.popmovies.R;
import br.com.tiagohs.popmovies.model.dto.ListActivityDTO;
import br.com.tiagohs.popmovies.view.callbacks.ListMoviesCallbacks;
import br.com.tiagohs.popmovies.view.fragment.HomeFragment;

public class HomeActivity extends BaseActivity implements ListMoviesCallbacks {

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, HomeActivity.class);
        return intent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        configurarDrawerLayout();

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.content_fragment);

        if (fragment == null) {
            fm.beginTransaction()
                    .add(R.id.content_fragment, HomeFragment.newInstance())
                    .commit();
        }
    }

    private void configurarDrawerLayout() {
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
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
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected View.OnClickListener onSnackbarClickListener() {
        return null;
    }

    @Override
    protected int getActivityBaseViewID() {
        return R.layout.activity_home;
    }

    @Override
    public void onMovieSelected(int movieID, ImageView imageView) {

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions transitionActivityOptions = ActivityOptions
                    .makeSceneTransitionAnimation(HomeActivity.this, imageView, getString(R.string.poster_movie));
            startActivity(MovieDetailActivity.newIntent(this, movieID), transitionActivityOptions.toBundle());
        } else {
            startActivity(MovieDetailActivity.newIntent(this, movieID));
        }
    }
}
