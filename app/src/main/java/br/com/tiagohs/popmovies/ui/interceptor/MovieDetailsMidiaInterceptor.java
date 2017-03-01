package br.com.tiagohs.popmovies.ui.interceptor;

import java.util.List;

import javax.inject.Inject;

import br.com.tiagohs.popmovies.ui.contracts.MovieDetailsMidiaContract;
import br.com.tiagohs.popmovies.model.media.Video;
import br.com.tiagohs.popmovies.model.response.ImageResponse;
import br.com.tiagohs.popmovies.model.response.VideosResponse;
import br.com.tiagohs.popmovies.server.methods.MoviesMethod;
import io.reactivex.Observable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class MovieDetailsMidiaInterceptor implements MovieDetailsMidiaContract.MovieDetailsMidiaInterceptor {

    private MoviesMethod mMoviesMethod;

    @Inject
    public MovieDetailsMidiaInterceptor(MoviesMethod moviesMethod) {
        mMoviesMethod = moviesMethod;
    }

    @Override
    public Observable<ImageResponse> getMovieImagens(int movieID) {
        return mMoviesMethod.getMovieImagens(movieID)
                            .subscribeOn(Schedulers.io());
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
