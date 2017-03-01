package br.com.tiagohs.popmovies.ui.presenter;

import android.util.Log;
import android.view.View;

import java.util.List;
import java.util.Random;

import br.com.tiagohs.popmovies.model.atwork.Artwork;
import br.com.tiagohs.popmovies.model.db.MovieDB;
import br.com.tiagohs.popmovies.model.db.ProfileDB;
import br.com.tiagohs.popmovies.model.db.UserDB;
import br.com.tiagohs.popmovies.model.response.ImageResponse;
import br.com.tiagohs.popmovies.ui.contracts.PerfilContract;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class PerfilPresenter implements PerfilContract.PerfilPresenter {
    private static final String TAG = PerfilPresenter.class.getSimpleName();

    private PerfilContract.PerfilView mPerfilView;
    private PerfilContract.PerfilInterceptor mInterceptor;

    private CompositeDisposable mSubscribers;

    public PerfilPresenter(PerfilContract.PerfilInterceptor perfilInterceptor, CompositeDisposable subscribers) {
        mInterceptor = perfilInterceptor;
        mSubscribers = subscribers;
    }


    @Override
    public void onBindView(PerfilContract.PerfilView view) {
        mPerfilView = view;
    }

    @Override
    public void onUnbindView() {
        mSubscribers.clear();
        mPerfilView = null;
    }


    public void initUpdates(String username) {

        mSubscribers.add(mInterceptor.findProfileByUserUsername(username)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<ProfileDB>() {
                        @Override
                        public void accept(ProfileDB profileDB) throws Exception {
                            onReponse(profileDB);
                        }
                    }));
    }

    private void onReponse(ProfileDB profile) {
        mPerfilView.setProfile(profile);

        getRandomBackground(profile);

        Log.i(TAG, "P " + profile.getUser().getTypePhoto());

        if (profile.getUser().getTypePhoto() == UserDB.PHOTO_ONLINE)
            mPerfilView.setImagePerfil(profile.getUser().getPicturePath());
        else if (profile.getUser().getTypePhoto() == UserDB.PHOTO_LOCAL)
            mPerfilView.setLocalImagePerfil();

        mPerfilView.setNamePerfil(profile.getUser().getNome());
        mPerfilView.setupTabs();
        mPerfilView.setProgressVisibility(View.GONE);
    }

    private void getRandomBackground(ProfileDB profile) {

        mInterceptor.findAllMoviesDB(profile.getProfileID())
                    .flatMap(new Function<List<MovieDB>, ObservableSource<ImageResponse>>() {
                                @Override
                                public ObservableSource<ImageResponse> apply(List<MovieDB> movieDBs) throws Exception {

                                    if (!movieDBs.isEmpty()) {
                                        int index = new Random().nextInt(movieDBs.size());
                                        return mInterceptor.getMovieImagens(movieDBs.get(index).getIdServer());
                                    }

                                    return mInterceptor.getMovieImagens(0);

                                }
                            })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(onBackgroundObserver());

    }

    private Observer<ImageResponse> onBackgroundObserver() {
        return new Observer<ImageResponse>() {
            @Override
            public void onSubscribe(Disposable disposable) {
                mSubscribers.add(disposable);
            }

            @Override
            public void onNext(ImageResponse images) {
                if (hasImages(images.getBackdrops(), images.getPosters())) {
                    if (!images.getBackdrops().isEmpty())
                        updatePerfilBackground(images.getBackdrops());
                    else
                        updatePerfilBackground(images.getPosters());
                }
            }

            @Override
            public void onError(Throwable throwable) {}

            @Override
            public void onComplete() {}
        };
    }

    private boolean hasImages(List<Artwork> backdrops, List<Artwork> posters) {
        return !backdrops.isEmpty() && !posters.isEmpty();
    }

    private void updatePerfilBackground(List<Artwork> images) {
        int index = new Random().nextInt(images.size());

        mPerfilView.setBackground(images.get(index).getFilePath());
    }

}
