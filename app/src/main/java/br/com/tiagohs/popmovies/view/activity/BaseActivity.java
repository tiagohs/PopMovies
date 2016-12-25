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
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.mikepenz.aboutlibraries.Libs;
import com.mikepenz.aboutlibraries.LibsBuilder;
import com.pnikosis.materialishprogress.ProgressWheel;

import com.mikepenz.aboutlibraries.Libs;
import com.mikepenz.aboutlibraries.LibsBuilder;

import java.util.HashMap;
import java.util.Map;

import br.com.tiagohs.popmovies.App;
import br.com.tiagohs.popmovies.PopMoviesComponent;
import br.com.tiagohs.popmovies.R;
import br.com.tiagohs.popmovies.data.repository.ProfileRepository;
import br.com.tiagohs.popmovies.model.db.ProfileDB;
import br.com.tiagohs.popmovies.model.dto.ListActivityDTO;
import br.com.tiagohs.popmovies.util.ImageUtils;
import br.com.tiagohs.popmovies.util.MovieUtils;
import br.com.tiagohs.popmovies.util.PrefsUtils;
import br.com.tiagohs.popmovies.util.ServerUtils;
import br.com.tiagohs.popmovies.util.enumerations.ImageSize;
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
    private ProfileRepository mProfileRepository;

    public BaseActivity() {
        mProfileRepository = new ProfileRepository(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getActivityBaseViewID());

        onSetupActionBar();

        mNavigationView.setNavigationItemSelectedListener(this);
        initNavigationDrawer();
    }

    private void initNavigationDrawer() {
        View view = mNavigationView.getHeaderView(0);
        ImageView fotoPerfil = (ImageView) view.findViewById(R.id.image_perfil);
        TextView nomeUsuario = (TextView) view.findViewById(R.id.nome_usuario);
        TextView emailUsuario = (TextView) view.findViewById(R.id.email_usuario);
        ProgressWheel progress = (ProgressWheel) view.findViewById(R.id.progress);

        ProfileDB profileDB = PrefsUtils.getCurrentProfile(this);
        ImageUtils.load(this, profileDB.getFotoPath(), R.drawable.placeholder_images_default, R.drawable.placeholder_images_default,  fotoPerfil, progress);
        nomeUsuario.setText(profileDB.getUser().getNome());
        emailUsuario.setText(profileDB.getUser().getEmail());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(PerfilActivity.newIntent(BaseActivity.this));
            }
        });
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);

        injectViews();
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_assistidos:
                startActivity(ListsDefaultActivity.newIntent(this, new ListActivityDTO(0, getString(R.string.assistidos), Sort.ASSISTIDOS, R.layout.item_list_movies, ListType.MOVIES)));
                return true;
            case R.id.menu_generos:
                startActivity(GenresActivity.newIntent(this));
                return true;
            case R.id.menu_atores:
                startActivity(ListsDefaultActivity.newIntent(this, new ListActivityDTO(getString(R.string.atores_atrizes), R.layout.item_list_movies, Sort.PERSON_POPULAR, ListType.PERSON)));
                return true;
            case R.id.menu_inicio:
                startActivity(HomeActivity.newIntent(this));
                return true;
            case R.id.menu_buscar:
                startActivity(SearchActivity.newIntent(this));
                return true;
            case R.id.menu_sobre:
                new LibsBuilder()
                        .withActivityStyle(Libs.ActivityStyle.LIGHT_DARK_TOOLBAR)
                        .withAboutIconShown(true)
                        .withAboutVersionShown(true)
                        .withAboutAppName(getString(R.string.app_name))
                        .withActivityTitle(getString(R.string.sobre_title_actionbar))
                        .withAboutDescription(getString(R.string.sobre_descricao))
                        //start the activity
                        .start(this);
                return true;
            case R.id.menu_sair:
                PrefsUtils.clearCurrentUser(this);
                PrefsUtils.clearCurrentProfile(this);
                LoginManager.getInstance().logOut();
                startActivity(LoginActivity.newIntent(this));
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
