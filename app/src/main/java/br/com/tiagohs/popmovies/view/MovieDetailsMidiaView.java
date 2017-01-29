package br.com.tiagohs.popmovies.view;

import java.util.List;

import br.com.tiagohs.popmovies.model.media.Video;
import br.com.tiagohs.popmovies.model.response.ImageResponse;

/**
 * Created by Tiago Henrique on 28/08/2016.
 */
public interface MovieDetailsMidiaView extends BaseView {

    void updateImageUI(ImageResponse imageResponse);
    void updateVideoUI(List<Video> videosResponse);
    void setVideosSearched(boolean videosSearched);

    void onErrorGetImages();
    void onErrorGetVideos();
}
