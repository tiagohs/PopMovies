package br.com.tiagohs.popmovies.interceptor;

import com.android.volley.VolleyError;

import br.com.tiagohs.popmovies.model.response.ImageResponse;
import br.com.tiagohs.popmovies.server.PopMovieServer;
import br.com.tiagohs.popmovies.server.ResponseListener;

/**
 * Created by Tiago Henrique on 28/08/2016.
 */
public class ImagemInterceptorImpl implements ResponseListener<ImageResponse>, ImagemInterceptor {
    private PopMovieServer mPopMovieServer;
    private onImagemListener mListener;

    public ImagemInterceptorImpl(onImagemListener listener) {
        this.mPopMovieServer = PopMovieServer.getInstance();
        this.mListener = listener;
    }

    public void getVideos(int movieID) {
        mPopMovieServer.getMovieImagens(movieID, this);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        mListener.onImageRequestError(error);
    }

    @Override
    public void onResponse(ImageResponse response) {
        mListener.onImageRequestSucess(response);
    }

    @Override
    public void getImagens(int movieID) {
        mPopMovieServer.getMovieImagens(movieID, this);
    }
}
