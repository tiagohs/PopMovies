package br.com.tiagohs.popmovies.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import br.com.tiagohs.popmovies.data.repository.ProfileRepository;
import br.com.tiagohs.popmovies.model.db.ProfileDB;
import br.com.tiagohs.popmovies.util.MovieUtils;
import br.com.tiagohs.popmovies.util.PrefsUtils;
import br.com.tiagohs.popmovies.util.ViewUtils;
import br.com.tiagohs.popmovies.view.PerfilEstatisticasView;
import br.com.tiagohs.popmovies.view.activity.MovieDetailActivity;

public class PerfilEstatisticasPresenterImpl implements PerfilEstatisticasPresenter {
    private static final String TAG = PerfilEstatisticasPresenterImpl.class.getSimpleName();

    private PerfilEstatisticasView mPerfilEstatisticasView;
    private ProfileRepository mProfileRepository;
    private Context mContext;
    private ProfileDB mProfileDB;

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

        new Handler().postDelayed(new Runnable() {
            public void run() {
                mPerfilEstatisticasView.setProgressVisibility(View.GONE);
                mPerfilEstatisticasView.setContainerPrincipalVisibility(View.VISIBLE);
            }
        }, 2000);
    }

    @Override
    public void setView(PerfilEstatisticasView view) {
        mPerfilEstatisticasView = view;
    }

    @Override
    public void onCancellRequest(Activity activity, String tag) {

    }
}
