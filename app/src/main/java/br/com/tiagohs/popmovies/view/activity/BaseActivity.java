package br.com.tiagohs.popmovies.view.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;

import br.com.tiagohs.popmovies.App;
import br.com.tiagohs.popmovies.PopMoviesComponent;
import br.com.tiagohs.popmovies.R;
import br.com.tiagohs.popmovies.util.ServerUtils;
import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Nullable @BindView(R.id.toolbar)             Toolbar mToolbar;
    @BindView(R.id.coordenation_layout) CoordinatorLayout coordinatorLayout;

    @Nullable @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    @Nullable @BindView(R.id.nav_view)
    NavigationView mNavigationView;

    protected MaterialDialog materialDialog;
    protected Snackbar mSnackbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getActivityBaseViewID());

        onSetupActionBar();

        mNavigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);

        injectViews();
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_generos:
                startActivity(GenresActivity.newIntent(this));
                return true;
            default:
                return false;
        }

    }

    private void onSetupActionBar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private void injectViews() {
        ButterKnife.bind(this);
    }

    protected void showDialogProgress() {
        materialDialog = new MaterialDialog.Builder(this)
                .content(getString(R.string.progress_wait))
                .cancelable(false)
                .progress(true, 0)
                .show();
    }

    protected void hideDialogProgress() {
        materialDialog.dismiss();
    }

    protected PopMoviesComponent getApplicationComponent() {
        return ((App) getApplication()).getPopMoviesComponent();
    }

    public void onError(String msg) {
        mSnackbar = Snackbar
                .make(getCoordinatorLayout(), msg, Snackbar.LENGTH_INDEFINITE);

        mSnackbar.setActionTextColor(Color.RED);
        mSnackbar.setAction(getString(R.string.tentar_novamente), onSnackbarClickListener());

        mSnackbar.show();
    }

    protected abstract View.OnClickListener onSnackbarClickListener();

    public boolean isInternetConnected() {
        return ServerUtils.isNetworkConnected(this);
    }

    protected abstract int getActivityBaseViewID();

    public CoordinatorLayout getCoordinatorLayout() {
        return coordinatorLayout;
    }
}
