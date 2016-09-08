package br.com.tiagohs.popmovies.view.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeStandalonePlayer;
import com.like.LikeButton;

import java.util.List;

import javax.inject.Inject;

import br.com.tiagohs.popmovies.BuildConfig;
import br.com.tiagohs.popmovies.R;
import br.com.tiagohs.popmovies.model.dto.ImageDTO;
import br.com.tiagohs.popmovies.model.dto.ItemListDTO;
import br.com.tiagohs.popmovies.model.media.Video;
import br.com.tiagohs.popmovies.model.movie.Genre;
import br.com.tiagohs.popmovies.model.movie.MovieDetails;
import br.com.tiagohs.popmovies.model.response.VideosResponse;
import br.com.tiagohs.popmovies.presenter.MovieDetailsPresenter;
import br.com.tiagohs.popmovies.util.AnimationsUtils;
import br.com.tiagohs.popmovies.util.ImageUtils;
import br.com.tiagohs.popmovies.util.MovieUtils;
import br.com.tiagohs.popmovies.util.ViewUtils;
import br.com.tiagohs.popmovies.util.enumerations.ImageSize;
import br.com.tiagohs.popmovies.util.enumerations.ItemType;
import br.com.tiagohs.popmovies.view.AppBarMovieListener;
import br.com.tiagohs.popmovies.view.MovieDetailsView;
import br.com.tiagohs.popmovies.view.adapters.ListWordsAdapter;
import br.com.tiagohs.popmovies.view.callbacks.ImagesCallbacks;
import br.com.tiagohs.popmovies.view.callbacks.ListMoviesCallbacks;
import br.com.tiagohs.popmovies.view.callbacks.ListWordsCallbacks;
import br.com.tiagohs.popmovies.view.callbacks.MovieVideosCallbacks;
import br.com.tiagohs.popmovies.view.callbacks.PersonCallbacks;
import br.com.tiagohs.popmovies.view.fragment.MovieDetailsFragment;
import butterknife.BindView;
import butterknife.OnClick;

public class MovieDetailActivity extends BaseActivity implements MovieDetailsView,
        MovieVideosCallbacks, ImagesCallbacks,
        PersonCallbacks,
        ListMoviesCallbacks, ListWordsCallbacks {
    private static final String TAG = MovieDetailActivity.class.getSimpleName();

    private static final int REQ_START_STANDALONE_PLAYER = 1;
    private static final int REQ_RESOLVE_SERVICE_MISSING = 2;
    private static final int RECYCLER_VIEW_ORIENTATION = LinearLayoutManager.HORIZONTAL;

    private static final String EXTRA_MOVIE_ID = "br.com.tiagohs.popmovies.movie";

    @BindView(R.id.movie_detail_app_bar)          AppBarLayout mAppBarLayout;
    @BindView(R.id.poster_movie)                  ImageView mPosterMovie;
    @BindView(R.id.background_movie)              ImageView mBackgroundMovie;
    @BindView(R.id.title_movie)                   TextView mTitleMovie;
    @BindView(R.id.movie_details_raking_total)    TextView mRakingTotalMovie;
    @BindView(R.id.movie_details_votes)           TextView mVotesMovie;
    @BindView(R.id.diretores_recycler_view)       RecyclerView mDiretoresRecyclerView;
    @BindView(R.id.play_image_movie_principal)    ImageView playButtonImageView;
    @BindView(R.id.movie_details_favorite_button) LikeButton mFavoriteButton;
    @BindView(R.id.progress_movies_details)       ProgressBar mProgressMovieDetails;
    @BindView(R.id.movie_details_fragment)        LinearLayout mContainerTabs;
    @BindView(R.id.img_background_no_connection)  ImageView mBackgroundNoConnectionImage;

    @Inject MovieDetailsPresenter mPresenter;

    private int mMovieID;
    private MovieDetails mMovie;
    private ListWordsAdapter mDiretoresAdapter;

    public static Intent newIntent(Context context, int movieID) {
        Intent intent = new Intent(context, MovieDetailActivity.class);
        intent.putExtra(EXTRA_MOVIE_ID, movieID);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getApplicationComponent().inject(this);
        mPresenter.setView(this);
    }

    @Override
    protected int getActivityBaseViewID() {
        return R.layout.activity_movie_detail;
    }

    @Override
    protected void onStart() {
        super.onStart();

        mMovieID = (int) getIntent().getSerializableExtra(EXTRA_MOVIE_ID);
        mPresenter.getMovieDetails(mMovieID);
    }

    public void setupDirectorsRecyclerView(List<ItemListDTO> directors) {
        mDiretoresRecyclerView.setLayoutManager(new LinearLayoutManager(this, RECYCLER_VIEW_ORIENTATION, false));
        mDiretoresAdapter = new ListWordsAdapter(this, directors, this, ItemType.DIRECTORS);
        mDiretoresRecyclerView.setAdapter(mDiretoresAdapter);
    }

    public void updateUI(MovieDetails movie) {
        this.mMovie = movie;
//        AnimationSet mAnimationSet = new AnimationSet(false);
//
//        mAnimationSet.addAnimation(fadeIn);
//        mAnimationSet.addAnimation(fadeOut);
//        playButtonImageView.startAnimation(mAnimationSet);

        ImageUtils.loadWithRevealAnimation(this, mMovie.getBackdropPath(), mBackgroundMovie, R.drawable.ic_image_default_back, ImageSize.BACKDROP_780);
        ImageUtils.load(this, mMovie.getPosterPath(), mPosterMovie, mMovie.getTitle(), ImageSize.POSTER_185);

        mTitleMovie.setText(mMovie.getTitle());
        mRakingTotalMovie.setText(mMovie.getVoteAverage());
        mVotesMovie.setText(MovieUtils.formatAbrev(mMovie.getVoteCount()));


    }

    public void setupTabs() {

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.movie_details_fragment);

        if (fragment == null) {
            fm.beginTransaction()
                    .add(R.id.movie_details_fragment, MovieDetailsFragment.newInstance(mMovie))
                    .commit();
        }

        mAppBarLayout.addOnOffsetChangedListener(onOffsetChangedListener());
    }

    private AppBarMovieListener onOffsetChangedListener() {
        return new AppBarMovieListener() {

            @Override
            public void onExpanded(AppBarLayout appBarLayout) {
                mToolbar.setBackground(ViewUtils.getDrawableFromResource(getApplicationContext(), R.drawable.background_action_bar_transparent));
                mToolbar.setTitle("");
                mFavoriteButton.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCollapsed(AppBarLayout appBarLayout) {
                mToolbar.setBackgroundColor(ViewUtils.getColorFromResource(getApplicationContext(), R.color.colorPrimary));
                mToolbar.setTitle(mMovie.getTitle());
                mFavoriteButton.setVisibility(View.GONE);
            }

            @Override
            public void onIdle(AppBarLayout appBarLayout) {
                mToolbar.setBackground(ViewUtils.getDrawableFromResource(getApplicationContext(), R.drawable.background_action_bar_transparent));
            }
        };
    }

    @OnClick({R.id.play_image_movie_principal, R.id.background_movie})
    public void onClickBackgroundMovie() {

        if (isInternetConnected() && !mMovie.getVideos().isEmpty())
            inflateVideoPlayer(mMovie.getVideos().get(0).getKey());
    }

    public void updateVideos(VideosResponse videos) {
        mMovie.setVideos(videos);
    }


    @Override
    public void onClickVideo(Video video) {
        inflateVideoPlayer(video.getKey());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_OK) return;

        switch (requestCode) {
            case REQ_START_STANDALONE_PLAYER:
                YouTubeInitializationResult errorReason =
                        YouTubeStandalonePlayer.getReturnedInitializationResult(data);
                if (errorReason.isUserRecoverableError())
                    errorReason.getErrorDialog(this, 0).show();
                break;
        }

    }

    private void inflateVideoPlayer(String videoKey) {

        int startTimeMillis = 0;
        boolean autoplay = true;
        boolean lightboxMode = false;

        Intent intent = YouTubeStandalonePlayer.createVideoIntent(
                this, BuildConfig.GOOGLE_KEY, videoKey, startTimeMillis, autoplay, lightboxMode);

        if (intent != null) {
            if (isAuthResolveIntent(intent)) {
                startActivityForResult(intent, REQ_START_STANDALONE_PLAYER);
            } else {
                YouTubeInitializationResult.SERVICE_MISSING
                        .getErrorDialog(this, REQ_RESOLVE_SERVICE_MISSING).show();
            }
        }
    }

    private boolean isAuthResolveIntent(Intent intent) {
        List<ResolveInfo> resolveInfo = this.getPackageManager().queryIntentActivities(intent, 0);
        return resolveInfo != null && !resolveInfo.isEmpty();
    }

    @Override
    public void onMovieSelected(int movieID, ImageView posterMovie) {
        startActivity(MovieDetailActivity.newIntent(this, movieID));
    }

    @Override
    public void onClickPerson(int castID) {
        startActivity(PersonDetailActivity.newIntent(this, castID));
    }

    @Override
    public void onItemSelected(ItemListDTO item, ItemType itemType) {

        switch (itemType) {
            case GENRE:
                startActivity(MoviesByGenreActivity.newIntent(this, new Genre(item.getItemID(), item.getNameItem())));
                break;
            case DIRECTORS:
                onClickPerson(item.getItemID());
                break;
        }

    }

    @Override
    public void onClickImage(ImageDTO imageDTO) {

    }

    @Override
    public void setProgressVisibility(int visibityState) {
        mProgressMovieDetails.setVisibility(visibityState);
    }

    @Override
    public void setBackgroundNoConnectionImageVisibility(int visibilityState) {
        mBackgroundNoConnectionImage.setVisibility(visibilityState);
    }

    @Override
    public void setPlayButtonVisibility(int visibilityState) {
        playButtonImageView.setVisibility(visibilityState);
    }

    @Override
    public void setTabsVisibility(int visibilityState) {

        if (visibilityState == View.VISIBLE) {
            mContainerTabs.setAnimation(AnimationsUtils.createFadeInAnimation(1000));
            mContainerTabs.setVisibility(visibilityState);
        }

    }

    @OnClick(R.id.img_background_no_connection)
    public void onClickImageNoConnection() {
        mPresenter.getMovieDetails(mMovieID);
        mSnackbar.dismiss();
    }

    @Override
    protected View.OnClickListener onSnackbarClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.getMovieDetails(mMovieID);
                mSnackbar.dismiss();
            }
        };
    }
}
