package br.com.tiagohs.popmovies.presenter;

import android.content.Context;

import br.com.tiagohs.popmovies.data.repository.MovieRepository;
import br.com.tiagohs.popmovies.model.movie.MovieDetails;
import br.com.tiagohs.popmovies.server.ResponseListener;
import br.com.tiagohs.popmovies.view.MovieDetailsView;

public interface MovieDetailsPresenter extends BasePresenter<MovieDetailsView> {

    void getMovieDetails(int movieID, String tag);
    void onClickJaAssisti(MovieDetails movie, boolean buttonStage);
    void setMovieRepository(MovieRepository movieRepository);
    void setProfileID(long profileID);
}
