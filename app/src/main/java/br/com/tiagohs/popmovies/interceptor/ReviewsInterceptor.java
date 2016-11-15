package br.com.tiagohs.popmovies.interceptor;

import com.android.volley.VolleyError;

import java.util.List;

import br.com.tiagohs.popmovies.model.media.Translation;
import br.com.tiagohs.popmovies.model.response.GenericListResponse;
import br.com.tiagohs.popmovies.model.review.Review;

public interface ReviewsInterceptor {

    interface onReviewsListener {

        void onReviewsRequestSucess(GenericListResponse<Review> moviesResponse);
        void onReviewsRequestError(VolleyError error);
    }

    void getReviews(int movieID, int page, String tag, List<Translation> translations);
}
