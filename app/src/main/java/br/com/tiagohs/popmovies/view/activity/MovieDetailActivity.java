package br.com.tiagohs.popmovies.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.tiagohs.popmovies.R;
import br.com.tiagohs.popmovies.model.Movie;
import br.com.tiagohs.popmovies.util.ImageSize;
import br.com.tiagohs.popmovies.util.ImageUtils;
import br.com.tiagohs.popmovies.util.MovieUtils;
import br.com.tiagohs.popmovies.view.AppBarMovieListener;
import br.com.tiagohs.popmovies.view.fragment.MovieDetailsAdapter;
import butterknife.BindView;

public class MovieDetailActivity extends BaseActivity {
    private static final String EXTRA_MOVIE_ID = "br.com.tiagohs.popmovies.movie";
    private static final String TAG = MovieDetailActivity.class.getSimpleName();

    @BindView(R.id.movie_view_pager)
    ViewPager mViewPager;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;

    @BindView(R.id.movie_detail_app_bar)
    AppBarLayout mAppBarLayout;

    @BindView(R.id.poster_movie)
    ImageView mPosterMovie;

    @BindView(R.id.background_movie)
    ImageView mBackgroundMovie;

    @BindView(R.id.title_movie)
    TextView mTitleMovie;

    @BindView(R.id.movie_details_raking_total)
    TextView mRakingTotalMovie;

    @BindView(R.id.movie_details_votes)
    TextView mVotesMovie;

    @BindView(R.id.movie_generos)
    TextView mGeneresMovie;

    private Movie mMovie;

    public static Intent newIntent(Context context, Movie movie) {
        Intent intent = new Intent(context, MovieDetailActivity.class);
        intent.putExtra(EXTRA_MOVIE_ID, movie);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mMovie = (Movie) getIntent().getSerializableExtra(EXTRA_MOVIE_ID);

        Log.i(TAG, "Movie ID: " + mMovie.getId());

        String[] tabs = new String[]{getString(R.string.tab_resumo),
                                     getString(R.string.tab_tecnico),
                                     getString(R.string.tab_reviews)};

        mViewPager.setAdapter(new MovieDetailsAdapter(getSupportFragmentManager(), mMovie, tabs));
        mTabLayout.setupWithViewPager(mViewPager);

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

        updateUI();
    }

    private void updateUI() {
        ImageUtils.load(this, mMovie.getBackdrop_path(), mBackgroundMovie, ImageSize.BACKDROP_780);
        ImageUtils.load(this, mMovie.getPoster_path(), mPosterMovie, ImageSize.POSTER_500);

        mTitleMovie.setText(mMovie.getTitle());
        mRakingTotalMovie.setText(mMovie.getVote_average());
        mVotesMovie.setText(MovieUtils.formatAbrev((long) Double.parseDouble(mMovie.getVote_average())));
        mGeneresMovie.setText(MovieUtils.formatGeneres(this, mMovie.getGenre_ids()));
    }
}
