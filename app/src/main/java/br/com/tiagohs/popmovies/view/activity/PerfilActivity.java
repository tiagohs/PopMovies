package br.com.tiagohs.popmovies.view.activity;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.pnikosis.materialishprogress.ProgressWheel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.com.tiagohs.popmovies.R;
import br.com.tiagohs.popmovies.data.repository.ProfileRepositoryImpl;
import br.com.tiagohs.popmovies.model.db.ProfileDB;
import br.com.tiagohs.popmovies.model.db.UserDB;
import br.com.tiagohs.popmovies.model.dto.ImageDTO;
import br.com.tiagohs.popmovies.presenter.PerfilPresenter;
import br.com.tiagohs.popmovies.util.ImageUtils;
import br.com.tiagohs.popmovies.util.PrefsUtils;
import br.com.tiagohs.popmovies.util.ViewUtils;
import br.com.tiagohs.popmovies.util.enumerations.ImageSize;
import br.com.tiagohs.popmovies.util.enumerations.TypeShowImage;
import br.com.tiagohs.popmovies.view.AppBarMovieListener;
import br.com.tiagohs.popmovies.view.PerfilView;
import br.com.tiagohs.popmovies.view.callbacks.ListMoviesCallbacks;
import br.com.tiagohs.popmovies.view.fragment.PerfilFragment;
import butterknife.BindView;
import butterknife.OnClick;

public class PerfilActivity extends BaseActivity implements PerfilView, ListMoviesCallbacks {
    private static final String TAG = PerfilActivity.class.getSimpleName();

    @BindView(R.id.background_perfil)
    ImageView mBackgroundPerfil;

    @BindView(R.id.image_circle)
    ImageView mImagePerfil;

    @BindView(R.id.name_perfil)
    TextView mNamePerfil;

    @BindView(R.id.progress_perfil)
    ProgressBar mProgressPerfil;

    @BindView(R.id.progress_image_circle)
    ProgressWheel mProgressFotoPerfil;

    @BindView(R.id.btn_editar)
    FloatingActionButton mEditar;

    @BindView(R.id.picture_container)
    LinearLayout mPictureContainer;


    @BindView(R.id.perfil_app_bar)
    AppBarLayout mAppBarLayout;

    @Inject()
    PerfilPresenter mPerfilPresenter;

    private String mBackgrounPath;
    private ProfileDB mProfile;

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, PerfilActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getApplicationComponent().inject(this);

        mPerfilPresenter.setProfileRepository(new ProfileRepositoryImpl(this));
        mPerfilPresenter.setUsername(PrefsUtils.getCurrentUser(this).getUsername());
        mPerfilPresenter.setView(this);

        mEditar.setOnClickListener(onClickEditButton());
    }

    public View.OnClickListener onClickEditButton() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(PerfilEditActivity.newIntent(PerfilActivity.this));
            }
        };
    }

    public void setProfile(ProfileDB mProfile) {
        this.mProfile = mProfile;
    }

    @Override
    public void onErrorLoadingBackground() {
        ViewUtils.createToastMessage(this, getString(R.string.error_loading_background));
    }

    @Override
    protected void onResume() {
        super.onResume();

        mPerfilPresenter.initUpdates(TAG);
    }

    public void setupTabs() {

        if (!isDestroyed()) {
            startFragment(R.id.perfil_fragment, PerfilFragment.newInstance());
        }

        mAppBarLayout.addOnOffsetChangedListener(onOffsetChangedListener());
    }

    public void setBackground(String backgroundPath) {
        mBackgrounPath = backgroundPath;

        ImageUtils.loadWithBlur(this, mBackgrounPath, mBackgroundPerfil, R.drawable.ic_image_default_back, ImageSize.BACKDROP_300);

        }

    private AppBarMovieListener onOffsetChangedListener() {
        return new AppBarMovieListener() {

            @Override
            public void onExpanded(AppBarLayout appBarLayout) {
                mToolbar.setBackground(ViewUtils.getDrawableFromResource(getApplicationContext(), R.drawable.background_action_bar_transparent));
                mToolbar.setTitle("");
                mNamePerfil.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCollapsed(AppBarLayout appBarLayout) {
                mToolbar.setBackgroundColor(ViewUtils.getColorFromResource(getApplicationContext(), R.color.colorPrimary));
                mToolbar.setTitle(PrefsUtils.getCurrentUser(PerfilActivity.this).getNome());
                mNamePerfil.setVisibility(View.GONE);
                mPictureContainer.setVisibility(View.GONE);
            }

            @Override
            public void onIdle(AppBarLayout appBarLayout) {
                mToolbar.setBackground(ViewUtils.getDrawableFromResource(getApplicationContext(), R.drawable.background_action_bar_transparent));
                mToolbar.setTitle("");
                mNamePerfil.setVisibility(View.VISIBLE);
                mPictureContainer.setVisibility(View.VISIBLE);
            }
        };
    }

    public void setNamePerfil(String nameUser) {
        mNamePerfil.setText(nameUser);
        mNamePerfil.setTypeface(Typeface.createFromAsset(getAssets(), "openSansBold.ttf"));
    }

    public void setImagePerfil(String imagePath) {
        ImageUtils.load(this, imagePath, R.drawable.placeholder_images_default, R.drawable.placeholder_images_default,  mImagePerfil, mProgressFotoPerfil);
    }

    public void setLocalImagePerfil() {
        mImagePerfil.setImageBitmap(ImageUtils.getBitmapFromPath(mProfile.getUser().getLocalPicture(), this));
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
    protected int getMenuLayoutID() {
        return 0;
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


    @OnClick(R.id.image_circle)
    public void onClickPerfilImage() {
        if (mProfile.getUser().getTypePhoto() == UserDB.PHOTO_ONLINE) {
            ImageDTO perfilImage = new ImageDTO(mProfile.getUser().getPicturePath());
            List<ImageDTO> imagens = new ArrayList<>();
            imagens.add(perfilImage);

            startActivity(WallpapersDetailActivity.newIntent(this, imagens, perfilImage, getString(R.string.wallpapers_title), mProfile.getUser().getNome(), TypeShowImage.SIMPLE_IMAGE));
        }

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
