package br.com.tiagohs.popmovies.presenter;

import java.util.List;

import br.com.tiagohs.popmovies.model.media.Translation;
import br.com.tiagohs.popmovies.view.MovieDetailsMidiaView;

public interface MovieDetailsMidiaPresenter  extends BasePresenter<MovieDetailsMidiaView> {

    void getVideos(int movieID, String tag, List<Translation> translations);
    void getImagens(int movieID, String tag);
}
