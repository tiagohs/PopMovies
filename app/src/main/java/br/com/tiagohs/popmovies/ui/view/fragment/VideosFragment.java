package br.com.tiagohs.popmovies.ui.view.fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import br.com.tiagohs.popmovies.ui.contracts.VideosContract;
import br.com.tiagohs.popmovies.model.media.Translation;
import br.com.tiagohs.popmovies.model.media.Video;
import br.com.tiagohs.popmovies.ui.callbacks.MovieVideosCallbacks;
import br.com.tiagohs.popmovies.ui.adapters.VideoAdapter;
import br.com.tiagohs.popmovies.ui.tools.EndlessRecyclerView;
import br.com.tiagohs.popmovies.util.EmptyUtils;
import butterknife.BindView;

public class VideosFragment extends BaseFragment implements VideosContract.VideosView, MovieVideosCallbacks {
    private static final String TAG = VideosFragment.class.getSimpleName();

    private static final int REQ_START_STANDALONE_PLAYER = 1;
    private static final int REQ_RESOLVE_SERVICE_MISSING = 2;

    public static final String ARG_MOVIE_ID = "br.com.tiagohs.popmovies.movie_id";
    public static final String ARG_TRANSLATION = "br.com.tiagohs.popmovies.translation";

    @BindView(R.id.videos_recycler_view)            RecyclerView mVideosRecyclerView;
    @BindView(R.id.videos_principal_progress)       ProgressWheel mProgress;
    @BindView(R.id.videos_nao_encontrado)           TextView mVideosNaoEncontrados;

    private RecyclerView.LayoutManager mLayoutManager;
    private VideoAdapter mVideoAdapter;

    private List<Video> mVideos;

    private boolean mHasMorePages;

    @Inject
    VideosContract.VideosPresenter mPresenter;

    private List<Translation> mTranslations;
    private int mMovieID;

    public static VideosFragment newInstance(int movieID, List<Translation> translations) {
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_MOVIE_ID, movieID);
        bundle.putParcelableArrayList(ARG_TRANSLATION, (ArrayList<Translation>) translations);

        VideosFragment videosFragment = new VideosFragment();
        videosFragment.setArguments(bundle);
        return videosFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getApplicationComponent().inject(this);

        mTranslations = getArguments().getParcelableArrayList(ARG_TRANSLATION);
        mMovieID = getArguments().getInt(ARG_MOVIE_ID, 0);
        mVideos = new ArrayList<>();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mPresenter.onBindView(this);

        mPresenter.getVideos(mMovieID, mTranslations);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.onUnbindView();
    }

    public void onUpdateUI(List<Video> videos, boolean hasMorePages) {
        mHasMorePages = hasMorePages;
        setProgressVisibility(View.GONE);

        if (videos.isEmpty()) {
            mVideosNaoEncontrados.setVisibility(View.VISIBLE);
            mProgress.setVisibility(View.GONE);
        } else {
            setupRecyclerView(videos);
        }

    }

    public void setupRecyclerView(List<Video> videos) {

        if (mVideoAdapter == null) {
            mVideos.addAll(videos);

            int columnCount = getResources().getInteger(R.integer.images_movie_detail_columns);
            mLayoutManager = new GridLayoutManager(getActivity(), columnCount);
            mVideosRecyclerView.setLayoutManager(mLayoutManager);
            mVideosRecyclerView.addOnScrollListener(createOnScrollListener());
            mVideoAdapter = new VideoAdapter(getActivity(), videos, this);
            mVideosRecyclerView.setAdapter(mVideoAdapter);

        } else {

            mVideos.addAll(videos);
            mVideoAdapter.setVideos(mVideos);
            mVideoAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_OK) return;

        switch (requestCode) {
            case REQ_START_STANDALONE_PLAYER:
                YouTubeInitializationResult errorReason =
                        YouTubeStandalonePlayer.getReturnedInitializationResult(data);
                if (errorReason.isUserRecoverableError())
                    errorReason.getErrorDialog(getActivity(), 0).show();
                break;
        }

    }

    private void inflateVideoPlayer(String videoKey) {

        int startTimeMillis = 0;
        boolean autoplay = true;
        boolean lightboxMode = false;

        Intent intent = YouTubeStandalonePlayer.createVideoIntent(
                getActivity(), BuildConfig.GOOGLE_KEY, videoKey, startTimeMillis, autoplay, lightboxMode);

        if (EmptyUtils.isNotNull(intent)) {
            if (isAuthResolveIntent(intent)) {
                startActivityForResult(intent, REQ_START_STANDALONE_PLAYER);
            } else {
                YouTubeInitializationResult.SERVICE_MISSING
                        .getErrorDialog(getActivity(), REQ_RESOLVE_SERVICE_MISSING).show();
            }
        }
    }

    private boolean isAuthResolveIntent(Intent intent) {
        List<ResolveInfo> resolveInfo = getContext().getPackageManager().queryIntentActivities(intent, 0);
        return EmptyUtils.isNotNull(resolveInfo) && !resolveInfo.isEmpty();
    }

    private RecyclerView.OnScrollListener createOnScrollListener() {
        return new EndlessRecyclerView(mLayoutManager) {

            @Override
            public void onLoadMore(int current_page) {
                if(mHasMorePages) {
                    mPresenter.getVideosByPage();
                }

            }
        };
    }

    @Override
    public void onClickVideo(Video video) {
        inflateVideoPlayer(video.getKey());
    }

    @Override
    public void setProgressVisibility(int visibityState) {
        mProgress.setVisibility(visibityState);
    }


    @Override
    protected int getViewID() {
        return R.layout.fragment_videos;
    }

    @Override
    protected View.OnClickListener onSnackbarClickListener() {
        return null;
    }
}
