package br.com.tiagohs.popmovies.view.activity;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.afollestad.materialdialogs.MaterialDialog;

import br.com.tiagohs.popmovies.App;
import br.com.tiagohs.popmovies.PopMoviesComponent;
import br.com.tiagohs.popmovies.R;
import br.com.tiagohs.popmovies.util.ServerUtils;
import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)             Toolbar mToolbar;
    @BindView(R.id.coordenation_layout) CoordinatorLayout coordinatorLayout;

    protected MaterialDialog materialDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getActivityBaseViewID());
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);

        injectViews();
        onSetupActionBar();
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

    protected boolean isInternetConnected() {
        return ServerUtils.isNetworkConnected(this);
    }

    protected abstract int getActivityBaseViewID();

    public CoordinatorLayout getCoordinatorLayout() {
        return coordinatorLayout;
    }
}
