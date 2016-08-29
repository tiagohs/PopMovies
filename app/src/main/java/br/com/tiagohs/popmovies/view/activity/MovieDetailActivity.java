package br.com.tiagohs.popmovies.view.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeStandalonePlayer;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.com.tiagohs.popmovies.App;
import br.com.tiagohs.popmovies.BuildConfig;
import br.com.tiagohs.popmovies.R;
import br.com.tiagohs.popmovies.model.Movie.MovieDetails;
import br.com.tiagohs.popmovies.model.atwork.Artwork;
import br.com.tiagohs.popmovies.model.credits.MediaCreditCrew;
import br.com.tiagohs.popmovies.model.media.Video;
import br.com.tiagohs.popmovies.model.response.VideosResponse;
import br.com.tiagohs.popmovies.presenter.MovieDetailsPresenter;
import br.com.tiagohs.popmovies.util.ImageSize;
import br.com.tiagohs.popmovies.util.ImageUtils;
import br.com.tiagohs.popmovies.util.MovieUtils;
import br.com.tiagohs.popmovies.view.AppBarMovieListener;
import br.com.tiagohs.popmovies.view.MovieDetailsView;
import br.com.tiagohs.popmovies.view.adapters.DiretoresAdapter;
import br.com.tiagohs.popmovies.view.fragment.MovieDetailsFragment;
import br.com.tiagohs.popmovies.view.fragment.MovieDetailsMidiaFragment;
import br.com.tiagohs.popmovies.view.fragment.MovieDetailsOverviewFragment;
import butterknife.BindView;
import butterknife.OnClick;

public class MovieDetailActivity extends BaseActivity implements MovieDetailsView, MovieDetailsMidiaFragment.Callbacks, MovieDetailsOverviewFragment.Callbacks {
    private static final String TAG = MovieDetailActivity.class.getSimpleName();

    private static final int REQ_START_STANDALONE_PLAYER = 1;
    private static final int REQ_RESOLVE_SERVICE_MISSING = 2;

    private static final String EXTRA_MOVIE_ID = "br.com.tiagohs.popmovies.movie";

    @BindView(R.id.toolbar)                    Toolbar mToolbar;
    @BindView(R.id.movie_detail_app_bar)       AppBarLayout mAppBarLayout;
    @BindView(R.id.poster_movie)               ImageView mPosterMovie;
    @BindView(R.id.background_movie)           ImageView mBackgroundMovie;
    @BindView(R.id.title_movie)                TextView mTitleMovie;
    @BindView(R.id.movie_details_raking_total) TextView mRakingTotalMovie;
    @BindView(R.id.movie_details_votes)        TextView mVotesMovie;
    @BindView(R.id.diretores_recycler_view)    RecyclerView mDiretoresRecyclerView;
    @BindView(R.id.play_image_movie_principal) ImageView playButtonImageView;

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
        setContentView(R.layout.activity_movie_detail);
        //setupWindowAnimations();
        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        ((App) getApplication()).getPopMoviesComponent().inject(this);

        mPresenter.setView(this);

        mDiretoresRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        updateDirectors();

        mMovieID = (int) getIntent().getSerializableExtra(EXTRA_MOVIE_ID);
        mPresenter.getMovieDetails(mMovieID, new String[]{"credits", "alternative_titles", "release_dates", "similar", "keywords", "videos"});

        Log.i(TAG, "Movie ID: " + mMovieID);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setupWindowAnimations() {
        Slide slide = new Slide(Gravity.RIGHT);
        slide.setDuration(1000);
        getWindow().setExitTransition(slide);
    }

    public void updateUI(MovieDetails movie) {
        this.mMovie = movie;

        if (mMovie.getVideos().isEmpty()) mPresenter.getVideos(mMovieID);

        ImageUtils.load(this, mMovie.getBackdropPath(), mBackgroundMovie, ImageSize.BACKDROP_780);
        ImageUtils.load(this, mMovie.getPosterPath(), mPosterMovie, ImageSize.POSTER_500);

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

        mAppBarLayout.addOnOffsetChangedListener(new AppBarMovieListener() {

            @Override
            public void onExpanded(AppBarLayout appBarLayout) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                    mToolbar.setBackground(getDrawable(R.drawable.toobar_transparent));
                else
                    mToolbar.setBackground(getResources().getDrawable(R.drawable.toobar_transparent));
                mToolbar.setTitle("");
            }

            @Override
            public void onCollapsed(AppBarLayout appBarLayout) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                    mToolbar.setBackgroundColor(getColor(R.color.colorPrimary));
                else
                    mToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

                mToolbar.setTitle(mMovie.getTitle());
            }

            @Override
            public void onIdle(AppBarLayout appBarLayout) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                    mToolbar.setBackground(getDrawable(R.drawable.toobar_transparent));
                else
                    mToolbar.setBackground(getResources().getDrawable(R.drawable.toobar_transparent));
            }
        });
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
}
