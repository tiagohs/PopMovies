package br.com.tiagohs.popmovies.presenter;

import android.app.Activity;
import android.content.Context;

import br.com.tiagohs.popmovies.data.repository.ProfileRepository;
import br.com.tiagohs.popmovies.util.PrefsUtils;
import br.com.tiagohs.popmovies.view.PerfilEstatisticasView;

public class PerfilEstatisticasPresenterImpl implements PerfilEstatisticasPresenter {

    private PerfilEstatisticasView mPerfilEstatisticasView;
    private ProfileRepository mProfileRepository;
    private Context mContext;

    public PerfilEstatisticasPresenterImpl() {

    }

    public void setContext(Context context) {
        mContext = context;
        mProfileRepository = new ProfileRepository(mContext);
    }

    public void initUpdates() {
        int duaracaoTotalAssistidas = 0;
        long profileID = PrefsUtils.getCurrentProfile(mContext).getProfileID();

        mPerfilEstatisticasView.setTotalHorasAssistidas(mProfileRepository.getTotalHoursWatched(profileID));
        mPerfilEstatisticasView.setTotalFilmesAssistidos((int) mProfileRepository.getTotalMoviesWached(profileID));
    }

    @Override
    public void setView(PerfilEstatisticasView view) {
        mPerfilEstatisticasView = view;
    }

    @Override
    public void onCancellRequest(Activity activity, String tag) {

    }
}
