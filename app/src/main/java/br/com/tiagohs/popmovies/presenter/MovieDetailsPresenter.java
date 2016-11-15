package br.com.tiagohs.popmovies.presenter;

import android.content.Context;

import br.com.tiagohs.popmovies.model.movie.MovieDetails;
import br.com.tiagohs.popmovies.server.ResponseListener;
import br.com.tiagohs.popmovies.view.MovieDetailsView;

public interface MovieDetailsPresenter extends BasePresenter<MovieDetailsView>, ResponseListener<MovieDetails> {

    void getMovieDetails(int movieID, String tag);
    void onClickJaAssisti(MovieDetails movie, boolean buttonStage);
    void setContext(Context context);
    void onResponse(MovieDetails response);
}
