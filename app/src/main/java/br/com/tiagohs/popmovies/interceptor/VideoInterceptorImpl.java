package br.com.tiagohs.popmovies.interceptor;

import com.android.volley.VolleyError;

import br.com.tiagohs.popmovies.model.response.VideosResponse;
import br.com.tiagohs.popmovies.server.PopMovieServer;
import br.com.tiagohs.popmovies.server.ResponseListener;

/**
 * Created by Tiago Henrique on 28/08/2016.
 */
public class VideoInterceptorImpl implements ResponseListener<VideosResponse>, VideoInterceptor  {
    private PopMovieServer mPopMovieServer;
    private onVideoListener mListener;

    public VideoInterceptorImpl(onVideoListener listener) {
        mPopMovieServer = PopMovieServer.getInstance();
        this.mListener = listener;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        mListener.onVideoRequestError(error);
    }

    @Override
    public void onResponse(VideosResponse response) {
        mListener.onVideoRequestSucess(response);
    }

    @Override
    public void getVideos(int movieID) {
        mPopMovieServer.getMovieVideos(movieID, this);
    }
}
