package br.com.tiagohs.popmovies.view;

import br.com.tiagohs.popmovies.model.response.ImageResponse;
import br.com.tiagohs.popmovies.model.response.VideosResponse;

/**
 * Created by Tiago Henrique on 28/08/2016.
 */
public interface MovieDetailsMidiaView extends BaseView {

    void updateImageUI(ImageResponse imageResponse);
    void updateVideoUI(VideosResponse videosResponse);

}
