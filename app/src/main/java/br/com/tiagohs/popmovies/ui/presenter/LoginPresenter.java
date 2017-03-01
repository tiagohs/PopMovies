package br.com.tiagohs.popmovies.ui.presenter;

import br.com.tiagohs.popmovies.model.db.ProfileDB;
import br.com.tiagohs.popmovies.model.db.UserDB;
import br.com.tiagohs.popmovies.ui.contracts.LoginContract;
import br.com.tiagohs.popmovies.util.LocaleUtils;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LoginPresenter implements LoginContract.LoginPresenter {

    private LoginContract.LoginView mLoginView;
    private LoginContract.LoginInterceptor mInterceptor;

    private CompositeDisposable mSubscribes;

    private ProfileDB mProfileDB;

    public LoginPresenter(LoginContract.LoginInterceptor interceptor, CompositeDisposable subscribes) {
        mInterceptor = interceptor;
        mSubscribes = subscribes;
    }

    @Override
    public void onBindView(LoginContract.LoginView view) {
        mLoginView = view;
    }

    @Override
    public void onUnbindView() {
        mSubscribes.clear();
        mLoginView = null;
    }

    @Override
    public void onSaveProfile(final String username, final String email, final String name, final int typeLogin, final String token, final String pathFoto, final int typePhoto) {

        mInterceptor.findProfileByUserUsername(username)
                    .observeOn(Schedulers.io())
                    .subscribe(new Observer<ProfileDB>() {
                        @Override
                        public void onSubscribe(Disposable disposable) {
                            mSubscribes.add(disposable);
                        }

                        @Override
                        public void onNext(ProfileDB profileDB) {
                            mProfileDB = profileDB;
                        }

                        @Override
                        public void onError(Throwable throwable) {}

                        @Override
                        public void onComplete() {
                            if (null == mProfileDB)
                                onNewProfile(username, email, name, typeLogin, token, pathFoto, typePhoto);
                            else
                                mLoginView.onSaveInSharedPreferences(mProfileDB);
                        }
                    });
    }

    private void onNewProfile(String username, String email, String name, int typeLogin, String token, String pathFoto, int typePhoto) {
        UserDB userDB = new UserDB(name, username, email, pathFoto, token, typePhoto, typeLogin, true);
        ProfileDB profileDB = new ProfileDB(userDB, LocaleUtils.getLocaleCountryName());

        mSubscribes.add(mInterceptor.saveProfile(profileDB)
                   .observeOn(AndroidSchedulers.mainThread())
                   .subscribe());

        mLoginView.onSaveInSharedPreferences(profileDB);
    }
}
