package br.com.tiagohs.popmovies.presenter;

import android.app.Activity;
import android.view.View;

import com.android.volley.VolleyError;

import java.util.List;

import br.com.tiagohs.popmovies.App;
import br.com.tiagohs.popmovies.interceptor.ImagemInterceptor;
import br.com.tiagohs.popmovies.interceptor.ImagemInterceptorImpl;
import br.com.tiagohs.popmovies.interceptor.VideoInterceptor;
import br.com.tiagohs.popmovies.interceptor.VideoInterceptorImpl;
import br.com.tiagohs.popmovies.model.media.Translation;
import br.com.tiagohs.popmovies.model.response.ImageResponse;
import br.com.tiagohs.popmovies.model.response.VideosResponse;
import br.com.tiagohs.popmovies.view.MovieDetailsMidiaView;

public class MovieDetailsMidiaPresenterImpl implements MovieDetailsMidiaPresenter,
                                                       ImagemInterceptor.onImagemListener,
                                                       VideoInterceptor.onVideoListener {

    private MovieDetailsMidiaView mView;
    private ImagemInterceptor mImagemInterceptor;
    private VideoInterceptor mVideoInterceptor;

    public MovieDetailsMidiaPresenterImpl() {
        this.mImagemInterceptor = new ImagemInterceptorImpl(this);
        this.mVideoInterceptor = new VideoInterceptorImpl(this);
    }

    @Override
    public void getVideos(int movieID, String tag, List<Translation> translations) {

        if (mView.isInternetConnected()) {
            mVideoInterceptor.getVideos(movieID, tag, translations);
        } else
            noConnectionError();

    }

    private void noConnectionError() {
        if (mView.isAdded())
            mView.onError("Sem Conexão");

        mView.setProgressVisibility(View.GONE);
    }

    @Override
    public void getImagens(int movieID, String tag) {
        if (mView.isInternetConnected())
            mImagemInterceptor.getImagens(movieID, tag);
        else
            noConnectionError();

    }

    @Override
    public void setView(MovieDetailsMidiaView view) {
        this.mView = view;
    }

    @Override
    public void onCancellRequest(Activity activity, String tag) {
        ((App) activity.getApplication()).cancelAll(tag);
    }

    @Override
    public void onImageRequestSucess(ImageResponse imageResponse) {
        if (mView.isAdded())
            mView.updateImageUI(imageResponse);
    }

    @Override
    public void onImageRequestError(VolleyError error) {
        mView.onError("Videos: Erro ao carregar as imagens, tente novamente.");
    }

    @Override
    public void onVideoRequestSucess(VideosResponse videosResponse) {
        if (mView.isAdded()) {
            mView.setVideosSearched(true);
            mView.updateVideoUI(videosResponse.getVideos());
        }
    }

    @Override
    public void onVideoRequestError(VolleyError error) {
        mView.onError("Videos: Erro ao carregar os vídeos, tente novamente.");
    }

    @Override
    public boolean isAdded() {
        return mView.isAdded();
    }

}
