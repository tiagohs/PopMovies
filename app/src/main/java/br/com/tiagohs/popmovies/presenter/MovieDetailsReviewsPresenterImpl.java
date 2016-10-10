package br.com.tiagohs.popmovies.presenter;

import android.view.View;

import com.android.volley.VolleyError;

import java.util.List;

import br.com.tiagohs.popmovies.interceptor.ReviewsInterceptor;
import br.com.tiagohs.popmovies.interceptor.ReviewsInterceptorImpl;
import br.com.tiagohs.popmovies.model.media.Translation;
import br.com.tiagohs.popmovies.model.response.GenericListResponse;
import br.com.tiagohs.popmovies.model.response.RankingResponse;
import br.com.tiagohs.popmovies.model.review.Review;
import br.com.tiagohs.popmovies.server.PopMovieServer;
import br.com.tiagohs.popmovies.server.ResponseListener;
import br.com.tiagohs.popmovies.view.MovieDetailsReviewsView;

public class MovieDetailsReviewsPresenterImpl implements MovieDetailsReviewsPresenter, ResponseListener<RankingResponse>, ReviewsInterceptor.onReviewsListener{
    private MovieDetailsReviewsView mView;
    private PopMovieServer mPopMovieServer;
    private ReviewsInterceptor mReviewsInterceptor;

    private int mCurrentPage;
    private int mTotalPages;

    public MovieDetailsReviewsPresenterImpl() {
        mPopMovieServer = PopMovieServer.getInstance();
        mReviewsInterceptor = new ReviewsInterceptorImpl(this);
        mCurrentPage = 0;
    }
    @Override
    public void setView(MovieDetailsReviewsView view) {
        this.mView = view;
    }

    public void getRankings(String imdbID) {

        if (mView.isInternetConnected()) {
            mPopMovieServer.getMovieRankings(imdbID, this);
        } else
            noConnectionError();

    }


    public void getReviews(int movieID, List<Translation> translations) {
        mView.setReviewsVisibility(View.GONE);

        if (mView.isInternetConnected()) {
            mView.setProgressVisibility(View.VISIBLE);
            mReviewsInterceptor.getReviews(movieID, ++mCurrentPage, translations);
        } else
            noConnectionError();

    }

    private void noConnectionError() {
        mView.onError("Sem Conexão");
        mView.setProgressVisibility(View.GONE);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        mView.setTomatoesRankingVisibility(View.GONE);
    }

    @Override
    public void onResponse(RankingResponse response) {

        if (response.getTomatoURL() == null)
            mView.setTomatoesRankingVisibility(View.GONE);

        mView.setTomatoesUrl(response.getTomatoURL());
    }

    @Override
    public void onReviewsRequestSucess(GenericListResponse<Review> moviesResponse) {
        mCurrentPage = moviesResponse.getPage();
        mTotalPages = moviesResponse.getTotalPage();

        if (isFirstPage()) {
            mView.setListReviews(moviesResponse.getResults(), hasMorePages());
            mView.setupRecyclerView();
        } else {
            mView.addAllReviews(moviesResponse.getResults(), hasMorePages());
            mView.updateAdapter();
        }

        mView.setProgressVisibility(View.GONE);
        mView.setReviewsVisibility(View.VISIBLE);
    }

    private boolean isFirstPage() {
        return mCurrentPage == 1;
    }

    private boolean hasMorePages() {
        return mCurrentPage < mTotalPages;
    }

    @Override
    public void onReviewsRequestError(VolleyError error) {
        mView.setProgressVisibility(View.GONE);
        mView.onError("Reviews: Erro ao carregar as reviews, tente novamente.");
    }
}
