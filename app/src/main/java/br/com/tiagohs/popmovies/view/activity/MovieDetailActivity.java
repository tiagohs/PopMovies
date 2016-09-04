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
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeStandalonePlayer;
import com.like.LikeButton;
import com.pnikosis.materialishprogress.ProgressWheel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.com.tiagohs.popmovies.BuildConfig;
import br.com.tiagohs.popmovies.R;
import br.com.tiagohs.popmovies.model.movie.Genre;
import br.com.tiagohs.popmovies.model.movie.MovieDetails;
import br.com.tiagohs.popmovies.model.atwork.Artwork;
import br.com.tiagohs.popmovies.model.credits.MediaCreditCrew;
import br.com.tiagohs.popmovies.model.media.Video;
import br.com.tiagohs.popmovies.model.response.VideosResponse;
import br.com.tiagohs.popmovies.presenter.MovieDetailsPresenter;
import br.com.tiagohs.popmovies.util.ImageUtils;
import br.com.tiagohs.popmovies.util.MovieUtils;
import br.com.tiagohs.popmovies.util.ViewUtils;
import br.com.tiagohs.popmovies.util.enumerations.ImageSize;
import br.com.tiagohs.popmovies.util.enumerations.SubMethod;
import br.com.tiagohs.popmovies.view.AppBarMovieListener;
import br.com.tiagohs.popmovies.view.MovieDetailsView;
import br.com.tiagohs.popmovies.view.adapters.DiretoresAdapter;
import br.com.tiagohs.popmovies.view.fragment.MovieDetailsFragment;
import br.com.tiagohs.popmovies.view.fragment.MovieDetailsMidiaFragment;
import br.com.tiagohs.popmovies.view.fragment.MovieDetailsOverviewFragment;
import br.com.tiagohs.popmovies.view.fragment.MovieDetailsTecnicoFragment;
import butterknife.BindView;
import butterknife.OnClick;

public class MovieDetailActivity extends BaseActivity implements MovieDetailsView,
        MovieDetailsMidiaFragment.Callbacks, MovieDetailsOverviewFragment.Callbacks, MovieDetailsTecnicoFragment.Callbacks {
    private static final String TAG = MovieDetailActivity.class.getSimpleName();

    private static final int REQ_START_STANDALONE_PLAYER = 1;
    private static final int REQ_RESOLVE_SERVICE_MISSING = 2;
    private static final int RECYCLER_VIEW_ORIENTATION = LinearLayoutManager.HORIZONTAL;

    private static final String EXTRA_MOVIE_ID = "br.com.tiagohs.popmovies.movie";

    private String[] mMovieParameters = new String[]{SubMethod.CREDITS.getValue(), SubMethod.RELEASE_DATES.getValue(),
                                              SubMethod.SIMILAR.getValue(), SubMethod.KEYWORDS.getValue(),
                                              SubMethod.VIDEOS.getValue()};

    @BindView(R.id.movie_detail_app_bar)          AppBarLayout mAppBarLayout;
    @BindView(R.id.poster_movie)                  ImageView mPosterMovie;
    @BindView(R.id.background_movie)              ImageView mBackgroundMovie;
    @BindView(R.id.title_movie)                   TextView mTitleMovie;
    @BindView(R.id.movie_details_raking_total)    TextView mRakingTotalMovie;
    @BindView(R.id.movie_details_votes)           TextView mVotesMovie;
    @BindView(R.id.diretores_recycler_view)       RecyclerView mDiretoresRecyclerView;
    @BindView(R.id.play_image_movie_principal)    ImageView playButtonImageView;
    @BindView(R.id.movie_details_favorite_button) LikeButton mFavoriteButton;
    @BindView(R.id.movie_details_poster_progress) ProgressWheel mProgressPoster;
    @BindView(R.id.movie_details_header_progress) ProgressWheel mProgressHeader;

    @Inject MovieDetailsPresenter mPresenter;

    private int mMovieID;
    private MovieDetails mMovie;
    private DiretoresAdapter mDiretoresAdapter;

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
        onSetupRecyclerView();
    }

    @Override
    protected int getActivityBaseViewID() {
        return R.layout.activity_movie_detail;
    }

    @Override
    protected void onStart() {
        super.onStart();

        mMovieID = (int) getIntent().getSerializableExtra(EXTRA_MOVIE_ID);
        mPresenter.getMovieDetails(mMovieID, mMovieParameters);
    }



    private void onSetupRecyclerView() {
        mDiretoresRecyclerView.setLayoutManager(new LinearLayoutManager(this, RECYCLER_VIEW_ORIENTATION, false));
        updateDirectors();
    }

    public void updateUI(MovieDetails movie) {
        this.mMovie = movie;

        if (mMovie.getVideos().isEmpty()) mPresenter.getVideos(mMovieID);

        ImageUtils.load(this, mMovie.getBackdropPath(), mBackgroundMovie, R.drawable.placeholder_images_default, R.drawable.placeholder_images_default, ImageSize.BACKDROP_780, mProgressHeader);
        ImageUtils.load(this, mMovie.getPosterPath(), mPosterMovie, R.drawable.placeholder_images_default, R.drawable.placeholder_images_default, ImageSize.POSTER_500, mProgressPoster);

        mTitleMovie.setText(mMovie.getTitle());
        mRakingTotalMovie.setText(mMovie.getVoteAverage());
        mVotesMovie.setText(MovieUtils.formatAbrev((long) mMovie.getVoteCount()));

        updateDirectors();
        updateTabs();
    }

    private void updateDirectors() {

        if (mDiretoresAdapter == null) {
            mDiretoresAdapter = new DiretoresAdapter(this, new ArrayList<MediaCreditCrew>());
            mDiretoresRecyclerView.setAdapter(mDiretoresAdapter);
        } else {
            mDiretoresAdapter.setDirectors(mMovie.getCrew());
            mDiretoresAdapter.notifyDataSetChanged();
        }
    }

    private void updateTabs() {

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
    public void onClickPosterTop() {

        if (!mMovie.getVideos().isEmpty())
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
    public void onClickImage(Artwork image) {

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
    public void onGenreSelected(Genre genre) {
        startActivity(MoviesByGenreActivity.newIntent(this, genre));
    }

    @Override
    public void onClickCast(int castID) {
        startActivity(PersonDetailActivity.newIntent(this, castID));
    }

    @Override
    public void onClickCrew(int crewID) {

    }
}
