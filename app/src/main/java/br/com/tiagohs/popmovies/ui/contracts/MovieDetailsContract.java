package br.com.tiagohs.popmovies.ui.contracts;

import java.util.List;

import br.com.tiagohs.popmovies.model.db.MovieDB;
import br.com.tiagohs.popmovies.model.dto.ItemListDTO;
import br.com.tiagohs.popmovies.model.movie.MovieDetails;
import br.com.tiagohs.popmovies.model.response.VideosResponse;
import br.com.tiagohs.popmovies.ui.presenter.IPresenter;
import br.com.tiagohs.popmovies.ui.view.IView;
import io.reactivex.Observable;

public class MovieDetailsContract {

    public interface MovieDetailsInterceptor {

        Observable<MovieDetails> getMovieDetails(int movieID, String[] appendToResponse);
        Observable<MovieDetails> getMovieDetails(int movieID, String[] appendToResponse, String language);

        Observable<VideosResponse> getMovieVideos(int movieID, String language);

        Observable<Boolean> isFavoriteMovie(long profileID, int serverID);
        Observable<Boolean> isWantSeeMovie(long profileID, int serverID);
        Observable<Boolean> isWachedMovie(long profileID, int serverID);
        Observable<Boolean> isDontWantSeeMovie(long profileID, int serverID);

        Observable<Long> saveMovie(MovieDB movie);
        Observable<Integer> deleteMovieByServerID(long serverID, long profileID);

    }

    public interface MovieDetailsPresenter extends IPresenter<MovieDetailsView> {

        void getMovieDetails(int movieID);
        void onClickJaAssisti(MovieDetails movie, boolean buttonStage);
        void setProfileID(long profileID);
    }

    public interface MovieDetailsView extends IView {

        void setupDirectorsRecyclerView(List<ItemListDTO> list);
        void setupTabs();

        void updateUI(MovieDetails movie);
        void updateVideos(VideosResponse videos);

        void setJaAssistido();
        void setTabsVisibility(int visibilityState);
        void setPlayButtonVisibility(int visibilityState);
        void setDuracaoMovieVisibility(int visibility);
    }
}
