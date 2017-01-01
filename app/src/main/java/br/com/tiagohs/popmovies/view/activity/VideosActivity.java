package br.com.tiagohs.popmovies.view.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeStandalonePlayer;
import com.pnikosis.materialishprogress.ProgressWheel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.com.tiagohs.popmovies.BuildConfig;
import br.com.tiagohs.popmovies.R;
import br.com.tiagohs.popmovies.model.dto.ImageDTO;
import br.com.tiagohs.popmovies.model.media.Translation;
import br.com.tiagohs.popmovies.model.media.Video;
import br.com.tiagohs.popmovies.model.response.VideosResponse;
import br.com.tiagohs.popmovies.presenter.VideosPresenter;
import br.com.tiagohs.popmovies.view.VideosView;
import br.com.tiagohs.popmovies.view.adapters.ImageAdapter;
import br.com.tiagohs.popmovies.view.adapters.VideoAdapter;
import br.com.tiagohs.popmovies.view.callbacks.ImagesCallbacks;
import br.com.tiagohs.popmovies.view.callbacks.MovieVideosCallbacks;
import butterknife.BindView;

public class VideosActivity extends BaseActivity implements VideosView, MovieVideosCallbacks {
    private static final String TAG = VideosActivity.class.getSimpleName();

    private static final int REQ_START_STANDALONE_PLAYER = 1;
    private static final int REQ_RESOLVE_SERVICE_MISSING = 2;

    public static final String ARG_MOVIE_ID = "br.com.tiagohs.popmovies.movie_id";
    public static final String ARG_TITLE_PAGE = "br.com.tiagohs.popmovies.title_page_wall";
    public static final String ARG_SUBTITLE_PAGE = "br.com.tiagohs.popmovies.subtitle_page_wall";
    public static final String ARG_TRANSLATION = "br.com.tiagohs.popmovies.translation";

    @BindView(R.id.videos_recycler_view)
    RecyclerView mVideosRecyclerView;
    @BindView(R.id.videos_principal_progress)
    ProgressWheel mProgress;
    @BindView(R.id.videos_nao_encontrado)
    TextView mVideosNaoEncontrados;

    @Inject
    VideosPresenter mPresenter;

    private String mPageTitle;
    private String mPageSubtitle;
    List<Translation> mTranslations;

    private int mMovieID;

    public static Intent newIntent(Context context, int movieID, List<Translation> translations, String pageTitle, String pageSubtitle) {
        Intent intent = new Intent(context, VideosActivity.class);
        intent.putExtra(ARG_TRANSLATION, (ArrayList<Translation>) translations);
        intent.putExtra(ARG_MOVIE_ID, movieID);
        intent.putExtra(ARG_TITLE_PAGE, pageTitle);
        intent.putExtra(ARG_SUBTITLE_PAGE, pageSubtitle);

        return intent;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getApplicationComponent().inject(this);
        mPresenter.setView(this);

        mTranslations = (ArrayList<Translation>) getIntent().getSerializableExtra(ARG_TRANSLATION);
        mMovieID = getIntent().getIntExtra(ARG_MOVIE_ID, 0);
        mPageTitle = getIntent().getStringExtra(ARG_TITLE_PAGE);
        mPageSubtitle = getIntent().getStringExtra(ARG_SUBTITLE_PAGE);
    }

    @Override
    protected void onStart() {
        super.onStart();

        mToolbar.setTitle(mPageTitle);

        if (mPageSubtitle != null)
            mToolbar.setSubtitle(mPageSubtitle);

        mPresenter.getVideos(mMovieID, mTranslations, TAG);
    }

    @Override
    public void onPause() {
        super.onPause();

        if (mPresenter != null)
            mPresenter.onCancellRequest(this, TAG);
    }

    public void onUpdateUI(VideosResponse videos) {

        if (videos.getVideos().isEmpty()) {
            mVideosNaoEncontrados.setVisibility(View.VISIBLE);
            mProgress.setVisibility(View.GONE);
        } else {
            int columnCount = getResources().getInteger(R.integer.images_movie_detail_columns);
            mVideosRecyclerView.setLayoutManager(new GridLayoutManager(this, columnCount));
            mVideosRecyclerView.setNestedScrollingEnabled(false);
            mVideosRecyclerView.setAdapter(new VideoAdapter(this, videos.getVideos(), this));
            mProgress.setVisibility(View.GONE);
        }

    }

    @Override
    protected View.OnClickListener onSnackbarClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSnackbar.dismiss();
            }
        };
    }

    @Override
    protected int getActivityBaseViewID() {
        return R.layout.activity_videos;
    }

    @Override
    public void setProgressVisibility(int visibityState) {
        mProgress.setVisibility(visibityState);
    }

    @Override
    public boolean isAdded() {
        return this != null;
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


}
