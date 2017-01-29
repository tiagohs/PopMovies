package br.com.tiagohs.popmovies.presenter;

import br.com.tiagohs.popmovies.view.MovieDetailsMidiaView;

public interface MovieDetailsMidiaPresenter  extends BasePresenter<MovieDetailsMidiaView> {

    void getVideos(int movieID, String tag, String language);
    void getImagens(int movieID, String tag);
}
