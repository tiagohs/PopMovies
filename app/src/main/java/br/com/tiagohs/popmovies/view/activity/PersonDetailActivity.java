package br.com.tiagohs.popmovies.view.activity;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;

import br.com.tiagohs.popmovies.R;
import br.com.tiagohs.popmovies.model.atwork.Artwork;
import br.com.tiagohs.popmovies.model.dto.ImageDTO;
import br.com.tiagohs.popmovies.model.dto.ListActivityDTO;
import br.com.tiagohs.popmovies.model.person.PersonInfo;
import br.com.tiagohs.popmovies.presenter.PersonDetailPresenter;
import br.com.tiagohs.popmovies.util.DTOUtils;
import br.com.tiagohs.popmovies.util.ImageUtils;
import br.com.tiagohs.popmovies.util.ViewUtils;
import br.com.tiagohs.popmovies.util.enumerations.ImageSize;
import br.com.tiagohs.popmovies.util.enumerations.ListType;
import br.com.tiagohs.popmovies.util.enumerations.Sort;
import br.com.tiagohs.popmovies.view.AppBarMovieListener;
import br.com.tiagohs.popmovies.view.PersonDetailView;
import br.com.tiagohs.popmovies.view.callbacks.ImagesCallbacks;
import br.com.tiagohs.popmovies.view.callbacks.ListMoviesCallbacks;
import br.com.tiagohs.popmovies.view.fragment.PersonDetailFragment;
import butterknife.BindView;
import butterknife.OnClick;

public class PersonDetailActivity extends BaseActivity implements PersonDetailView, ListMoviesCallbacks, ImagesCallbacks {

    private static final String ARG_PERSON_ID = "br.com.tiagohs.popmovies.person_id";
    private static final String ARG_NAME_PERSON = "br.com.tiagohs.popmovies.name_person";

    @BindView(R.id.progress_person_details)
    ProgressBar mPersonProgressBar;

    @BindView(R.id.total_filmes)
    TextView mTotalFilmes;

    @BindView(R.id.total_fotos)
    TextView mTotalPhotos;

    @BindView(R.id.label_total_filmes)
    TextView mLabelTotalFilmes;

    @BindView(R.id.label_total_fotos)
    TextView mLabelTotalFotos;

    @BindView(R.id.name_person)
    TextView mPersonName;

    @BindView(R.id.background_person)
    ImageView mBackgroundPerson;

    @BindView(R.id.image_person)
    ImageView mImagePerson;

    @BindView(R.id.person_app_bar)
    AppBarLayout mAppBarLayout;

    @BindView(R.id.facebook_riple)
    MaterialRippleLayout mFacabookRiple;

    @BindView(R.id.twitter_riple)
    MaterialRippleLayout mTwitterRiple;

    @BindView(R.id.instagram_riple)
    MaterialRippleLayout mInstagramRiple;

    @Inject
    PersonDetailPresenter mPersonDetailPresenter;

    private int mPersonID;
    private PersonInfo mPerson;
    private String mArgPersonName;

    public static Intent newIntent(Context context, int personID) {
        Intent intent = new Intent(context, PersonDetailActivity.class);
        intent.putExtra(ARG_PERSON_ID, personID);

        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getApplicationComponent().inject(this);

        mPersonDetailPresenter.setView(this);

        if (savedInstanceState != null)
            mArgPersonName = savedInstanceState.getString(ARG_NAME_PERSON);

        mPersonID = getIntent().getIntExtra(ARG_PERSON_ID, 0);
        setVisibilityFacebook(View.GONE);
        setVisibilityTwitter(View.GONE);
        setVisibilityInstagram(View.GONE);
    }

    @Override
    protected View.OnClickListener onSnackbarClickListener() {
        return null;
    }

    @Override
    protected void onStart() {
        super.onStart();

        mPersonDetailPresenter.getPersonDetails(mPersonID);
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
                mToolbar.setTitle(mPerson != null ? mPerson.getName() : mArgPersonName);
                setVisibilityFacebook(View.GONE);
                setVisibilityTwitter(View.GONE);
                setVisibilityInstagram(View.GONE);
            }

            @Override
            public void onIdle(AppBarLayout appBarLayout) {
                mToolbar.setBackground(ViewUtils.getDrawableFromResource(getApplicationContext(), R.drawable.background_action_bar_transparent));
                mToolbar.setTitle("");
                setVisibilityFacebook(mPerson.getExternalIDs().getFacebookId() != null ? View.VISIBLE : View.GONE);
                setVisibilityTwitter(mPerson.getExternalIDs().getTwitterId() != null ? View.VISIBLE : View.GONE);
                setVisibilityInstagram(mPerson.getExternalIDs().getInstagramID() != null ? View.VISIBLE : View.GONE);
            }
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail_default, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_share:

                return true;
            case android.R.id.home:
                finish();
                return true;
            default:
                return false;
        }
    }

    public void setPerson(PersonInfo person) {
        mPerson = person;
    }

    public void updateImages() {

        mBackgroundPerson.post(new Runnable() {
            @Override
            public void run() {
                int indexImage = 0;

                if (!mPerson.getTaggedImages().isEmpty()) {
                    indexImage = new Random().nextInt(mPerson.getTaggedImages().size());
                    ImageUtils.loadWithRevealAnimation(PersonDetailActivity.this, mPerson.getTaggedImages().get(indexImage).getFilePath(), mBackgroundPerson, R.drawable.ic_image_default_back, ImageSize.BACKDROP_780);
                } else if (!mPerson.getImages().isEmpty()) {
                    indexImage = new Random().nextInt(mPerson.getImages().size());
                    ImageUtils.loadWithRevealAnimation(PersonDetailActivity.this, mPerson.getImages().get(indexImage).getFilePath(), mBackgroundPerson, R.drawable.ic_image_default_back, ImageSize.BACKDROP_780);
                }
            }
        });

        ImageUtils.loadByCircularImage(this, mPerson.getProfilePath(), mImagePerson, mPerson.getName(), ImageSize.POSTER_154);
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

        mPersonName.setTypeface(Typeface.createFromAsset(getAssets(), "opensans.ttf"));
        mPersonName.setText(mPerson.getName());

        mLabelTotalFilmes.setText(getResources().getQuantityString(R.plurals.number_of_films_person, totalFilmes));
        mTotalFilmes.setText(String.valueOf(totalFilmes));

        mLabelTotalFotos.setText(getResources().getQuantityString(R.plurals.number_of_fotos_person, totalFotos));
        mTotalPhotos.setText(String.valueOf(totalFotos));

    }

    public void setupTabs() {

        if (!isDestroyed()) {
            FragmentManager fm = getSupportFragmentManager();
            Fragment fragment = fm.findFragmentById(R.id.person_details_fragment);

            if (fragment == null) {
                fm.beginTransaction()
                        .add(R.id.person_details_fragment, PersonDetailFragment.newInstance(mPerson))
                        .commit();
            }
        }

        mAppBarLayout.addOnOffsetChangedListener(onOffsetChangedListener());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

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

        }catch (Exception e) {
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
        if (!mPerson.getTaggedImages().isEmpty())
            onClickImage(DTOUtils.createPersonImagesBackgroundDTO(mPerson, mPerson.getImages().size(), mPerson.getTaggedImages()), new ImageDTO(mPerson.getId(), null, mPerson.getImages().get(0).getFilePath()));
        else if (!mPerson.getImages().isEmpty())
            onClickProfileImage();
    }

    @OnClick(R.id.image_person)
    public void onClickProfileImage() {
        if (!mPerson.getImages().isEmpty())
            onClickImage(DTOUtils.createPersonImagesDTO(mPerson, mPerson.getImages().size(), mPerson.getImages()), new ImageDTO(mPerson.getId(), null, mPerson.getImages().get(0).getFilePath()));
    }

    @OnClick(R.id.filmes_total_person_riple)
    public void onClickTotalFilmes() {
        startActivity(ListsDefaultActivity.newIntent(this, new ListActivityDTO(mPerson.getId(), mPerson.getName(), getString(R.string.carreira), Sort.PERSON_MOVIES_CARRER, R.layout.item_list_movies, ListType.MOVIES)));
    }

    private List<Artwork> getTotalPersonImages() {
        List<Artwork> images = new ArrayList<>();
        images.addAll(mPerson.getTaggedImages());
        images.addAll(mPerson.getImages());

        return images;
    }

    @OnClick(R.id.fotos_total_person_riple)
    public void onClickTotalFotos() {
        List<Artwork> list = getTotalPersonImages();

        if (!getTotalPersonImages().isEmpty())
            startActivity(WallpapersActivity.newIntent(this, DTOUtils.createPersonImagesDTO(mPerson, list.size(), list), getString(R.string.wallpapers_title, mPerson.getName())));
    }

    @Override
    public void onMovieSelected(int movieID, ImageView imageView) {
        startActivity(MovieDetailActivity.newIntent(this, movieID));
    }

    @Override
    public void onClickImage(List<ImageDTO> imagens, ImageDTO imageDTO) {
        startActivity(WallpapersDetailActivity.newIntent(this, imagens, imageDTO, getString(R.string.wallpapers_title, mPerson.getName())));
    }

    @Override
    public void setProgressVisibility(int visibityState) {
        mPersonProgressBar.setVisibility(visibityState);
    }

    @Override
    public boolean isAdded() {
        return this != null;
    }

}
