package br.com.tiagohs.popmovies.ui.view.activity;

import android.content.Context;
import android.content.Intent;
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
import br.com.tiagohs.popmovies.model.db.ProfileDB;
import br.com.tiagohs.popmovies.model.db.UserDB;
import br.com.tiagohs.popmovies.model.dto.ImageDTO;
import br.com.tiagohs.popmovies.ui.callbacks.ListMoviesCallbacks;
import br.com.tiagohs.popmovies.ui.contracts.PerfilContract;
import br.com.tiagohs.popmovies.ui.tools.AppBarMovieListener;
import br.com.tiagohs.popmovies.ui.view.fragment.PerfilFragment;
import br.com.tiagohs.popmovies.util.ImageUtils;
import br.com.tiagohs.popmovies.util.PrefsUtils;
import br.com.tiagohs.popmovies.util.ViewUtils;
import br.com.tiagohs.popmovies.util.enumerations.ImageSize;
import br.com.tiagohs.popmovies.util.enumerations.TypeShowImage;
import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class PerfilActivity extends BaseActivity implements PerfilContract.PerfilView, ListMoviesCallbacks {
    private static final String TAG = PerfilActivity.class.getSimpleName();

    @BindView(R.id.background_perfil)               ImageView mBackgroundPerfil;
    @BindView(R.id.image_circle)                    CircleImageView mImagePerfil;
    @BindView(R.id.name_perfil)                     TextView mNamePerfil;
    @BindView(R.id.progress_perfil)                 ProgressBar mProgressPerfil;
    @BindView(R.id.progress_image_circle)           ProgressWheel mProgressFotoPerfil;
    @BindView(R.id.btn_editar)                      FloatingActionButton mEditar;
    @BindView(R.id.picture_container)               LinearLayout mPictureContainer;
    @BindView(R.id.perfil_app_bar)                  AppBarLayout mAppBarLayout;

    @Inject
    PerfilContract.PerfilPresenter mPerfilPresenter;

    private ProfileDB mProfile;

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, PerfilActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getApplicationComponent().inject(this);

        mPerfilPresenter.onBindView(this);

        //mEditar.setOnClickListener(onClickEditButton());

        mPerfilPresenter.initUpdates(PrefsUtils.getCurrentUser(this).getUsername());
    }

    @OnClick(R.id.btn_editar)
    public void onClickEditButton() {
        startActivity(PerfilEditActivity.newIntent(PerfilActivity.this));
    }

    public void setProfile(ProfileDB mProfile) {
        this.mProfile = mProfile;
    }

    @Override
    public void onErrorLoadingBackground() {
        ViewUtils.createToastMessage(this, getString(R.string.error_loading_background));
    }

    public void setupTabs() {

        if (!isDestroyed()) {
            startFragment(R.id.perfil_fragment, PerfilFragment.newInstance());
        }

        mAppBarLayout.addOnOffsetChangedListener(onOffsetChangedListener());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPerfilPresenter.onUnbindView();
    }

    public void setBackground(String backgroundPath) {
        ImageUtils.loadWithBlur(this, backgroundPath, mBackgroundPerfil, R.drawable.ic_image_default_back, ImageSize.BACKDROP_300);
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
                String name = PrefsUtils.getCurrentUser(PerfilActivity.this).getNome();

                mToolbar.setBackgroundColor(ViewUtils.getColorFromResource(getApplicationContext(), R.color.colorPrimary));
                mToolbar.setTitle(name == null ? PrefsUtils.getCurrentUser(PerfilActivity.this).getEmail() : name);
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
    }

    public void setImagePerfil(String imagePath) {
        ImageUtils.load(this, imagePath, mProfileDB.getUser().getNome(),  mImagePerfil, mProgressFotoPerfil);

        if (isTablet())
            mImagePerfil.setScaleType(ImageView.ScaleType.CENTER_CROP);
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
        return !isDestroyed();
    }


    @OnClick(R.id.image_circle)
    public void onClickPerfilImage() {
        if (mProfile.getUser().getTypePhoto() == UserDB.PHOTO_ONLINE && mProfile.getUser().getPicturePath() != null) {
            ImageDTO perfilImage = new ImageDTO(mProfile.getUser().getPicturePath());
            List<ImageDTO> imagens = new ArrayList<>();
            imagens.add(perfilImage);

            startActivity(WallpapersDetailActivity.newIntent(this, imagens, perfilImage, getString(R.string.wallpapers_title), mProfile.getUser().getNome(), TypeShowImage.SIMPLE_IMAGE));
        }

    }

    @Override
    public void onMovieSelected(int movieID, ImageView imageView) {
        ViewUtils.startMovieActivityWithTranslation(this, movieID, imageView, getString(R.string.poster_movie));
    }
}
