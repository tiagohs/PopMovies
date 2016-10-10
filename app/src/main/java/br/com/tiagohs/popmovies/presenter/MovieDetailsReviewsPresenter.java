package br.com.tiagohs.popmovies.presenter;

import java.util.List;

import br.com.tiagohs.popmovies.model.media.Translation;
import br.com.tiagohs.popmovies.view.MovieDetailsReviewsView;

public interface MovieDetailsReviewsPresenter extends BasePresenter<MovieDetailsReviewsView> {

    void getRankings(String imdbID);
    void getReviews(int movieID, List<Translation> translations);


}
