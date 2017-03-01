package br.com.tiagohs.popmovies.ui.contracts;

import java.util.Calendar;
import java.util.List;

import br.com.tiagohs.popmovies.model.db.ProfileDB;
import br.com.tiagohs.popmovies.model.dto.GenrerMoviesDTO;
import br.com.tiagohs.popmovies.ui.presenter.IPresenter;
import br.com.tiagohs.popmovies.ui.view.IView;
import io.reactivex.Observable;

/**
 * Created by Tiago on 25/02/2017.
 */

public class PerfilEstatisticasContract {

    public interface PerfilEstatisticasInterceptor {

        Observable<ProfileDB> findProfileByUserUsername(String username);

        Observable<Long> getTotalHoursWatched(long profileID);
        Observable<Long> getTotalMovies(long profileID);

        Observable<Long> getTotalMoviesWatched(long profileID);
        Observable<Long> getTotalMoviesWantSee(long profileID);
        Observable<Long> getTotalMoviesDontWantSee(long profileID);
        Observable<Long> getTotalFavorites(long profileID);

        Observable<List<GenrerMoviesDTO>> getAllGenrersSaved(long profileID);
    }

    public interface PerfilEstatisticasPresenter extends IPresenter<PerfilEstatisticasView> {

        void initUpdates(String username);

    }


    public interface PerfilEstatisticasView extends IView {

        void setTotalHorasAssistidas(long duracaoTotal);
        void setTotalFilmesAssistidos(int totalFilmesAssistidos);
        void configurateGraphic(List<GenrerMoviesDTO> genres);

        void setTotalMoviesWatched(long total);
        void setTotalMoviesFavorite(long total);
        void setTotalMoviesWanSee(long total);
        void setTotalMoviesDontWanSee(long total);

        void setDadosPessoais(String pais, Calendar birthday, String genero);
        void setDescricao(String descricao);
        void setContainerPrincipalVisibility(int visibility);
    }
}
