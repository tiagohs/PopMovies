package br.com.tiagohs.popmovies.ui.interceptor;

import java.util.List;

import javax.inject.Inject;

import br.com.tiagohs.popmovies.ui.contracts.PerfilEstatisticasContract;
import br.com.tiagohs.popmovies.database.repository.ProfileRepository;
import br.com.tiagohs.popmovies.model.db.ProfileDB;
import br.com.tiagohs.popmovies.model.dto.GenrerMoviesDTO;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class PerfilEstatisticasInterceptor implements PerfilEstatisticasContract.PerfilEstatisticasInterceptor {

    private ProfileRepository mProfileRepository;

    @Inject
    public PerfilEstatisticasInterceptor(ProfileRepository profileRepository) {
        mProfileRepository = profileRepository;
    }

    @Override
    public Observable<ProfileDB> findProfileByUserUsername(String username) {
        return mProfileRepository.findProfileByUserUsername(username)
                                 .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<Long> getTotalHoursWatched(long profileID) {
        return mProfileRepository.getTotalHoursWatched(profileID)
                                 .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<Long> getTotalMovies(long profileID) {
        return mProfileRepository.getTotalMovies(profileID)
                                 .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<Long> getTotalMoviesWatched(long profileID) {
        return mProfileRepository.getTotalMoviesWatched(profileID)
                                 .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<Long> getTotalMoviesWantSee(long profileID) {
        return mProfileRepository.getTotalMoviesWantSee(profileID)
                                 .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<Long> getTotalMoviesDontWantSee(long profileID) {
        return mProfileRepository.getTotalMoviesDontWantSee(profileID)
                                 .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<Long> getTotalFavorites(long profileID) {
        return mProfileRepository.getTotalFavorites(profileID)
                                 .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<List<GenrerMoviesDTO>> getAllGenrersSaved(long profileID) {
        return mProfileRepository.getAllGenrersSaved(profileID)
                                 .subscribeOn(Schedulers.io());
    }
}
