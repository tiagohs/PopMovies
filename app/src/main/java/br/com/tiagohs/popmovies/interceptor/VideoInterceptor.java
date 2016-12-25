package br.com.tiagohs.popmovies.interceptor;

import com.android.volley.VolleyError;

import java.util.List;

import br.com.tiagohs.popmovies.model.media.Translation;
import br.com.tiagohs.popmovies.model.response.VideosResponse;

public interface VideoInterceptor {

    interface onVideoListener {

        void onVideoRequestSucess(VideosResponse videosResponse);
        void onVideoRequestError(VolleyError error);
        boolean isAdded();
    }

    void getVideos(int movieID, String tag, String language);
}
