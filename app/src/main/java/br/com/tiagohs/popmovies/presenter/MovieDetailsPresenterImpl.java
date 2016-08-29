package br.com.tiagohs.popmovies.presenter;

import com.android.volley.VolleyError;

import br.com.tiagohs.popmovies.interceptor.VideoInterceptor;
import br.com.tiagohs.popmovies.interceptor.VideoInterceptorImpl;
import br.com.tiagohs.popmovies.model.Movie.MovieDetails;
import br.com.tiagohs.popmovies.model.response.VideosResponse;
import br.com.tiagohs.popmovies.server.PopMovieServer;
import br.com.tiagohs.popmovies.view.MovieDetailsView;

/**
 * Created by Tiago Henrique on 25/08/2016.
 */
public class MovieDetailsPresenterImpl implements MovieDetailsPresenter, VideoInterceptor.onVideoListener {
    private MovieDetailsView mMovieDetailsView;

    private PopMovieServer mPopMovieServer;
    private VideoInterceptor mVideoInterceptor;

    public MovieDetailsPresenterImpl() {
        mPopMovieServer = PopMovieServer.getInstance();
        mVideoInterceptor = new VideoInterceptorImpl(this);
    }

    @Override
    public void setView(MovieDetailsView view) {
        this.mMovieDetailsView = view;
    }

    @Override
    public void getMovieDetails(int movieID, String[] appendToResponse) {
        mPopMovieServer.getMovieDetails(movieID, appendToResponse, this);
    }

    @Override
    public void getVideos(int movieID) {
        mVideoInterceptor.getVideos(movieID);
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(MovieDetails response) {
        mMovieDetailsView.updateUI(response);
    }

    @Override
    public void onVideoRequestSucess(VideosResponse videosResponse) {
        mMovieDetailsView.updateVideos(videosResponse);
    }

    @Override
    public void onVideoRequestError(VolleyError error) {

    }
}
