package br.com.tiagohs.popmovies.ui.contracts;

import java.util.List;

import br.com.tiagohs.popmovies.model.media.Translation;
import br.com.tiagohs.popmovies.model.media.Video;
import br.com.tiagohs.popmovies.ui.presenter.IPresenter;
import br.com.tiagohs.popmovies.ui.view.IView;
import io.reactivex.Observable;

public class VideosContract {

    public interface VideosPresenter extends IPresenter<VideosView> {

        void getVideos(int movieID, List<Translation> translation);
        void getVideosByPage();
    }

    public interface VideosView extends IView {

        void onUpdateUI(List<Video> videos, boolean hasMorePages);
    }

    public interface VideoInterceptor {

        Observable<List<Video>> getMovieVideos(int movieID, String language);
    }

}
