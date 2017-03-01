package br.com.tiagohs.popmovies.ui.contracts;

import java.util.List;

import br.com.tiagohs.popmovies.model.media.Video;
import br.com.tiagohs.popmovies.model.response.ImageResponse;
import br.com.tiagohs.popmovies.ui.presenter.IPresenter;
import br.com.tiagohs.popmovies.ui.view.IView;
import io.reactivex.Observable;

public class MovieDetailsMidiaContract {

    public interface MovieDetailsMidiaInterceptor {

        Observable<ImageResponse> getMovieImagens(int movieID);
        Observable<List<Video>> getMovieVideos(int movieID, String language);

    }

    public interface MovieDetailsMidiaPresenter  extends IPresenter<MovieDetailsMidiaView> {

        void getVideos(int movieID, String language);
        void getImagens(int movieID);
    }

    public interface MovieDetailsMidiaView extends IView {

        void updateImageUI(ImageResponse imageResponse);
        void updateVideoUI(List<Video> videosResponse);
        void setVideosSearched(boolean videosSearched);

        void onErrorGetImages();
        void onErrorGetVideos();

        void setImagesProgressVisibility(int visibityState);
        void setVideosProgressVisibility(int visibityState);
    }
}
