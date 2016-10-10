package br.com.tiagohs.popmovies.interceptor;

import com.android.volley.VolleyError;

import java.util.List;

import br.com.tiagohs.popmovies.model.media.Translation;
import br.com.tiagohs.popmovies.model.response.VideosResponse;

/**
 * Created by Tiago Henrique on 28/08/2016.
 */
public interface VideoInterceptor {

    interface onVideoListener {

        void onVideoRequestSucess(VideosResponse videosResponse);
        void onVideoRequestError(VolleyError error);
    }

    void getVideos(int movieID, List<Translation> translations);
}
