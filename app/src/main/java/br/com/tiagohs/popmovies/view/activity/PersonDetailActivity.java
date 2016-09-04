package br.com.tiagohs.popmovies.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.pnikosis.materialishprogress.ProgressWheel;

import javax.inject.Inject;

import br.com.tiagohs.popmovies.R;
import br.com.tiagohs.popmovies.model.person.PersonInfo;
import br.com.tiagohs.popmovies.presenter.PersonDetailPresenter;
import br.com.tiagohs.popmovies.util.ImageUtils;
import br.com.tiagohs.popmovies.util.ViewUtils;
import br.com.tiagohs.popmovies.util.enumerations.ImageSize;
import br.com.tiagohs.popmovies.view.AppBarMovieListener;
import br.com.tiagohs.popmovies.view.PersonDetailView;
import butterknife.BindView;

public class PersonDetailActivity extends BaseActivity implements PersonDetailView {

    private static final String ARG_PERSON_ID = "br.com.tiagohs.popmovies.person_id";

    @BindView(R.id.background_person)
    ImageView mBackgroundPerson;

    @BindView(R.id.background_person_progress)
    ProgressWheel mBackgroundProgress;

    @BindView(R.id.image_person)
    ImageView mImagePerson;

    @BindView(R.id.image_person_progress)
    ProgressWheel mImagePersonProgress;

    @BindView(R.id.name_person)
    TextView mPersonName;

    @BindView(R.id.total_filmes)
    TextView mTotalFilmes;

    @BindView(R.id.total_fotos)
    TextView mTotalPhotos;

    @BindView(R.id.descricao_person)
    TextView mDescricaoPerson;

    @BindView(R.id.person_app_bar)
    AppBarLayout mAppBarLayout;

    @Inject
    PersonDetailPresenter mPersonDetailPresenter;

    private int mPersonID;
    private PersonInfo mPerson;

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

        mPersonID = getIntent().getIntExtra(ARG_PERSON_ID, 0);
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
                mToolbar.setTitle(mPerson.getName());
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
            ImageUtils.load(this, mPerson.getTaggedImages().get(0).getFilePath(), mBackgroundPerson, R.drawable.placeholder_images_default, R.drawable.placeholder_images_default, ImageSize.BACKDROP_780, mBackgroundProgress);
        }else if (!mPerson.getImages().isEmpty()) {
            ImageUtils.load(this, mPerson.getImages().get(0).getFilePath(), mBackgroundPerson, R.drawable.placeholder_images_default, R.drawable.placeholder_images_default, ImageSize.BACKDROP_780, mBackgroundProgress);
        }

        ImageUtils.load(this, mPerson.getProfilePath(), mImagePerson, R.drawable.placeholder_images_default, R.drawable.placeholder_images_default, ImageSize.POSTER_154, mImagePersonProgress);

        mPersonName.setTypeface(Typeface.createFromAsset(getAssets(), "opensans.ttf"));
        mPersonName.setText(mPerson.getName());

        mTotalFilmes.setText(String.valueOf(mPerson.getMovieCredits().getCast().size() + mPerson.getMovieCredits().getCrew().size()));
        mTotalPhotos.setText(String.valueOf(mPerson.getImages().size() + mPerson.getTaggedImages().size()));

        mDescricaoPerson.setTypeface(Typeface.createFromAsset(getAssets(), "opensans.ttf"));
        mDescricaoPerson.setText(mPerson.getBiography());
    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void hideProgressBar() {

    }
}
