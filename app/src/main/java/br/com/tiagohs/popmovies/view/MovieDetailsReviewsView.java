package br.com.tiagohs.popmovies.view;

import java.util.List;

import br.com.tiagohs.popmovies.model.review.Review;

/**
 * Created by Tiago Henrique on 07/10/2016.
 */

public interface MovieDetailsReviewsView extends BaseView {

    void setTomatoesUrl(String tomatoesUrl);
    void setTomatoesRankingVisibility(int visibility);
    void setReviewsVisibility(int visibility);

    void setListReviews(List<Review> reviews, boolean hasMorePages);
    void addAllReviews(List<Review> reviews, boolean hasMorePages);
    void setupRecyclerView();
    void updateAdapter();
}
