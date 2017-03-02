package br.com.tiagohs.popmovies.ui.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import br.com.tiagohs.popmovies.R;
import br.com.tiagohs.popmovies.model.media.Translation;
import br.com.tiagohs.popmovies.ui.view.fragment.VideosFragment;
import br.com.tiagohs.popmovies.util.EmptyUtils;

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
        intent.putParcelableArrayListExtra(ARG_TRANSLATION, (ArrayList<Translation>) translations);
        intent.putExtra(ARG_MOVIE_ID, movieID);
        intent.putExtra(ARG_TITLE_PAGE, pageTitle);
        intent.putExtra(ARG_SUBTITLE_PAGE, pageSubtitle);

        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTranslations = getIntent().getParcelableArrayListExtra(ARG_TRANSLATION);
        mMovieID = getIntent().getIntExtra(ARG_MOVIE_ID, 0);
        mPageTitle = getIntent().getStringExtra(ARG_TITLE_PAGE);
        mPageSubtitle = getIntent().getStringExtra(ARG_SUBTITLE_PAGE);

        mToolbar.setTitle(mPageTitle);

        if (EmptyUtils.isNotNull(mPageSubtitle))
            mToolbar.setSubtitle(mPageSubtitle);

        startFragment(R.id.content_fragment, VideosFragment.newInstance(mMovieID, mTranslations));
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
        return R.layout.activity_default;
    }


}
