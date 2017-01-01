package br.com.tiagohs.popmovies.view;

import br.com.tiagohs.popmovies.model.response.VideosResponse;

/**
 * Created by Tiago on 30/12/2016.
 */

public interface VideosView extends BaseView {

    void onUpdateUI(VideosResponse videos);
}
