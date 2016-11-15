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

import com.mikepenz.aboutlibraries.Libs;
import com.mikepenz.aboutlibraries.LibsBuilder;

import java.util.HashMap;
import java.util.Map;

import br.com.tiagohs.popmovies.App;
import br.com.tiagohs.popmovies.PopMoviesComponent;
import br.com.tiagohs.popmovies.R;
import br.com.tiagohs.popmovies.model.dto.ListActivityDTO;
import br.com.tiagohs.popmovies.util.MovieUtils;
import br.com.tiagohs.popmovies.util.ServerUtils;
import br.com.tiagohs.popmovies.util.enumerations.ListType;
import br.com.tiagohs.popmovies.util.enumerations.Param;
import br.com.tiagohs.popmovies.util.enumerations.ParamSortBy;
import br.com.tiagohs.popmovies.util.enumerations.Sort;
import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Nullable @BindView(R.id.toolbar)             Toolbar mToolbar;
    @BindView(R.id.coordenation_layout)           CoordinatorLayout coordinatorLayout;
    @Nullable @BindView(R.id.drawer_layout)       DrawerLayout mDrawerLayout;
    @Nullable @BindView(R.id.nav_view)            NavigationView mNavigationView;

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
        Map<String, String> parameters;

        switch (item.getItemId()) {
            case R.id.menu_generos:
                startActivity(GenresActivity.newIntent(this));
                return true;
            case R.id.menu_atores:
                startActivity(ListsDefaultActivity.newIntent(this, new ListActivityDTO("Atores e Atrizes", R.layout.item_list_movies, Sort.PERSON_POPULAR, ListType.PERSON)));
                return true;
            case R.id.menu_lancamentos:
                parameters = new HashMap<>();
                parameters.put(Param.PRIMARY_RELEASE_DATE_GTE.getParam(), MovieUtils.getDateBefore(30));
                parameters.put(Param.PRIMARY_RELEASE_DATE_LTE.getParam(), MovieUtils.getDateToday());
                parameters.put(Param.SORT_BY.getParam(), ParamSortBy.POPULARITY_DESC.getValue());

                startActivity(ListsDefaultActivity.newIntent(this, new ListActivityDTO(0, getString(R.string.menu_lancamentos), Sort.DISCOVER, R.layout.item_list_movies, ListType.MOVIES), parameters));
                return true;
            case R.id.menu_bem_avaliados:
                parameters = new HashMap<>();
                parameters.put(Param.VOTE_AVERAGE_GTE.getParam(), String.valueOf(6.5));

                startActivity(ListsDefaultActivity.newIntent(this, new ListActivityDTO(0, getString(R.string.mais_bem_avaliados), Sort.DISCOVER, R.layout.item_list_movies, ListType.MOVIES), parameters));
                return true;
            case R.id.menu_sobre:
                new LibsBuilder()
                        //provide a style (optional) (LIGHT, DARK, LIGHT_DARK_TOOLBAR)
                        .withActivityStyle(Libs.ActivityStyle.LIGHT_DARK_TOOLBAR)
                        .withAboutIconShown(true)
                        .withAboutVersionShown(true)
                        .withAboutAppName(getString(R.string.app_name))
                        .withActivityTitle(getString(R.string.sobre_title_actionbar))
                        .withAboutDescription(getString(R.string.sobre_descricao))
                        //start the activity
                        .start(this);
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

    protected PopMoviesComponent getApplicationComponent() {
        return ((App) getApplication()).getPopMoviesComponent();
    }

    public void onError(String msg) {
        mSnackbar = Snackbar
                .make(getCoordinatorLayout(), msg, Snackbar.LENGTH_INDEFINITE);

        mSnackbar.setActionTextColor(Color.RED);
        mSnackbar.show();
        mSnackbar.setAction(getString(R.string.tentar_novamente), onSnackbarClickListener());

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
