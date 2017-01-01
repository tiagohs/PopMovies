package br.com.tiagohs.popmovies.presenter;

import android.app.Activity;
import android.view.View;

import com.android.volley.VolleyError;

import java.util.List;

import br.com.tiagohs.popmovies.App;
import br.com.tiagohs.popmovies.interceptor.VideoInterceptor;
import br.com.tiagohs.popmovies.interceptor.VideoInterceptorImpl;
import br.com.tiagohs.popmovies.model.media.Translation;
import br.com.tiagohs.popmovies.model.response.VideosResponse;
import br.com.tiagohs.popmovies.view.VideosView;

public class VideosPresenterImpl implements VideosPresenter, VideoInterceptor.onVideoListener {

    private VideosView mVideosView;
    private VideoInterceptor mVideoInterceptor;

    private VideosResponse mFinalVideosReponse;

    private List<Translation> mTranslations;

    private int mCurrentTranslation;
    private int mTotalTranslation;
    private int mMovieID;
    private String mTag;

    public VideosPresenterImpl() {
        mVideoInterceptor = new VideoInterceptorImpl(this);
    }

    @Override
    public void getVideos(int movieID, List<Translation> translation, String tag) {
        mTranslations = translation;
        mTotalTranslation = translation.size() - 1;
        mMovieID = movieID;
        mTag = tag;

        mVideosView.setProgressVisibility(View.VISIBLE);

        if (mVideosView.isInternetConnected()) {
            mVideoInterceptor.getVideos(mMovieID, tag, mTranslations.get(mCurrentTranslation).getLanguage() + "-" + mTranslations.get(0).getCountry());
        } else {
            noConnectionError();
        }

    }

    private void noConnectionError() {
        if (mVideosView.isAdded()) {
            mVideosView.onError("Sem Conexao");
            mVideosView.setProgressVisibility(View.GONE);
        }
    }

    @Override
    public void setView(VideosView view) {
        mVideosView = view;
    }

    @Override
    public void onCancellRequest(Activity activity, String tag) {
        ((App) activity.getApplication()).cancelAll(tag);
    }

    @Override
    public void onVideoRequestSucess(VideosResponse response) {

        if (mVideosView.isAdded()) {
            if (isFirstTranslation())
                mFinalVideosReponse = response;

            if (containsTranslations()) {
                mFinalVideosReponse.getVideos().addAll(response.getVideos());
                mVideoInterceptor.getVideos(mMovieID, mTag, mTranslations.get(++mCurrentTranslation).getLanguage() + "-" + mTranslations.get(0).getCountry());
            } else {
                mVideosView.onUpdateUI(mFinalVideosReponse);
            }
        }

        mVideosView.setProgressVisibility(View.GONE);
    }

    private boolean containsTranslations() {
        return mCurrentTranslation < mTotalTranslation;
    }

    private boolean isFirstTranslation() {
        return mCurrentTranslation == 0;
    }

    @Override
    public void onVideoRequestError(VolleyError error) {

    }

    @Override
    public boolean isAdded() {
        return mVideosView.isAdded();
    }
}
