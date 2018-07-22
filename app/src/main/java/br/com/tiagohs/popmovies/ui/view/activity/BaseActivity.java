package br.com.tiagohs.popmovies.ui.view.activity;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsIntent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.mikepenz.aboutlibraries.Libs;
import com.mikepenz.aboutlibraries.LibsBuilder;
import com.pnikosis.materialishprogress.ProgressWheel;

import br.com.tiagohs.popmovies.App;
import br.com.tiagohs.popmovies.R;
import br.com.tiagohs.popmovies.dragger.components.PopMoviesComponent;
import br.com.tiagohs.popmovies.model.db.ProfileDB;
import br.com.tiagohs.popmovies.model.db.UserDB;
import br.com.tiagohs.popmovies.model.dto.ListActivityDTO;
import br.com.tiagohs.popmovies.util.EmptyUtils;
import br.com.tiagohs.popmovies.util.ImageUtils;
import br.com.tiagohs.popmovies.util.PrefsUtils;
import br.com.tiagohs.popmovies.util.ServerUtils;
import br.com.tiagohs.popmovies.util.ViewUtils;
import br.com.tiagohs.popmovies.util.enumerations.ListType;
import br.com.tiagohs.popmovies.util.enumerations.Sort;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class

BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Nullable @BindView(R.id.toolbar)             Toolbar mToolbar;
    @Nullable @BindView(R.id.coordenation_layout) CoordinatorLayout mCoordinatorLayout;
    @Nullable @BindView(R.id.drawer_layout)       DrawerLayout mDrawerLayout;
    @Nullable @BindView(R.id.nav_view)            NavigationView mNavigationView;

    protected Unbinder mBinder;

    protected Snackbar mSnackbar;
    protected ProfileDB mProfileDB;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getActivityBaseViewID());

        mProfileDB = PrefsUtils.getCurrentProfile(this);
        mAuth = FirebaseAuth.getInstance();

        if (EmptyUtils.isNotNull(mToolbar))
            onSetupActionBar();

        onSetupNavigationDrawer();
    }

    private void onSetupNavigationDrawer() {
        if (EmptyUtils.isNotNull(mNavigationView)) {
            mNavigationView.setNavigationItemSelectedListener(this);
            View view = mNavigationView.getHeaderView(0);

            ImageView fotoPerfil = (ImageView) view.findViewById(R.id.image_circle);
            ImageView background = (ImageView) view.findViewById(R.id.background);
            TextView nomeUsuario = (TextView) view.findViewById(R.id.nome_usuario);
            TextView emailUsuario = (TextView) view.findViewById(R.id.email_usuario);
            ProgressWheel progress = (ProgressWheel) view.findViewById(R.id.progress_image_circle);

            if (mProfileDB.getUser().getTypePhoto() == UserDB.PHOTO_ONLINE)
                ImageUtils.load(this, mProfileDB.getUser().getPicturePath(), mProfileDB.getUser().getNome() == null ? mProfileDB.getUser().getEmail() : mProfileDB.getUser().getNome(), R.drawable.placeholder_images_default,  fotoPerfil, progress);
            else if (mProfileDB.getUser().getTypePhoto() == UserDB.PHOTO_LOCAL)
                fotoPerfil.setImageBitmap(ImageUtils.getBitmapFromPath(mProfileDB.getUser().getLocalPicture(), this));

            nomeUsuario.setText(mProfileDB.getUser().getNome() == null ? mProfileDB.getUser().getEmail() : mProfileDB.getUser().getNome());
            emailUsuario.setText(mProfileDB.getCountry());
            ImageUtils.loadWithBlur(this, R.drawable.background_image, background);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(PerfilActivity.newIntent(BaseActivity.this));
                }
            });
        }
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);

        injectViews();
    }

    private void injectViews() {
        mBinder = ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        ((App) getApplication()).clearCache();
    }

    public MaterialDialog showDialogProgress() {
        return new MaterialDialog.Builder(this)
                .content(getString(R.string.progress_wait))
                .cancelable(false)
                .progress(true, 0)
                .show();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.menu_perfil:
                startActivity(PerfilActivity.newIntent(BaseActivity.this));
                return true;
            case R.id.menu_assistidos:
                startActivity(ListsDefaultActivity.newIntent(this, new ListActivityDTO(0, getString(R.string.assistidos), Sort.ASSISTIDOS, R.layout.item_list_movies, ListType.MOVIES)));
                return true;
            case R.id.menu_generos:
                startActivity(GenresActivity.newIntent(this));
                return true;
            case R.id.menu_lancamentos:
                startActivity(LancamentosSemanaActivity.newIntent(this));
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
                createNewAboutActivity();
                return true;
            case R.id.menu_sair:
                PrefsUtils.clearCurrentUser(this);
                PrefsUtils.clearCurrentProfile(this);
                LoginManager.getInstance().logOut();
                mAuth.signOut();
                startActivity(LoginActivity.newIntent(this));
                return true;
            default:
                return false;
        }
    }

    private void onSignOut() {

    }

    private void createNewAboutActivity() {
        new LibsBuilder()
                .withActivityStyle(Libs.ActivityStyle.LIGHT_DARK_TOOLBAR)
                .withAboutIconShown(true)
                .withAboutVersionShown(true)
                .withAboutAppName(getString(R.string.app_name))
                .withActivityTitle(getString(R.string.menu_sobre))
                .withAboutDescription(getString(R.string.sobre_descricao))
                .start(this);
    }

    private void onSetupActionBar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (getMenuLayoutID() != 0)
            getMenuInflater().inflate(getMenuLayoutID(), menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {
            case R.id.menu_buscar:
                startActivity(SearchActivity.newIntent(this));
                return true;
            case android.R.id.home:
                finish();
                return true;
            case R.id.menu_sobre:
                createNewAboutActivity();
                return true;
            default:
                return false;
        }
    }

    protected boolean isTablet() {
        return getResources().getBoolean(R.bool.isTablet);
    }

    protected PopMoviesComponent getApplicationComponent() {
        return ((App) getApplication()).getPopMoviesComponent();
    }

    public void onError(int msgID) {
        mSnackbar = Snackbar
                .make(getCoordinatorLayout(), getString(msgID), Snackbar.LENGTH_LONG);

        mSnackbar.setActionTextColor(Color.RED);
        mSnackbar.show();
        mSnackbar.setAction(getString(R.string.tentar_novamente).toUpperCase(), onSnackbarClickListener());

    }

    public void onErrorNoConnection() {
        onError(R.string.error_no_internet);
    }

    public void onErrorInServer() {
        onError(R.string.error_no_server);
    }

    public void onErrorUnexpected() {
        onError(R.string.erro_unexpected);
    }

    public boolean isInternetConnected() {
        return ServerUtils.isNetworkConnected(this);
    }

    protected abstract int getActivityBaseViewID();

    public CoordinatorLayout getCoordinatorLayout() {
        return mCoordinatorLayout;
    }

    public void openUrl(String url) {

        if (!EmptyUtils.isEmpty(url)) {
            try {
                Uri urlUri = Uri.parse(url);
                CustomTabsIntent intent = new CustomTabsIntent.Builder()
                                                .setToolbarColor(ViewUtils.getColorFromResource(this, R.color.colorPrimary))
                                                .setShowTitle(true)
                                                .build();
                intent.launchUrl(this, urlUri);
            } catch (Exception ex) {

            }
        }
    }

    protected void startFragment(int fragmentID, Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        Fragment f = fm.findFragmentById(fragmentID);

        if (null == f) {
            fm.beginTransaction()
                    .add(fragmentID, fragment)
                    .commitAllowingStateLoss();
        } else {
            fm.beginTransaction()
                    .replace(fragmentID, fragment)
                    .commitAllowingStateLoss();
        }
    }

    protected void setActivityTitle(String title) {
        if (!EmptyUtils.isEmpty(title))
            mToolbar.setTitle(title);
    }

    protected void setActivitySubtitle(String subtitle) {
        if (!EmptyUtils.isEmpty(subtitle))
            mToolbar.setSubtitle(subtitle);
    }

    protected abstract View.OnClickListener onSnackbarClickListener();
    protected abstract int getMenuLayoutID();
}
