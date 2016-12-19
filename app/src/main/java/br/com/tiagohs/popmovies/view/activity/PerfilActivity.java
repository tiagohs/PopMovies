package br.com.tiagohs.popmovies.view.activity;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.pnikosis.materialishprogress.ProgressWheel;

import java.util.Calendar;
import java.util.HashMap;

import javax.inject.Inject;

import br.com.tiagohs.popmovies.R;
import br.com.tiagohs.popmovies.data.repository.MovieRepository;
import br.com.tiagohs.popmovies.model.db.MovieDB;
import br.com.tiagohs.popmovies.model.db.ProfileDB;
import br.com.tiagohs.popmovies.model.dto.ListActivityDTO;
import br.com.tiagohs.popmovies.presenter.PerfilPresenter;
import br.com.tiagohs.popmovies.util.ImageUtils;
import br.com.tiagohs.popmovies.util.PrefsUtils;
import br.com.tiagohs.popmovies.util.ViewUtils;
import br.com.tiagohs.popmovies.util.enumerations.ImageSize;
import br.com.tiagohs.popmovies.util.enumerations.ListType;
import br.com.tiagohs.popmovies.util.enumerations.Sort;
import br.com.tiagohs.popmovies.view.AppBarMovieListener;
import br.com.tiagohs.popmovies.view.PerfilView;
import br.com.tiagohs.popmovies.view.callbacks.ListMoviesCallbacks;
import br.com.tiagohs.popmovies.view.fragment.PerfilFilmesFragment;
import br.com.tiagohs.popmovies.view.fragment.PerfilFragment;
import br.com.tiagohs.popmovies.view.fragment.PersonDetailFragment;
import butterknife.BindView;
import butterknife.OnClick;

public class PerfilActivity extends BaseActivity implements PerfilView, ListMoviesCallbacks {
    private static final String TAG = PerfilActivity.class.getSimpleName();

    @BindView(R.id.background_perfil)
    ImageView mBackgroundPerfil;

    @BindView(R.id.image_perfil)
    ImageView mImagePerfil;

    @BindView(R.id.name_perfil)
    TextView mNamePerfil;

    @BindView(R.id.email_perfil)
    TextView mEmailPerfil;

    @BindView(R.id.progress_perfil)
    ProgressBar mProgressPerfil;

    @BindView(R.id.progress_perfil_foto)
    ProgressWheel mProgressFotoPerfil;

    @BindView(R.id.btn_editar)
    FloatingActionButton mEditar;

    @BindView(R.id.total_anos)
    TextView mTotalAnosAssistidos;

    @BindView(R.id.label_total_anos)
    TextView mTotalAnosAssistidosLabel;

    @BindView(R.id.container_total_anos)
    LinearLayout mTotalAnosAssistidosContainer;

    @BindView(R.id.total_meses)
    TextView mTotalMesesAssistidos;

    @BindView(R.id.label_total_meses)
    TextView mTotalMesesAssistidosLabel;

    @BindView(R.id.container_total_meses)
    LinearLayout mTotalMesesAssistidosContainer;

    @BindView(R.id.total_dias)
    TextView mTotalDiasAssistidos;

    @BindView(R.id.label_total_dias)
    TextView mTotalDiasAssistidosLabel;

    @BindView(R.id.container_total_dias)
    LinearLayout mTotalDiasAssistidosContainer;

    @BindView(R.id.total_horas)
    TextView mTotalHorasAssistidos;

    @BindView(R.id.label_total_horas)
    TextView mTotalHorasAssistidosLabel;

    @BindView(R.id.container_total_horas)
    LinearLayout mTotalHorasAssistidosContainer;

    @BindView(R.id.total_minutos)
    TextView mTotalMinutosAssistidos;

    @BindView(R.id.label_total_minutos)
    TextView mTotalMinutosAssistidosLabel;

    @BindView(R.id.container_total_minutos)
    LinearLayout mTotalMinutosAssistidosContainer;

    @BindView(R.id.total_horas_riple)
    MaterialRippleLayout mTotalHorasRiple;

    @BindView(R.id.total_filmes)
    TextView mTotalFilmesAssistidos;

    @BindView(R.id.label_total_filmes)
    TextView mTotalFilmesAssistidosLabel;

    @BindView(R.id.dados_iniciais_container)
    LinearLayout mDadosIniciaisContainer;

    @BindView(R.id.perfil_app_bar)
    AppBarLayout mAppBarLayout;

    @Inject()
    PerfilPresenter mPerfilPresenter;

    private String mBackgrounPath;

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, PerfilActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getApplicationComponent().inject(this);

        mPerfilPresenter.setContext(this);
        mPerfilPresenter.setView(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        mPerfilPresenter.initUpdates(TAG);
    }

    public void setupTabs() {

        if (!isDestroyed()) {
            FragmentManager fm = getSupportFragmentManager();
            Fragment fragment = fm.findFragmentById(R.id.perfil_fragment);

            if (fragment == null) {
                fm.beginTransaction()
                        .add(R.id.perfil_fragment, PerfilFilmesFragment.newInstance())
                        .commit();
            }
        }

        mAppBarLayout.addOnOffsetChangedListener(onOffsetChangedListener());
    }

    public void setBackground(String backgroundPath) {
        mBackgrounPath = backgroundPath;

        if (!isDestroyed()) {
            mBackgroundPerfil.post(new Runnable() {
                @Override
                public void run() {
                    ImageUtils.loadWithRevealAnimation(PerfilActivity.this, mBackgrounPath, mBackgroundPerfil, R.drawable.ic_image_default_back, ImageSize.BACKDROP_780);
                }
            });
        }
        }

    private AppBarMovieListener onOffsetChangedListener() {
        return new AppBarMovieListener() {

            @Override
            public void onExpanded(AppBarLayout appBarLayout) {
                mToolbar.setBackground(ViewUtils.getDrawableFromResource(getApplicationContext(), R.drawable.background_action_bar_transparent));
                mToolbar.setTitle("");

                mDadosIniciaisContainer.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCollapsed(AppBarLayout appBarLayout) {
                mToolbar.setBackgroundColor(ViewUtils.getColorFromResource(getApplicationContext(), R.color.colorPrimary));
                mToolbar.setTitle(PrefsUtils.getCurrentUser(PerfilActivity.this).getNome());
                mNamePerfil.setVisibility(View.GONE);
                mEmailPerfil.setVisibility(View.GONE);
                mDadosIniciaisContainer.setVisibility(View.GONE);
            }

            @Override
            public void onIdle(AppBarLayout appBarLayout) {
                mToolbar.setBackground(ViewUtils.getDrawableFromResource(getApplicationContext(), R.drawable.background_action_bar_transparent));
                mToolbar.setTitle("");
                mNamePerfil.setVisibility(View.VISIBLE);
                mEmailPerfil.setVisibility(View.VISIBLE);
                mDadosIniciaisContainer.setVisibility(View.VISIBLE);
            }
        };
    }

    public void setNamePerfil(String nameUser) {
        mNamePerfil.setText(nameUser);
        mNamePerfil.setTypeface(Typeface.createFromAsset(getAssets(), "opensans.ttf"));
    }

    public void setEmailPerfil(String emailUser) {
        mEmailPerfil.setText(emailUser);
        mEmailPerfil.setTypeface(Typeface.createFromAsset(getAssets(), "opensans.ttf"));
    }

    public void setImagePerfil(String imagePath) {
        ImageUtils.load(this, imagePath, R.drawable.placeholder_images_default, R.drawable.placeholder_images_default,  mImagePerfil, mProgressFotoPerfil);
    }

    public void setTotalHorasAssistidas(int duracaoTotal) {

        if (duracaoTotal > 0) {
            int years = duracaoTotal/(365*24*60);
            int months = (duracaoTotal%(365*24*60))/(12*24*60);
            int days = (duracaoTotal%(365*24*60))/(24*60);
            int hours= (duracaoTotal%(365*24*60)) / 60;
            int minutes = (duracaoTotal%(365*24*60)) % 60;

            setTotalHorasVisibility(mTotalAnosAssistidosContainer, mTotalAnosAssistidos, mTotalAnosAssistidosLabel, R.plurals.number_of_year, years);
            setTotalHorasVisibility(mTotalMesesAssistidosContainer, mTotalMesesAssistidos, mTotalMesesAssistidosLabel, R.plurals.number_of_month, months);
            setTotalHorasVisibility(mTotalDiasAssistidosContainer, mTotalDiasAssistidos, mTotalDiasAssistidosLabel, R.plurals.number_of_days, days);
            setTotalHorasVisibility(mTotalHorasAssistidosContainer, mTotalHorasAssistidos, mTotalHorasAssistidosLabel, R.plurals.number_of_hours, hours);
            setTotalHorasVisibility(mTotalMinutosAssistidosContainer, mTotalMinutosAssistidos, mTotalMinutosAssistidosLabel, R.plurals.number_of_minute, minutes);
        } else {
            mTotalHorasRiple.setVisibility(View.GONE);
        }

    }

    public void setTotalFilmesAssistidos(int totalFilmesAssistidos) {
        mTotalFilmesAssistidos.setText(String.valueOf(totalFilmesAssistidos));
        mTotalFilmesAssistidosLabel.setText(getResources().getQuantityString(R.plurals.number_of_filmes_assistidos, totalFilmesAssistidos));
    }

    private void setTotalHorasVisibility(LinearLayout container, TextView numerTextView, TextView label, int idLabelString, int value) {
        if (value == 0)
            container.setVisibility(View.GONE);
        else {
            numerTextView.setText(String.valueOf(value));
            label.setText(getResources().getQuantityString(idLabelString, value));
        }
    }

    @Override
    protected View.OnClickListener onSnackbarClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onStart();
                mSnackbar.dismiss();
            }
        };
    }

    @Override
    protected int getActivityBaseViewID() {
        return R.layout.activity_perfil;
    }

    @Override
    public void setProgressVisibility(int visibityState) {
        mProgressPerfil.setVisibility(visibityState);
    }

    @Override
    public boolean isAdded() {
        return isAdded();
    }

    @OnClick({R.id.filmes_total_person_riple, R.id.total_horas_riple})
    public void onClickTotalFilmesAssistidos() {
        startActivity(ListsDefaultActivity.newIntent(this, new ListActivityDTO(0, getString(R.string.assistidos), Sort.ASSISTIDOS, R.layout.item_list_movies, ListType.MOVIES), new HashMap<String, String>()));
    }

    @Override
    public void onMovieSelected(int movieID, ImageView imageView) {

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions transitionActivityOptions = ActivityOptions
                    .makeSceneTransitionAnimation(PerfilActivity.this, imageView, getString(R.string.poster_movie));
            startActivity(MovieDetailActivity.newIntent(this, movieID), transitionActivityOptions.toBundle());
        } else {
            startActivity(MovieDetailActivity.newIntent(this, movieID));
        }
    }
}
