package br.com.tiagohs.popmovies.presenter;

import java.util.List;

import br.com.tiagohs.popmovies.model.media.Translation;
import br.com.tiagohs.popmovies.view.VideosView;

public interface VideosPresenter extends BasePresenter<VideosView> {

    void getVideos(int movieID, List<Translation> translation, String tag);
}
