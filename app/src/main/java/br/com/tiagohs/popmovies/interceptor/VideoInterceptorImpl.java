package br.com.tiagohs.popmovies.interceptor;

import com.android.volley.VolleyError;

import java.util.List;

import br.com.tiagohs.popmovies.model.media.Translation;
import br.com.tiagohs.popmovies.model.response.VideosResponse;
import br.com.tiagohs.popmovies.server.ResponseListener;
import br.com.tiagohs.popmovies.server.methods.MoviesServer;

public class VideoInterceptorImpl implements ResponseListener<VideosResponse>, VideoInterceptor  {
    private MoviesServer mMoviesServer;
    private onVideoListener mListener;

    private VideosResponse mFinalVideosReponse;

    private List<Translation> mTranslations;

    private int mCurrentTranslation;
    private int mTotalTranslation;
    private int mMovieID;
    private String mTag;

    public VideoInterceptorImpl(onVideoListener listener) {
        mMoviesServer = new MoviesServer();
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

    private boolean containsTranslations() {
        return mCurrentTranslation < mTotalTranslation;
    }

    private boolean isFirstTranslation() {
        return mCurrentTranslation == 0;
    }

    @Override
    public void getVideos(int movieID, String tag, String language) {
        mMoviesServer.getMovieVideos(movieID, language, tag, this);
    }
}
