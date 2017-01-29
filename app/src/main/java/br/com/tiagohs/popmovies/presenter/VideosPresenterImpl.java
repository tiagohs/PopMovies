package br.com.tiagohs.popmovies.presenter;

import android.app.Activity;
import android.view.View;

import com.android.volley.VolleyError;

import org.apache.commons.collections4.ListUtils;

import java.util.List;

import br.com.tiagohs.popmovies.App;
import br.com.tiagohs.popmovies.R;
import br.com.tiagohs.popmovies.interceptor.VideoInterceptor;
import br.com.tiagohs.popmovies.interceptor.VideoInterceptorImpl;
import br.com.tiagohs.popmovies.model.media.Translation;
import br.com.tiagohs.popmovies.model.media.Video;
import br.com.tiagohs.popmovies.model.response.VideosResponse;
import br.com.tiagohs.popmovies.view.VideosView;

public class VideosPresenterImpl implements VideosPresenter, VideoInterceptor.onVideoListener {
    private static final String TAG = VideosPresenterImpl.class.getSimpleName();

    private static final String LOCALE_US = "en-US";
    private static final int NUM_MAX_VIDEOS_BY_PAGE = 12;

    private VideosView mVideosView;
    private VideoInterceptor mVideoInterceptor;

    private VideosResponse mFinalVideosReponse;

    private List<Translation> mTranslations;

    private int mCurrentTranslation;
    private int mTotalTranslation;

    private int mMovieID;
    private String mTag;

    private List<List<Video>> mVideos;

    private int mCurrentPage;
    private int mTotalPages;

    public VideosPresenterImpl() {
        mCurrentPage = 0;
        mVideoInterceptor = new VideoInterceptorImpl(this);
    }

    @Override
    public void getVideos(int movieID, List<Translation> translation, String tag) {
        mTranslations = translation;
        mTotalTranslation = translation.size() - 1;
        mMovieID = movieID;
        mTag = tag;

        if (mVideosView.isInternetConnected()) {
            if (!translation.isEmpty())
                mVideoInterceptor.getVideos(mMovieID, tag, mTranslations.get(mCurrentTranslation).getLanguage() + "-" + mTranslations.get(0).getCountry());
            else
                mVideoInterceptor.getVideos(mMovieID, tag, LOCALE_US);
        } else {
            noConnectionError();
        }

    }

    private void noConnectionError() {
        if (mVideosView.isAdded()) {
            mVideosView.onErrorNoConnection();
        }
    }

    @Override
    public void setView(VideosView view) {
        mVideosView = view;
    }

    @Override
    public void onVideoRequestSucess(VideosResponse response) {

        if (mVideosView.isAdded()) {
            if (isFirstTranslation()) {
                mVideosView.setProgressVisibility(View.VISIBLE);
                mFinalVideosReponse = response;
            }

            if (containsTranslations()) {
                mFinalVideosReponse.getVideos().addAll(response.getVideos());
                mVideoInterceptor.getVideos(mMovieID, mTag, mTranslations.get(++mCurrentTranslation).getLanguage() + "-" + mTranslations.get(0).getCountry());
                return;
            } else {
                mVideos = ListUtils.partition(mFinalVideosReponse.getVideos(), NUM_MAX_VIDEOS_BY_PAGE);
                mTotalPages = mVideos.size();

                mVideosView.onUpdateUI(mVideos.get(mCurrentPage++), mCurrentPage < mTotalPages);
            }
        }

    }

    public void getVideosByPage() {
        mVideosView.onUpdateUI(mVideos.get(mCurrentPage++), mCurrentPage < mTotalPages);
    }

    private boolean containsTranslations() {
        return mCurrentTranslation < mTotalTranslation;
    }

    private boolean isFirstTranslation() {
        return mCurrentTranslation == 0;
    }

    @Override
    public void onVideoRequestError(VolleyError error) {
        if (mVideosView.isAdded())
            mVideosView.onErrorInServer();
    }

    @Override
    public boolean isAdded() {
        return mVideosView.isAdded();
    }
}
