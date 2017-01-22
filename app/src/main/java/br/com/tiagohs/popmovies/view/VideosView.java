package br.com.tiagohs.popmovies.view;

import java.util.List;

import br.com.tiagohs.popmovies.model.media.Video;
import br.com.tiagohs.popmovies.model.response.VideosResponse;

/**
 * Created by Tiago on 30/12/2016.
 */

public interface VideosView extends BaseView {

    void onUpdateUI(List<Video> videos, boolean hasMorePages);
}
