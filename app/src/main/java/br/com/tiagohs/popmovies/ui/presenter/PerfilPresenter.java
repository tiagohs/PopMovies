package br.com.tiagohs.popmovies.ui.presenter;

import android.view.View;

import java.util.List;
import java.util.Random;

import br.com.tiagohs.popmovies.model.atwork.Artwork;
import br.com.tiagohs.popmovies.model.db.ProfileDB;
import br.com.tiagohs.popmovies.model.db.UserDB;
import br.com.tiagohs.popmovies.model.response.ImageResponse;
import br.com.tiagohs.popmovies.ui.contracts.PerfilContract;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class PerfilPresenter extends BasePresenter<PerfilContract.PerfilView, PerfilContract.PerfilInterceptor> implements PerfilContract.PerfilPresenter {
    private static final String TAG = PerfilPresenter.class.getSimpleName();

    public PerfilPresenter(PerfilContract.PerfilInterceptor perfilInterceptor, CompositeDisposable subscribers) {
        super(perfilInterceptor, subscribers);
    }

    public void initUpdates(String username) {

        try {
            mSubscribers.add(mInterceptor.findProfileByUserUsername(username)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<ProfileDB>() {
                        @Override
                        public void accept(ProfileDB profileDB) throws Exception {
                            onReponse(profileDB);
                        }
                    }));
        } catch (Exception ex) {}


    }

    private void onReponse(ProfileDB profile) {
        mView.setProfile(profile);

        getRandomBackground(profile);

        if (profile.getUser().getTypePhoto() == UserDB.PHOTO_ONLINE)
            mView.setImagePerfil(profile.getUser().getPicturePath());
        else if (profile.getUser().getTypePhoto() == UserDB.PHOTO_LOCAL)
            mView.setLocalImagePerfil();

        mView.setNamePerfil(profile.getUser().getNome());
        mView.setupTabs();
        mView.setProgressVisibility(View.GONE);
    }

    private void getRandomBackground(ProfileDB profile) {

        mInterceptor.getMovieImagens(profile.getProfileID())
                                     .observeOn(AndroidSchedulers.mainThread())
                                     .subscribe(onBackgroundObserver());

    }

    private Observer<ImageResponse> onBackgroundObserver() {
        return new Observer<ImageResponse>() {
            @Override
            public void onSubscribe(Disposable d) {
                mSubscribers.add(d);
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
            public void onError(Throwable e) {}

            @Override
            public void onComplete() {}
        };
    }

    private boolean hasImages(List<Artwork> backdrops, List<Artwork> posters) {
        return !backdrops.isEmpty() && !posters.isEmpty();
    }

    private void updatePerfilBackground(List<Artwork> images) {
        int index = new Random().nextInt(images.size());

        mView.setBackground(images.get(index).getFilePath());
    }

}
