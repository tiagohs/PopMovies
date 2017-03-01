package br.com.tiagohs.popmovies.ui.presenter;

import android.os.Handler;
import android.view.View;

import java.util.List;

import javax.inject.Inject;

import br.com.tiagohs.popmovies.model.db.ProfileDB;
import br.com.tiagohs.popmovies.model.dto.GenrerMoviesDTO;
import br.com.tiagohs.popmovies.ui.contracts.PerfilEstatisticasContract;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class PerfilEstatisticasPresenter extends BasePresenter<PerfilEstatisticasContract.PerfilEstatisticasView, PerfilEstatisticasContract.PerfilEstatisticasInterceptor> implements PerfilEstatisticasContract.PerfilEstatisticasPresenter {
    private static final String TAG = PerfilEstatisticasPresenter.class.getSimpleName();

    private static final int DELAY = 2000;

    @Inject
    public PerfilEstatisticasPresenter(PerfilEstatisticasContract.PerfilEstatisticasInterceptor interceptor, CompositeDisposable subscribers) {
        super(interceptor, subscribers);
    }

    public void initUpdates(String username) {

        mInterceptor.findProfileByUserUsername(username)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<ProfileDB>() {
                        @Override
                        public void accept(ProfileDB profileDB) throws Exception {
                            onProfileReceived(profileDB);
                        }
                    });

    }

    private void onProfileReceived(ProfileDB profile) {
        long profileID = profile.getProfileID();

        mView.setDadosPessoais(profile.getCountry(), profile.getBirthday(), profile.getGenrer());
        mView.setDescricao(profile.getDescricao());

        try {
            getAllGenrersSaved(profileID);
            getTotalHoursWatched(profileID);
            getTotalMovies(profileID);
            getTotalMoviesWatched(profileID);
            getTotalFavorites(profileID);
            getTotalMoviesDontWantSee(profileID);
            getTotalMoviesWantSee(profileID);
        } catch (Exception ex) {

        }

        new Handler().postDelayed(new Runnable() {
            public void run() {
                if (mView.isAdded()) {
                    mView.setProgressVisibility(View.GONE);
                    mView.setContainerPrincipalVisibility(View.VISIBLE);
                }
            }
        }, DELAY);

    }

    private void getAllGenrersSaved(long profileID) {
        mSubscribers.add(mInterceptor.getAllGenrersSaved(profileID)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<GenrerMoviesDTO>>() {
                    @Override
                    public void accept(List<GenrerMoviesDTO> genrers) {
                        mView.configurateGraphic(genrers);
                    }
                }));
    }

    private void getTotalHoursWatched(long profileID) {
        mSubscribers.add(mInterceptor.getTotalHoursWatched(profileID)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Long>() {
                        @Override
                        public void accept(Long aLong) {
                            mView.setTotalHorasAssistidas(aLong);
                        }
                    }));
    }

    private void getTotalMovies(long profileID) {
        mSubscribers.add(mInterceptor.getTotalMovies(profileID)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) {
                        mView.setTotalFilmesAssistidos(aLong.intValue());
                    }
                }));
    }

    private void getTotalMoviesWatched(long profileID) {
        mSubscribers.add(mInterceptor.getTotalMoviesWatched(profileID)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        mView.setTotalMoviesWatched(aLong);
                    }
                }));
    }

    private void getTotalFavorites(long profileID) {
        mSubscribers.add(mInterceptor.getTotalFavorites(profileID)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) {
                        mView.setTotalMoviesFavorite(aLong);

                    }
                }));
    }

    private void getTotalMoviesWantSee(long profileID) {
        mSubscribers.add(mInterceptor.getTotalMoviesWantSee(profileID)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) {
                        mView.setTotalMoviesWanSee(aLong);
                    }
                }));
    }

    private void getTotalMoviesDontWantSee(long profileID) {
        mSubscribers.add(mInterceptor.getTotalMoviesDontWantSee(profileID)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) {
                        mView.setTotalMoviesDontWanSee(aLong);
                    }
                }));
    }

}
