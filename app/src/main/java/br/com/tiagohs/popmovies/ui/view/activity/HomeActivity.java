package br.com.tiagohs.popmovies.ui.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.View;
import android.widget.ImageView;

import br.com.tiagohs.popmovies.R;
import br.com.tiagohs.popmovies.ui.callbacks.ListMoviesCallbacks;
import br.com.tiagohs.popmovies.ui.view.fragment.HomeFragment;
import br.com.tiagohs.popmovies.util.ViewUtils;

public class HomeActivity extends BaseActivity implements ListMoviesCallbacks {

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, HomeActivity.class);
        return intent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        onSetupDrawerLayout();
        startFragment(R.id.content_fragment, HomeFragment.newInstance());
    }

    private void onSetupDrawerLayout() {
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    @Override
    protected View.OnClickListener onSnackbarClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSnackbar.dismiss();
            }
        };
    }

    @Override
    protected int getMenuLayoutID() {
        return R.menu.menu_principal;
    }

    @Override
    protected int getActivityBaseViewID() {
        return R.layout.activity_default;
    }

    @Override
    public void onMovieSelected(int movieID, ImageView imageView) {
        ViewUtils.startMovieActivityWithTranslation(this, movieID, imageView, getString(R.string.poster_movie));
    }

}
