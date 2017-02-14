package br.com.tiagohs.popmovies.presenter;

import android.view.View;

import com.android.volley.VolleyError;

import br.com.tiagohs.popmovies.interceptor.ImagemInterceptor;
import br.com.tiagohs.popmovies.interceptor.ImagemInterceptorImpl;
import br.com.tiagohs.popmovies.interceptor.VideoInterceptor;
import br.com.tiagohs.popmovies.interceptor.VideoInterceptorImpl;
import br.com.tiagohs.popmovies.model.response.ImageResponse;
import br.com.tiagohs.popmovies.model.response.VideosResponse;
import br.com.tiagohs.popmovies.view.MovieDetailsMidiaView;

public class MovieDetailsMidiaPresenterImpl implements MovieDetailsMidiaPresenter,
                                                       ImagemInterceptor.onImagemListener,
                                                       VideoInterceptor.onVideoListener {
    private final String US_LOCALE = "en-US";

    private MovieDetailsMidiaView mView;
    private ImagemInterceptor mImagemInterceptor;
    private VideoInterceptor mVideoInterceptor;

    private VideosResponse mFinalVideosReponse;
    private int mMovieID;
    private String mTag;
    private boolean isLastSearch;

    public MovieDetailsMidiaPresenterImpl() {
        this.mImagemInterceptor = new ImagemInterceptorImpl(this);
        this.mVideoInterceptor = new VideoInterceptorImpl(this);
    }

    @Override
    public void getVideos(int movieID, String tag, String language) {
        mMovieID = movieID;
        mTag = tag;

        if (mView.isInternetConnected()) {
            mVideoInterceptor.getVideos(movieID, tag, language);
        } else
            noConnectionError();

    }

    private void noConnectionError() {
        if (mView.isAdded())
            mView.onErrorNoConnection();

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
    public void onImageRequestSucess(ImageResponse imageResponse) {
        if (mView.isAdded())
            mView.updateImageUI(imageResponse);
    }

    @Override
    public void onImageRequestError(VolleyError error) {
        mView.onErrorGetImages();
    }

    @Override
    public void onVideoRequestSucess(VideosResponse videosResponse) {

        if (mView.isAdded()) {

            if(!isLastSearch) {
                isLastSearch = true;
                mFinalVideosReponse = videosResponse;
                mVideoInterceptor.getVideos(mMovieID, mTag, US_LOCALE);
            } else {
                mFinalVideosReponse.getVideos().addAll(videosResponse.getVideos());
                mView.setVideosSearched(true);
                mView.updateVideoUI(mFinalVideosReponse.getVideos());
            }

        }
    }

    @Override
    public void onVideoRequestError(VolleyError error) {
        mView.onErrorGetVideos();
    }

    @Override
    public boolean isAdded() {
        return mView.isAdded();
    }

}
