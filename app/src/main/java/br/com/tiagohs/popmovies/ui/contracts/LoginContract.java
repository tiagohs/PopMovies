package br.com.tiagohs.popmovies.ui.contracts;

import br.com.tiagohs.popmovies.model.db.ProfileDB;
import br.com.tiagohs.popmovies.ui.presenter.BasePresenter;
import io.reactivex.Observable;

public class LoginContract {

    public interface LoginInterceptor {

        Observable<Long> saveProfile(ProfileDB profile);
        Observable<ProfileDB> findProfileByUserUsername(String username);
    }

    public interface LoginPresenter extends BasePresenter<LoginView> {

        void onSaveProfile(String username, String email, String name, int typeLogin, String token, String pathFoto, int typePhoto);
    }

    public interface LoginView {

        boolean isInternetConnected();
        void onSaveInSharedPreferences(ProfileDB profileDB);
    }
}
