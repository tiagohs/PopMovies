package br.com.tiagohs.popmovies.ui.interceptor;

import java.util.List;

import javax.inject.Inject;

import br.com.tiagohs.popmovies.database.repository.MovieRepository;
import br.com.tiagohs.popmovies.model.db.MovieDB;
import br.com.tiagohs.popmovies.ui.contracts.PerfilContract;
import br.com.tiagohs.popmovies.database.repository.ProfileRepository;
import br.com.tiagohs.popmovies.model.db.ProfileDB;
import br.com.tiagohs.popmovies.model.response.ImageResponse;
import br.com.tiagohs.popmovies.server.methods.MoviesMethod;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class PerfilInterceptor implements PerfilContract.PerfilInterceptor {

    private MoviesMethod mMoviesMethod;
    private ProfileRepository mProfileRepository;
    private MovieRepository mMovieRepository;

    @Inject
    public PerfilInterceptor(MoviesMethod moviesMethod, ProfileRepository profileRepository, MovieRepository movieRepository) {
        mMoviesMethod = moviesMethod;
        mProfileRepository = profileRepository;
        mMovieRepository = movieRepository;
    }


    @Override
    public Observable<ProfileDB> findProfileByUserUsername(String username) {
        return mProfileRepository.findProfileByUserUsername(username)
                                 .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<List<MovieDB>> findAllMoviesDB(long profileID) {
        return mMovieRepository.findAllMoviesDB(profileID)
                               .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<ImageResponse> getMovieImagens(int movieID) {
        return mMoviesMethod.getMovieImagens(movieID)
                            .subscribeOn(Schedulers.io());
    }
}
