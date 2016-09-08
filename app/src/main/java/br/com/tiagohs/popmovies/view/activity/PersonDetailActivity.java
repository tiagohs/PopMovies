package br.com.tiagohs.popmovies.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import javax.inject.Inject;

import br.com.tiagohs.popmovies.R;
import br.com.tiagohs.popmovies.model.dto.ImageDTO;
import br.com.tiagohs.popmovies.model.person.PersonInfo;
import br.com.tiagohs.popmovies.presenter.PersonDetailPresenter;
import br.com.tiagohs.popmovies.util.ImageUtils;
import br.com.tiagohs.popmovies.util.ViewUtils;
import br.com.tiagohs.popmovies.util.enumerations.ImageSize;
import br.com.tiagohs.popmovies.view.AppBarMovieListener;
import br.com.tiagohs.popmovies.view.PersonDetailView;
import br.com.tiagohs.popmovies.view.adapters.TabPersonCarrerAdapter;
import br.com.tiagohs.popmovies.view.callbacks.ImagesCallbacks;
import br.com.tiagohs.popmovies.view.callbacks.ListMoviesCallbacks;
import butterknife.BindView;
import butterknife.OnClick;

public class PersonDetailActivity extends BaseActivity implements PersonDetailView, ListMoviesCallbacks, ImagesCallbacks {

    private static final String ARG_PERSON_ID = "br.com.tiagohs.popmovies.person_id";
    private static final String ARG_NAME_PERSON = "br.com.tiagohs.popmovies.name_person";

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

    @BindView(R.id.img_facebook)
    ImageView mFacebookImage;

    @BindView(R.id.img_twitter)
    ImageView mTwitterImage;

    @BindView(R.id.person_app_bar)
    AppBarLayout mAppBarLayout;

    @BindView(R.id.tabLayout)
    TabLayout mTabCarrer;

    @BindView(R.id.carrer_view_pager)
    ViewPager mCarrerViewPager;

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
    }

    @Override
    protected View.OnClickListener onSnackbarClickListener() {
        return null;
    }

    @Override
    protected void onStart() {
        super.onStart();

        mPersonDetailPresenter.getPersonDeatils(mPersonID);
        mAppBarLayout.addOnOffsetChangedListener(onOffsetChangedListener());
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
                mToolbar.setTitle("");
            }

            @Override
            public void onCollapsed(AppBarLayout appBarLayout) {
                mToolbar.setBackgroundColor(ViewUtils.getColorFromResource(getApplicationContext(), R.color.colorPrimary));
                mToolbar.setTitle(mPerson != null ? mPerson.getName() : mArgPersonName);
            }

            @Override
            public void onIdle(AppBarLayout appBarLayout) {
                mToolbar.setBackground(ViewUtils.getDrawableFromResource(getApplicationContext(), R.drawable.background_action_bar_transparent));
            }
        };
    }

    @Override
    public void atualizarView(PersonInfo person) {
        mPerson = person;

        Log.i("Person", mPerson.getTaggedImages().size() + "");

        if (!mPerson.getTaggedImages().isEmpty()) {
            ImageUtils.loadWithRevealAnimation(this, mPerson.getTaggedImages().get(0).getFilePath(), mBackgroundPerson, R.drawable.ic_image_default_back, ImageSize.BACKDROP_780);
        } else if (!mPerson.getImages().isEmpty()) {
            ImageUtils.loadWithRevealAnimation(this, mPerson.getImages().get(0).getFilePath(), mBackgroundPerson, R.drawable.ic_image_default_back, ImageSize.BACKDROP_780);
        }

        ImageUtils.loadByCircularImage(this, mPerson.getProfilePath(), mImagePerson, mPerson.getName(), ImageSize.POSTER_154);

        if (mPerson.getExternalIDs().getFacebookId() != null)
            mFacebookImage.setVisibility(View.VISIBLE);

        if (mPerson.getExternalIDs().getTwitterId() != null)
            mTwitterImage.setVisibility(View.VISIBLE);

        mPersonName.setTypeface(Typeface.createFromAsset(getAssets(), "opensans.ttf"));
        mPersonName.setText(mPerson.getName());

        mCarrerViewPager.setAdapter(new TabPersonCarrerAdapter(getSupportFragmentManager(), getResources().getStringArray(R.array.person_carrer_tab_array), mPerson));
        mTabCarrer.setupWithViewPager(mCarrerViewPager);
        int totalFilmes = mPerson.getMovieCredits().getCast().size() + mPerson.getMovieCredits().getCrew().size();

        mLabelTotalFilmes.setText(getResources().getQuantityString(R.plurals.number_of_films_person, totalFilmes));
        mTotalFilmes.setText(String.valueOf(totalFilmes));

        int totalFotos = mPerson.getImages().size() + mPerson.getTaggedImages().size();
        mLabelTotalFotos.setText(getResources().getQuantityString(R.plurals.number_of_fotos_person, totalFotos));
        mTotalPhotos.setText(String.valueOf(totalFotos));

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(ARG_NAME_PERSON, mPerson.getName());
    }

    @OnClick(R.id.img_facebook)
    public void onClickFacebook() {
        Toast.makeText(this, "Facebook", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.img_twitter)
    public void onClickTwitter() {
        Toast.makeText(this, "Twitter", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void hideProgressBar() {

    }

    @Override
    public void onMovieSelected(int movieID, ImageView imageView) {
        startActivity(MovieDetailActivity.newIntent(this, movieID));
    }

    @Override
    public void onClickImage(ImageDTO imageDTO) {

    }
}
