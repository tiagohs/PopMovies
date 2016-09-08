package br.com.tiagohs.popmovies.presenter;

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
    private static final String TAG = MovieDetailsMidiaPresenterImpl.class.getSimpleName();

    private MovieDetailsMidiaView mView;
    private ImagemInterceptor mImagemInterceptor;
    private VideoInterceptor mVideoInterceptor;

    public MovieDetailsMidiaPresenterImpl() {
        this.mImagemInterceptor = new ImagemInterceptorImpl(this);
        this.mVideoInterceptor = new VideoInterceptorImpl(this);
    }

    @Override
    public void getVideos(int movieID) {
        mVideoInterceptor.getVideos(movieID);
    }

    @Override
    public void getImagens(int movieID) {
        mImagemInterceptor.getImagens(movieID);
    }

    @Override
    public void setView(MovieDetailsMidiaView view) {
        this.mView = view;
    }

    @Override
    public void onImageRequestSucess(ImageResponse imageResponse) {
        mView.updateImageUI(imageResponse);
    }

    @Override
    public void onImageRequestError(VolleyError error) {

    }

    @Override
    public void onVideoRequestSucess(VideosResponse videosResponse) {
        mView.updateVideoUI(videosResponse);
    }

    @Override
    public void onVideoRequestError(VolleyError error) {

    }

}
