package br.com.tiagohs.popmovies.presenter;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import br.com.tiagohs.popmovies.data.repository.ProfileRepository;
import br.com.tiagohs.popmovies.data.repository.ProfileRepositoryImpl;
import br.com.tiagohs.popmovies.model.db.ProfileDB;
import br.com.tiagohs.popmovies.util.PrefsUtils;
import br.com.tiagohs.popmovies.view.PerfilEstatisticasView;

public class PerfilEstatisticasPresenterImpl implements PerfilEstatisticasPresenter {
    private static final String TAG = PerfilEstatisticasPresenterImpl.class.getSimpleName();

    private PerfilEstatisticasView mPerfilEstatisticasView;
    private ProfileRepository mProfileRepository;
    private ProfileDB mProfileDB;

    public String mUsername;

    public void setProfileRepository(ProfileRepository profileRepository) {
        this.mProfileRepository = profileRepository;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

    public void initUpdates() {
        mProfileDB = mProfileRepository.findProfileByUserUsername(mUsername);
        long profileID = mProfileDB.getProfileID();

        Log.i(TAG, mProfileDB.getCountry() + " " + mProfileDB.getBirthday() + " " + mProfileDB.getGenrer());

        mPerfilEstatisticasView.setDadosPessoais(mProfileDB.getCountry(), mProfileDB.getBirthday(), mProfileDB.getGenrer());
        mPerfilEstatisticasView.setDescricao(mProfileDB.getDescricao());
        mPerfilEstatisticasView.setTotalHorasAssistidas(mProfileRepository.getTotalHoursWatched(profileID));
        mPerfilEstatisticasView.setTotalFilmesAssistidos((int) mProfileRepository.getTotalMovies(profileID));
        mPerfilEstatisticasView.configurateGraphic(mProfileRepository.getAllGenrersSaved(profileID));

        mPerfilEstatisticasView.setTotalsMovies(mProfileRepository.getTotalMoviesWatched(profileID), mProfileRepository.getTotalFavorites(profileID),
                                                mProfileRepository.getTotalMoviesWantSee(profileID), mProfileRepository.getTotalMoviesDontWantSee(profileID));

        new Handler().postDelayed(new Runnable() {
            public void run() {
                if (mPerfilEstatisticasView.isAdded()) {
                    mPerfilEstatisticasView.setProgressVisibility(View.GONE);
                    mPerfilEstatisticasView.setContainerPrincipalVisibility(View.VISIBLE);
                }
            }
        }, 2000);
    }

    @Override
    public void setView(PerfilEstatisticasView view) {
        mPerfilEstatisticasView = view;
    }

}
