package br.com.tiagohs.popmovies.view.fragment;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.pnikosis.materialishprogress.ProgressWheel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.com.tiagohs.popmovies.R;
import br.com.tiagohs.popmovies.model.movie.MovieDetails;
import br.com.tiagohs.popmovies.model.review.Review;
import br.com.tiagohs.popmovies.presenter.MovieDetailsReviewsPresenter;
import br.com.tiagohs.popmovies.view.MovieDetailsReviewsView;
import br.com.tiagohs.popmovies.view.activity.WebViewActivity;
import br.com.tiagohs.popmovies.view.adapters.ReviewsAdaper;
import br.com.tiagohs.popmovies.view.callbacks.ReviewCallbacks;
import butterknife.BindView;
import butterknife.OnClick;

public class MovieDetailsReviewsFragment extends BaseFragment implements MovieDetailsReviewsView {
    private static final String TAG = MovieDetailsOverviewFragment.class.getSimpleName();
    private static final String ARG_MOVIE = "movie";

    private static final String ARG_REVIEWS = "reviewsSaved";
    private static final String ARG_TOMATOES_URL = "tomatoesURLSaved";
    private static final String ARG_MOVIE_SAVED = "movieSaved";

    @BindView(R.id.imdb_reviews_container)              CardView mImdbContainer;
    @BindView(R.id.tomatoes_reviews_container)          CardView mTomatoesContainer;
    @BindView(R.id.reviews_container)                   FrameLayout mReviewsContainer;
    @BindView(R.id.label_imdb)                          TextView mImdbLabel;
    @BindView(R.id.label_imdb_extra)                    TextView mImdbLabelExtra;
    @BindView(R.id.label_tomatoes)                      TextView mTomatoesLabel;
    @BindView(R.id.label_tomatoes_extra)                TextView mTomatoesLabelExtra;
    @BindView(R.id.reviews_progress)                    ProgressWheel mProgress;
    @BindView(R.id.reviews_recycler_view)               RecyclerView mReviewsRecyclerView;

    @Inject
    MovieDetailsReviewsPresenter mPresenter;

    private MovieDetails mMovieDetails;
    private String mTomatoesUrl;
    private boolean hasMorePages;
    private List<Review> mReviews;
    private LinearLayoutManager mLinearLayoutManager;
    private ReviewsAdaper mReviewsAdaper;
    private ReviewCallbacks mCallbacks;

    public MovieDetailsReviewsFragment() {

    }

    public static MovieDetailsReviewsFragment newInstance(MovieDetails movieDetails) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_MOVIE, movieDetails);

        MovieDetailsReviewsFragment movieDetailsReviewsFragment = new MovieDetailsReviewsFragment();
        movieDetailsReviewsFragment.setArguments(bundle);
        return movieDetailsReviewsFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallbacks = (ReviewCallbacks) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getApplicationComponent().inject(this);

        mPresenter.setView(this);

        if (savedInstanceState != null) {
            mReviews = (ArrayList<Review>) savedInstanceState.getSerializable(ARG_REVIEWS);
            mTomatoesUrl = savedInstanceState.getString(ARG_TOMATOES_URL);
            mMovieDetails = (MovieDetails) savedInstanceState.getSerializable(ARG_MOVIE_SAVED);
        } else {
            mMovieDetails = (MovieDetails) getArguments().getSerializable(ARG_MOVIE);
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        init();
    }

    private void init() {
        updateView();
        configureExternalLinks();
        configureReviews();
    }

    @Override
    public void onPause() {
        super.onPause();

        if (mPresenter != null)
            mPresenter.onCancellRequest(getActivity(), TAG);
    }

    private void configureExternalLinks() {
        if (mMovieDetails.getImdbID() != null) {
            if (mTomatoesUrl == null)
                mPresenter.getRankings(mMovieDetails.getImdbID(), TAG);
        } else {
            setImdbRankingVisibility(View.GONE);
            setTomatoesRankingVisibility(View.GONE);
        }
    }

    private void configureReviews() {
        if (mReviews == null) {
            mReviews = new ArrayList<>();
            mPresenter.getReviews(mMovieDetails.getId(), TAG, mMovieDetails.getTranslations());
        } else {
            setupRecyclerView();
            setProgressVisibility(View.GONE);
            setReviewsVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (!mReviews.isEmpty())
            outState.putSerializable(ARG_REVIEWS, (ArrayList<Review>) mReviews);
        if (mTomatoesUrl != null)
            outState.putString(ARG_TOMATOES_URL, mTomatoesUrl);
        if (mMovieDetails != null)
            outState.putSerializable(ARG_MOVIE_SAVED, mMovieDetails);
    }

    private void updateView() {
        mImdbLabel.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "opensans.ttf"));
        mTomatoesLabel.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "opensans.ttf"));
        mTomatoesLabelExtra.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "opensans.ttf"));
        mImdbLabelExtra.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "opensans.ttf"));
    }

    public void setTomatoesUrl(String tomatoesUrl) {
        mTomatoesUrl = tomatoesUrl;
    }

    @Override
    protected int getViewID() {
        return R.layout.fragment_movie_detail_reviews;
    }

    public void setListReviews(List<Review> reviews, boolean hasMorePages) {
        mReviews = reviews;
        this.hasMorePages = hasMorePages;
    }

    public void addAllReviews(List<Review> reviews, boolean hasMorePages) {
        mReviews.addAll(reviews);
        this.hasMorePages = hasMorePages;
    }

    public void setupRecyclerView() {
        mLinearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        mReviewsRecyclerView.setLayoutManager(mLinearLayoutManager);
        mReviewsRecyclerView.addOnScrollListener(createOnScrollListener());
        setupAdapter();
    }

    public void updateAdapter() {
        mReviewsAdaper.notifyDataSetChanged();
    }

    private void setupAdapter() {
        if (mReviewsAdaper == null) {
            mReviewsAdaper = new ReviewsAdaper(getActivity(), mReviews, mCallbacks);
            mReviewsRecyclerView.setAdapter(mReviewsAdaper);
        } else
            updateAdapter();
    }

    private RecyclerView.OnScrollListener createOnScrollListener() {
        return new EndlessRecyclerView(mLinearLayoutManager) {

            @Override
            public void onLoadMore(int current_page) {
                if(hasMorePages)
                    mPresenter.getReviews(mMovieDetails.getId(), TAG, mMovieDetails.getTranslations());
            }
        };
    }

    @Override
    protected View.OnClickListener onSnackbarClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                init();
                mSnackbar.dismiss();
            }
        };
    }

    @OnClick(R.id.imdb_ranking_riple)
    public void onClickIMDBReviews() {
        if (mMovieDetails.getImdbID() != null)
            startActivityForResult(WebViewActivity.newIntent(getActivity(), getString(R.string.imdb_reviews_link, mMovieDetails.getImdbID()), mMovieDetails.getTitle()), 0);
    }

    @OnClick(R.id.tomatoes_ranking_riple)
    public void onClickTomatoesReviews() {
        if (mTomatoesUrl != null)
            startActivityForResult(WebViewActivity.newIntent(getActivity(), getString(R.string.tomatoes_reviews_link, mTomatoesUrl), mMovieDetails.getTitle()), 0);
    }

    public void setImdbRankingVisibility(int visibility) {
        mImdbContainer.setVisibility(visibility);
    }

    public void setTomatoesRankingVisibility(int visibility) {
        mTomatoesContainer.setVisibility(visibility);
    }

    public void setReviewsVisibility(int visibility) {
        mReviewsContainer.setVisibility(visibility);
    }

    @Override
    public void setProgressVisibility(int visibityState) {
        mProgress.setVisibility(visibityState);
    }
}
