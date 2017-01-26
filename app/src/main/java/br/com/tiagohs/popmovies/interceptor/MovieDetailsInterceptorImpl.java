package br.com.tiagohs.popmovies.interceptor;

import android.util.Log;

import com.android.volley.VolleyError;

import br.com.tiagohs.popmovies.model.movie.MovieDetails;
import br.com.tiagohs.popmovies.model.response.ImageResponse;
import br.com.tiagohs.popmovies.server.ResponseListener;
import br.com.tiagohs.popmovies.server.methods.MoviesServer;
import br.com.tiagohs.popmovies.util.MovieUtils;
import br.com.tiagohs.popmovies.util.ViewUtils;

/**
 * Created by Tiago on 29/12/2016.
 */

public class MovieDetailsInterceptorImpl implements MovieDetailsInterceptor, ResponseListener<MovieDetails> {
    private static final String TAG = MovieDetailsInterceptorImpl.class.getSimpleName();

    private String mOriginalLanguage;

    private MovieDetails mMovieDetails;
    private int mMovieID;
    private String mTag;
    private MoviesServer mMoviesServer;
    private MovieDetailsInterceptor.onMovieDetailsListener mListener;
    private String[] mMovieParameters;

    public MovieDetailsInterceptorImpl(MovieDetailsInterceptor.onMovieDetailsListener listener) {
        mListener = listener;
        mMoviesServer = new MoviesServer();
    }

    @Override
    public void getMovieDetails(int movieID, String[] parameters, String tag) {
        mMovieID = movieID;
        mTag = tag;
        mMovieParameters = parameters;

        mMoviesServer.getMovieDetails(movieID, mMovieParameters, tag, this);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        mListener.onMovieDetailsRequestError(error);
    }

    @Override
    public void onResponse(MovieDetails response) {

        if (mOriginalLanguage == null)
            mMovieDetails = response;

        if ((ViewUtils.isEmptyValue(response.getOverview()) || response.getRuntime() == 0) && mOriginalLanguage == null) {

            mOriginalLanguage = response.getOriginalLanguage();
            mMoviesServer.getMovieDetails(mMovieID, mMovieParameters, mTag, mOriginalLanguage, this);

        } else {

            if (mOriginalLanguage != null) {
                if (ViewUtils.isEmptyValue(mMovieDetails.getOverview())) {
                    mMovieDetails.setOverview(response.getOverview());
                }
                if (mMovieDetails.getRuntime() == 0)
                    mMovieDetails.setRuntime(response.getRuntime());

                mListener.onMovieDetailsRequestSucess(mMovieDetails);
                mOriginalLanguage = null;
            } else {
                mListener.onMovieDetailsRequestSucess(response);
                mOriginalLanguage = null;
            }

        }
    }
}
