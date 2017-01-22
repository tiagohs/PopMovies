package br.com.tiagohs.popmovies.presenter;

import android.app.Activity;
import android.content.Context;

import br.com.tiagohs.popmovies.data.repository.ProfileRepository;
import br.com.tiagohs.popmovies.model.db.ProfileDB;
import br.com.tiagohs.popmovies.util.MovieUtils;
import br.com.tiagohs.popmovies.util.PrefsUtils;
import br.com.tiagohs.popmovies.view.PerfilEstatisticasView;

public class PerfilEstatisticasPresenterImpl implements PerfilEstatisticasPresenter {
    private static final String TAG = PerfilEstatisticasPresenterImpl.class.getSimpleName();

    private PerfilEstatisticasView mPerfilEstatisticasView;
    private ProfileRepository mProfileRepository;
    private Context mContext;
    private ProfileDB mProfileDB;

    public PerfilEstatisticasPresenterImpl() {

    }

    public void setContext(Context context) {
        mContext = context;
        mProfileRepository = new ProfileRepository(mContext);
        mProfileDB = PrefsUtils.getCurrentProfile(mContext);
    }

    public void initUpdates() {
        long profileID = mProfileDB.getProfileID();

        mPerfilEstatisticasView.setDadosPessoais(mProfileDB.getCountry(), mProfileDB.getBirthday(), mProfileDB.getGenrer());
        mPerfilEstatisticasView.setDescricao(mProfileDB.getDescricao());
        mPerfilEstatisticasView.setTotalHorasAssistidas(mProfileRepository.getTotalHoursWatched(profileID));
        mPerfilEstatisticasView.setTotalFilmesAssistidos((int) mProfileRepository.getTotalMovies(profileID));
        mPerfilEstatisticasView.configurateGraphic(mProfileRepository.getAllGenrersSaved(profileID));

        mPerfilEstatisticasView.setTotalsMovies(mProfileRepository.getTotalMoviesWatched(profileID), mProfileRepository.getTotalFavorites(profileID),
                                                mProfileRepository.getTotalMoviesWantSee(profileID), mProfileRepository.getTotalMoviesDontWantSee(profileID));

    }

    @Override
    public void setView(PerfilEstatisticasView view) {
        mPerfilEstatisticasView = view;
    }

    @Override
    public void onCancellRequest(Activity activity, String tag) {

    }
}
