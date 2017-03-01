package br.com.tiagohs.popmovies.ui.interceptor;

import javax.inject.Inject;

import br.com.tiagohs.popmovies.database.repository.ProfileRepository;
import br.com.tiagohs.popmovies.model.db.ProfileDB;
import br.com.tiagohs.popmovies.ui.contracts.LoginContract;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class LoginInterceptor implements LoginContract.LoginInterceptor {

    private ProfileRepository mProfileRepository;

    @Inject
    public LoginInterceptor(ProfileRepository profileRepository) {
        mProfileRepository = profileRepository;
    }

    @Override
    public Observable<Long> saveProfile(ProfileDB profile) {
        return mProfileRepository.saveProfile(profile)
                                 .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<ProfileDB> findProfileByUserUsername(String username) {
        return mProfileRepository.findProfileByUserUsername(username)
                                 .subscribeOn(Schedulers.io());
    }
}
