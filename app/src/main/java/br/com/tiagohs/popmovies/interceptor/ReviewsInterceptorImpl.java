package br.com.tiagohs.popmovies.interceptor;

import com.android.volley.VolleyError;

import java.util.List;

import br.com.tiagohs.popmovies.model.media.Translation;
import br.com.tiagohs.popmovies.model.response.GenericListResponse;
import br.com.tiagohs.popmovies.model.review.Review;
import br.com.tiagohs.popmovies.server.ResponseListener;
import br.com.tiagohs.popmovies.server.methods.MoviesServer;

public class ReviewsInterceptorImpl implements ReviewsInterceptor, ResponseListener<GenericListResponse<Review>> {

    private MoviesServer mMoviesServer;
    private ReviewsInterceptor.onReviewsListener mOnReviewsListener;

    private GenericListResponse<Review> mFinalReviewsReponse;

    private List<Translation> mTranslations;

    private int mCurrentTranslation;
    private int mTotalTranslation;
    private int mMovieID;
    private int mPage;
    private String mTag;

    public ReviewsInterceptorImpl(ReviewsInterceptor.onReviewsListener reviewsListener) {
        mMoviesServer = new MoviesServer();
        mOnReviewsListener = reviewsListener;
    }

    @Override
    public void getReviews(int movieID, int page, String tag, List<Translation> translations) {
        mTranslations = translations;
        mTotalTranslation = translations.size() - 1;
        mMovieID = movieID;
        mPage = page;
        mTag = tag;

        mMoviesServer.getMovieReviews(movieID, page, mTranslations.get(mCurrentTranslation).getLanguage() + "-" + mTranslations.get(0).getCountry(), tag, this);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        mOnReviewsListener.onReviewsRequestError(error);
    }

    @Override
    public void onResponse(GenericListResponse<Review> response) {

        if (isFirstTranslation())
            mFinalReviewsReponse = response;

        if (containsTranslations()) {
            mFinalReviewsReponse.getResults().addAll(response.getResults());
            mMoviesServer.getMovieReviews(mMovieID, mPage, mTranslations.get(++mCurrentTranslation).getLanguage() + "-" + mTranslations.get(0).getCountry(), mTag, this);
        } else {
            mOnReviewsListener.onReviewsRequestSucess(mFinalReviewsReponse);
        }


    }

    private boolean containsTranslations() {
        return mCurrentTranslation < mTotalTranslation;
    }

    private boolean isFirstTranslation() {
        return mCurrentTranslation == 0;
    }
}
