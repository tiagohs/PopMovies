package br.com.tiagohs.popmovies.ui.view.activity;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.pnikosis.materialishprogress.ProgressWheel;
import com.squareup.picasso.Callback;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;

import br.com.tiagohs.popmovies.R;
import br.com.tiagohs.popmovies.ui.contracts.PersonDetailContract;
import br.com.tiagohs.popmovies.model.atwork.Artwork;
import br.com.tiagohs.popmovies.model.dto.ImageDTO;
import br.com.tiagohs.popmovies.model.dto.ListActivityDTO;
import br.com.tiagohs.popmovies.model.person.PersonInfo;
import br.com.tiagohs.popmovies.ui.callbacks.ListMoviesCallbacks;
import br.com.tiagohs.popmovies.ui.view.fragment.PersonDetailFragment;
import br.com.tiagohs.popmovies.util.AnimationsUtils;
import br.com.tiagohs.popmovies.util.DTOUtils;
import br.com.tiagohs.popmovies.util.DateUtils;
import br.com.tiagohs.popmovies.util.EmptyUtils;
import br.com.tiagohs.popmovies.util.ImageUtils;
import br.com.tiagohs.popmovies.util.MovieUtils;
import br.com.tiagohs.popmovies.util.PermissionUtils;
import br.com.tiagohs.popmovies.util.ShareUtils;
import br.com.tiagohs.popmovies.util.ViewUtils;
import br.com.tiagohs.popmovies.util.enumerations.ImageSize;
import br.com.tiagohs.popmovies.util.enumerations.ListType;
import br.com.tiagohs.popmovies.util.enumerations.Sort;
import br.com.tiagohs.popmovies.util.enumerations.TypeShowImage;
import br.com.tiagohs.popmovies.ui.tools.AppBarMovieListener;
import br.com.tiagohs.popmovies.ui.tools.EllipsizingTextView;
import br.com.tiagohs.popmovies.ui.callbacks.ImagesCallbacks;
import butterknife.BindView;
import butterknife.OnClick;

public class PersonDetailActivity extends BaseActivity implements PersonDetailContract.PersonDetailView, ListMoviesCallbacks, ImagesCallbacks {
    private static final String TAG = PersonDetailActivity.class.getSimpleName();

    private static final String ARG_PERSON_ID = "br.com.tiagohs.popmovies.person_id";
    private static final String ARG_NAME_PERSON = "br.com.tiagohs.popmovies.name_person";

    private static final int CONTAINER_PERSON_NUMBERS_ANIMATION_DURATION = 1000;
    private static final int IMAGE_SCALE_UP_ANIMATION_DURATION = 500;

    @BindView(R.id.progress_person_details)     ProgressBar mPersonProgressBar;
    @BindView(R.id.total_filmes)                TextView mTotalFilmes;
    @BindView(R.id.total_fotos)                 TextView mTotalPhotos;
    @BindView(R.id.label_total_filmes)          TextView mLabelTotalFilmes;
    @BindView(R.id.label_total_fotos)           TextView mLabelTotalFotos;
    @BindView(R.id.name_person)                 TextView mPersonName;
    @BindView(R.id.person_wallpaper_overlay)    View mBackgroundPersonOverlay;
    @BindView(R.id.movie_shadow)                View mBackgroundShadow;
    @BindView(R.id.background_person)           ImageView mBackgroundPerson;
    @BindView(R.id.image_circle)                ImageView mImagePerson;
    @BindView(R.id.person_app_bar)              AppBarLayout mAppBarLayout;
    @BindView(R.id.facebook_riple)              MaterialRippleLayout mFacabookRiple;
    @BindView(R.id.twitter_riple)               MaterialRippleLayout mTwitterRiple;
    @BindView(R.id.instagram_riple)             MaterialRippleLayout mInstagramRiple;
    @BindView(R.id.share_progress)              ProgressWheel mProgressShare;
    @BindView(R.id.person_numbers_container)    LinearLayout mPersonNumbersContainer;
    @BindView(R.id.picture_circle_container)
    FrameLayout mPictureContainer;

    @Inject
    PersonDetailContract.PersonDetailPresenter mPersonDetailPresenter;

    private int mPersonID;
    private PersonInfo mPerson;
    private String mArgPersonName;
    private String mDescricao;
    private Bitmap mImageToShare;

    public static Intent newIntent(Context context, int personID) {
        Intent intent = new Intent(context, PersonDetailActivity.class);
        intent.putExtra(ARG_PERSON_ID, personID);

        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getApplicationComponent().inject(this);

        mPersonDetailPresenter.onBindView(this);

        if (EmptyUtils.isNotNull(savedInstanceState))
            mArgPersonName = savedInstanceState.getString(ARG_NAME_PERSON);

        mPersonID = getIntent().getIntExtra(ARG_PERSON_ID, 0);

        mPictureContainer.setVisibility(View.GONE);
        mPersonDetailPresenter.getPersonDetails(mPersonID);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPersonDetailPresenter.onUnbindView();
    }

    public void setDescricao(String mDescricao) {
        this.mDescricao = mDescricao;
    }

    @Override
    protected View.OnClickListener onSnackbarClickListener() {
        return null;
    }

    @Override
    protected int getMenuLayoutID() {
        return R.menu.menu_detail_default;
    }

    @Override
    protected int getActivityBaseViewID() {
        return R.layout.activity_person_detail_perfil;
    }

    private AppBarMovieListener onOffsetChangedListener() {
        return new AppBarMovieListener() {

            @Override
            public void onExpanded(AppBarLayout appBarLayout) {
                mToolbar.setBackground(ViewUtils.getDrawableFromResource(getApplicationContext(), R.drawable.background_action_bar_transparent));

            }

            @Override
            public void onCollapsed(AppBarLayout appBarLayout) {
                mToolbar.setBackgroundColor(ViewUtils.getColorFromResource(getApplicationContext(), R.color.colorPrimary));
                mToolbar.setTitle(EmptyUtils.isNotNull(mPerson) ? mPerson.getName() : mArgPersonName);
                setVisibilityFacebook(View.GONE);
                setVisibilityTwitter(View.GONE);
                setVisibilityInstagram(View.GONE);
            }

            @Override
            public void onIdle(AppBarLayout appBarLayout) {
                mToolbar.setBackground(ViewUtils.getDrawableFromResource(getApplicationContext(), R.drawable.background_action_bar_transparent));
                mToolbar.setTitle("");
                setVisibilityFacebook(EmptyUtils.isNotNull(mPerson.getExternalIDs().getFacebookId()) ? View.VISIBLE : View.GONE);
                setVisibilityTwitter(EmptyUtils.isNotNull(mPerson.getExternalIDs().getTwitterId()) ? View.VISIBLE : View.GONE);
                setVisibilityInstagram(EmptyUtils.isNotNull(mPerson.getExternalIDs().getInstagramID()) ? View.VISIBLE : View.GONE);
            }
        };
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_share:

                sharePersonDetails();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void sharePersonDetails() {

        if (PermissionUtils.validatePermission(PersonDetailActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, getString(R.string.permission_error_write_content))) {
            mProgressShare.setVisibility(View.VISIBLE);

            if (isInternetConnected() && EmptyUtils.isNotNull(mPerson)) {
                View view = getLayoutInflater().inflate(R.layout.share_person_details, null);
                ImageView personPerfil = (ImageView) view.findViewById(R.id.person_perfil);
                TextView personName = (TextView) view.findViewById(R.id.person_name);
                TextView personSubtitle = (TextView) view.findViewById(R.id.person_subtitle);
                EllipsizingTextView personDescricao = (EllipsizingTextView) view.findViewById(R.id.person_descricao);

                ImageUtils.load(this, mPerson.getProfilePath(), personPerfil, mPerson.getName(), ImageSize.POSTER_185);
                personName.setText(mPerson.getName());

                if (EmptyUtils.isNotNull(mPerson.getBirthday())) {
                    int age = MovieUtils.getAge(mPerson.getYear(), mPerson.getMonth(), mPerson.getDay());
                    personSubtitle.setText(getString(R.string.data_nascimento_formatado, DateUtils.formateDate(mPerson.getBirthday()), age) + " " + getResources().getQuantityString(R.plurals.number_of_year, age));
                } else {
                    personSubtitle.setVisibility(View.GONE);
                }

                if (EmptyUtils.isEmpty(mPerson.getBiography()))
                    personDescricao.setText(mDescricao);
                else
                    personDescricao.setText(mPerson.getBiography());
                personDescricao.setTypeface(Typeface.createFromAsset(getAssets(), "opensans.ttf"));

                final LinearLayout movieShareContainer = (LinearLayout) view.findViewById(R.id.share_person_container);

                new Handler().postDelayed(new Runnable() {
                    public void run() {

                        if (!isDestroyed()) {
                            mProgressShare.setVisibility(View.GONE);

                            mImageToShare = ViewUtils.getBitmapFromView(movieShareContainer);

                            createShareIntent(mImageToShare);
                        }

                    }
                }, 3000);
            }
        }




    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (PermissionUtils.onRequestPermissionsResultValidate(grantResults, requestCode))
            createShareIntent(mImageToShare);
    }

    public void createShareIntent(Bitmap imageToShare) {
        String imdbID = null;

        if (EmptyUtils.isNotNull(mPerson.getImdbId()))
            imdbID = getString(R.string.person_imdb, mPerson.getImdbId());

        ShareUtils.shareImageWithText(this, MediaStore.Images.Media.insertImage(getContentResolver(), imageToShare, mPerson.getName() , null), imdbID);
        mProgressShare.setVisibility(View.GONE);
    }

    public void setPerson(PersonInfo person) {
        mPerson = person;
    }

    public void updateImages() {

            mBackgroundPerson.post(new Runnable() {
                @Override
                public void run() {
                    int indexImage = 0;

                    if (!isDestroyed()) {
                        if (!mPerson.getTaggedImages().isEmpty()) {
                            indexImage = new Random().nextInt(mPerson.getTaggedImages().size());
                            ImageUtils.loadWithRevealAnimation(PersonDetailActivity.this, mPerson.getTaggedImages().get(indexImage).getFilePath(), mBackgroundPerson, R.drawable.ic_image_default_back, ImageSize.POSTER_500, new Callback() {
                                @Override
                                public void onSuccess() {
                                    mBackgroundPersonOverlay.setVisibility(View.VISIBLE);
                                    mBackgroundShadow.setVisibility(View.VISIBLE);
                                }

                                @Override
                                public void onError(Exception e) {
                                    mBackgroundPersonOverlay.setVisibility(View.VISIBLE);
                                    mBackgroundShadow.setVisibility(View.VISIBLE);
                                }
                            });
                        } else if (!mPerson.getImages().isEmpty()) {
                            indexImage = new Random().nextInt(mPerson.getImages().size());
                            ImageUtils.loadWithRevealAnimation(PersonDetailActivity.this, mPerson.getImages().get(indexImage).getFilePath(), mBackgroundPerson, R.drawable.ic_image_default_back, ImageSize.POSTER_500);
                        }
                    }
                }
            });

            ImageUtils.loadByCircularImage(this, mPerson.getProfilePath(), mImagePerson, mPerson.getName(), isTablet() ? ImageSize.PROFILE_632 : ImageSize.PROFILE_185);

            if (isTablet())
                mImagePerson.setScaleType(ImageView.ScaleType.CENTER_CROP);

            AnimationsUtils.creatScaleUpAnimation(mPictureContainer, IMAGE_SCALE_UP_ANIMATION_DURATION);
            mPictureContainer.setVisibility(View.VISIBLE);

    }

    public void setVisibilityFacebook(int visibility) {
        mFacabookRiple.setVisibility(visibility);
    }

    public void setVisibilityTwitter(int visibility) {
        mTwitterRiple.setVisibility(visibility);
    }

    public void setVisibilityInstagram(int visibility) {
        mInstagramRiple.setVisibility(visibility);
    }

    public void updateAditionalInfo(int totalFilmes, int totalFotos) {

        mPersonName.setText(mPerson.getName());

        mLabelTotalFilmes.setText(getResources().getQuantityString(R.plurals.number_of_films, totalFilmes).toUpperCase());
        mTotalFilmes.setText(String.valueOf(totalFilmes));

        mLabelTotalFotos.setText(getResources().getQuantityString(R.plurals.number_of_fotos_person, totalFotos).toUpperCase());
        mTotalPhotos.setText(String.valueOf(totalFotos));

        mPersonNumbersContainer.setAnimation(AnimationsUtils.createFadeInAnimation(CONTAINER_PERSON_NUMBERS_ANIMATION_DURATION));
        mPersonNumbersContainer.setVisibility(View.VISIBLE);
    }

    public void setupTabs() {

        if (!isDestroyed())
            startFragment(R.id.person_details_fragment, PersonDetailFragment.newInstance(mPerson));

        mAppBarLayout.addOnOffsetChangedListener(onOffsetChangedListener());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (EmptyUtils.isNotNull(mPerson))
            outState.putString(ARG_NAME_PERSON, mPerson.getName());
    }

    @OnClick(R.id.facebook_riple)
    public void onClickFacebook() {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.facebook_link, mPerson.getExternalIDs().getFacebookId()))));
    }

    @OnClick(R.id.twitter_riple)
    public void onClickTwitter() {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse(getString(R.string.twitter_link_uri, mPerson.getExternalIDs().getTwitterId())));
            startActivity(intent);

        } catch (Exception e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse(getString(R.string.twitter_link, mPerson.getExternalIDs().getTwitterId()))));
        }

    }

    @OnClick(R.id.instagram_riple)
    public void onClickInstagram() {
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.instagram_link_uri, mPerson.getExternalIDs().getInstagramID()))));
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse(getString(R.string.instagram_link, mPerson.getExternalIDs().getInstagramID()))));
        }
    }

    @OnClick(R.id.background_person)
    public void onClickBackground() {
        if (EmptyUtils.isNotNull(mPerson)) {
            if (!mPerson.getTaggedImages().isEmpty())
                onClickImage(DTOUtils.createPersonImagesBackgroundDTO(mPerson, mPerson.getImages().size(), mPerson.getTaggedImages()), new ImageDTO(mPerson.getId(), null, mPerson.getImages().get(0).getFilePath()));
            else if (!mPerson.getImages().isEmpty())
                onClickProfileImage();
        }

    }

    @OnClick(R.id.image_circle)
    public void onClickProfileImage() {
        if (EmptyUtils.isNotNull(mPerson)) {
            if (!mPerson.getImages().isEmpty())
                onClickImage(DTOUtils.createPersonImagesDTO(mPerson, mPerson.getImages().size(), mPerson.getImages()), new ImageDTO(mPerson.getId(), null, mPerson.getImages().get(0).getFilePath()));
        }
        }

    @OnClick(R.id.filmes_total_person_riple)
    public void onClickTotalFilmes() {
        if (EmptyUtils.isNotNull(mPerson))
            startActivity(ListsDefaultActivity.newIntent(this, mPerson.getMoviesCarrer(), new ListActivityDTO(mPerson.getId(), mPerson.getName(), getString(R.string.carreira), Sort.LIST_DEFAULT, R.layout.item_list_movies, ListType.MOVIES)));
    }

    private List<Artwork> getTotalPersonImages() {
        List<Artwork> images = new ArrayList<>();
        images.addAll(mPerson.getTaggedImages());
        images.addAll(mPerson.getImages());

        return images;
    }

    @OnClick(R.id.fotos_total_person_riple)
    public void onClickTotalFotos() {
        if (EmptyUtils.isNotNull(mPerson)) {
            List<Artwork> list = getTotalPersonImages();

            if (!getTotalPersonImages().isEmpty())
                startActivity(WallpapersActivity.newIntent(this, DTOUtils.createPersonImagesDTO(mPerson, list.size(), list), getString(R.string.wallpapers_title), mPerson.getName()));
        }
    }

    @Override
    public void onMovieSelected(int movieID, ImageView imageView) {
        startActivity(MovieDetailActivity.newIntent(this, movieID));
    }

    @Override
    public void onClickImage(List<ImageDTO> imagens, ImageDTO imageDTO) {
        startActivity(WallpapersDetailActivity.newIntent(this, imagens, imageDTO, getString(R.string.wallpapers_title), mPerson.getName(), TypeShowImage.WALLPAPER_IMAGES));
    }

    @Override
    public void setProgressVisibility(int visibityState) {
        mPersonProgressBar.setVisibility(visibityState);
    }

    @Override
    public boolean isAdded() {
        return !isDestroyed();
    }

}
