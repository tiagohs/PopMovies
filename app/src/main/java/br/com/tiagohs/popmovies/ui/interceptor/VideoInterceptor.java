package br.com.tiagohs.popmovies.ui.interceptor;

import java.util.List;

import javax.inject.Inject;

import br.com.tiagohs.popmovies.ui.contracts.VideosContract;
import br.com.tiagohs.popmovies.model.media.Video;
import br.com.tiagohs.popmovies.model.response.VideosResponse;
import br.com.tiagohs.popmovies.server.methods.MoviesMethod;
import io.reactivex.Observable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class VideoInterceptor implements VideosContract.VideoInterceptor {

    private MoviesMethod mMoviesMethod;

    @Inject
    public VideoInterceptor(MoviesMethod moviesMethod) {
        mMoviesMethod = moviesMethod;
    }

    @Override
    public Observable<List<Video>> getMovieVideos(int movieID, String language) {
        return mMoviesMethod.getMovieVideos(movieID, language)
                            .subscribeOn(Schedulers.io())
                            .map(new Function<VideosResponse, List<Video>>() {
                                @Override
                                public List<Video> apply(VideosResponse videosResponse) throws Exception {
                                    return videosResponse.getVideos();
                                }
                            });
    }
}
