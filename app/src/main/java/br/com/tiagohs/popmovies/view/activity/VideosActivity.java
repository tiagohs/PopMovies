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
import br.com.tiagohs.popmovies.view.fragment.EndlessRecyclerView;
import br.com.tiagohs.popmovies.view.fragment.VideosFragment;
import butterknife.BindView;

public class VideosActivity extends BaseActivity {
    private static final String TAG = VideosActivity.class.getSimpleName();

    public static final String ARG_MOVIE_ID = "br.com.tiagohs.popmovies.movie_id";
    public static final String ARG_TITLE_PAGE = "br.com.tiagohs.popmovies.title_page_wall";
    public static final String ARG_SUBTITLE_PAGE = "br.com.tiagohs.popmovies.subtitle_page_wall";
    public static final String ARG_TRANSLATION = "br.com.tiagohs.popmovies.translation";

    private String mPageTitle;
    private String mPageSubtitle;
    private List<Translation> mTranslations;
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

        mTranslations = (ArrayList<Translation>) getIntent().getSerializableExtra(ARG_TRANSLATION);
        mMovieID = getIntent().getIntExtra(ARG_MOVIE_ID, 0);
        mPageTitle = getIntent().getStringExtra(ARG_TITLE_PAGE);
        mPageSubtitle = getIntent().getStringExtra(ARG_SUBTITLE_PAGE);

        mToolbar.setTitle(mPageTitle);

        if (mPageSubtitle != null)
            mToolbar.setSubtitle(mPageSubtitle);

        startFragment(R.id.fragment_videos, VideosFragment.newInstance(mMovieID, mTranslations));
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
    protected int getMenuLayoutID() {
        return 0;
    }

    @Override
    protected int getActivityBaseViewID() {
        return R.layout.activity_videos;
    }


}
