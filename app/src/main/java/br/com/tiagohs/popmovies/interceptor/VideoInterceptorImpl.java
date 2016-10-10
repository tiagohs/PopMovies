package br.com.tiagohs.popmovies.interceptor;

import com.android.volley.VolleyError;

import java.util.List;

import br.com.tiagohs.popmovies.model.media.Translation;
import br.com.tiagohs.popmovies.model.response.VideosResponse;
import br.com.tiagohs.popmovies.server.PopMovieServer;
import br.com.tiagohs.popmovies.server.ResponseListener;

/**
 * Created by Tiago Henrique on 28/08/2016.
 */
public class VideoInterceptorImpl implements ResponseListener<VideosResponse>, VideoInterceptor  {
    private PopMovieServer mPopMovieServer;
    private onVideoListener mListener;

    private VideosResponse mFinalVideosReponse;

    private List<Translation> mTranslations;

    private int mCurrentTranslation;
    private int mTotalTranslation;
    private int mMovieID;

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

        if (isFirstTranslation())
            mFinalVideosReponse = response;

        if (containsTranslations()) {
            mFinalVideosReponse.getVideos().addAll(response.getVideos());
            mPopMovieServer.getMovieVideos(mMovieID, mTranslations.get(++mCurrentTranslation).getLanguage() + "-" + mTranslations.get(0).getCountry(), this);
        } else {
            mListener.onVideoRequestSucess(mFinalVideosReponse);
        }

    }

    private boolean containsTranslations() {
        return mCurrentTranslation < mTotalTranslation;
    }

    private boolean isFirstTranslation() {
        return mCurrentTranslation == 0;
    }

    @Override
    public void getVideos(int movieID, List<Translation> translations) {
        mTranslations = translations;
        mTotalTranslation = translations.size() - 1;
        mMovieID = movieID;

        mPopMovieServer.getMovieVideos(mMovieID, mTranslations.get(mCurrentTranslation).getLanguage() + "-" + mTranslations.get(0).getCountry(), this);
    }
}
