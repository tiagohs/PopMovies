package br.com.tiagohs.popmovies.ui.interceptor;

import javax.inject.Inject;

import br.com.tiagohs.popmovies.database.repository.ProfileRepository;
import br.com.tiagohs.popmovies.ui.contracts.PerfilFilmesContract;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class PerfilFilmesInterceptor implements PerfilFilmesContract.PerfilFilmesInterceptor {

    private ProfileRepository mProfileRepository;

    @Inject
    public PerfilFilmesInterceptor(ProfileRepository profileRepository) {
        mProfileRepository = profileRepository;
    }

    @Override
    public Observable<Boolean> hasMoviesWatched(long profileID) {
        return mProfileRepository.hasMoviesWatched(profileID)
                                 .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<Boolean> hasMoviesWantSee(long profileID) {
        return mProfileRepository.hasMoviesWantSee(profileID)
                                 .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<Boolean> hasMoviesDontWantSee(long profileID) {
        return mProfileRepository.hasMoviesDontWantSee(profileID)
                                 .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<Boolean> hasMoviesFavorite(long profileID) {
        return mProfileRepository.hasMoviesFavorite(profileID)
                                 .subscribeOn(Schedulers.io());
    }
}
