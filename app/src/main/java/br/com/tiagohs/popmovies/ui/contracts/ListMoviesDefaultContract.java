package br.com.tiagohs.popmovies.ui.contracts;

import java.util.List;

import br.com.tiagohs.popmovies.model.dto.DiscoverDTO;
import br.com.tiagohs.popmovies.model.dto.MovieListDTO;
import br.com.tiagohs.popmovies.model.movie.Movie;
import br.com.tiagohs.popmovies.model.movie.MovieDetails;
import br.com.tiagohs.popmovies.model.response.GenericListResponse;
import br.com.tiagohs.popmovies.ui.presenter.IPresenter;
import br.com.tiagohs.popmovies.util.enumerations.Sort;
import br.com.tiagohs.popmovies.ui.view.IView;
import io.reactivex.Observable;

/**
 * Created by Tiago on 25/02/2017.
 */

public class ListMoviesDefaultContract {

    public interface ListMoviesDefaultPresenter extends IPresenter<ListMoviesDefaultView> {

        void getMovies(int id, Sort typeList, String tag, DiscoverDTO discoverDTO);
        void getMovieDetails(int movieID, boolean isSaved, boolean isFavorite, boolean dontIsFavorite, int status, String tag, int position);

        void setProfileID(long profileID);

        Observable<Movie> isJaAssistido(int movieID);
    }

    public interface ListMoviesDefaultView extends IView {

        void hideDialogProgress();
        void showDialogProgress();

        void setProgressVisibility(int visibityState);
        void setRecyclerViewVisibility(int visibilityState);
        void setNenhumFilmeEncontradoVisibility(int visibility);
        void setupRecyclerView();
        void setListMovies(List<MovieListDTO> listMovies, boolean hasMorePages);
        void addAllMovies(List<MovieListDTO> listMovies, boolean hasMorePages);
        void updateAdapter();
        void notifyMovieRemoved(int position);

        void onErrorSaveMovie();
        void onSucessSaveMovie();
        void onDeleteSaveSucess();
    }

    public interface ListMoviesDefaultInterceptor {

        Observable<GenericListResponse<Movie>> getDiscoverMovies(int currentPage, DiscoverDTO discoverDTO);

        Observable<GenericListResponse<Movie>> getMoviesByKeywords(int keywordID, int currentPage);

        Observable<GenericListResponse<Movie>> getMovieSimilars(int movieID, int currentPage);

        Observable<GenericListResponse<Movie>> getMoviesByGenres(int genreID, int currentPage);

        Observable<GenericListResponse<Movie>> getPersonMoviesCredits(int genreID, int currentPage);

        Observable<MovieDetails> getMovieDetails(int movieID, String[] appendToResponse);

        Observable<GenericListResponse<Movie>> getMovieDB(Sort typeList, long profileID, final int currentPage);

        Observable<Long> saveMovie(MovieDetails movie, boolean isFavorite, int status, long profileID);

        Observable<Integer> deleteMovie(MovieDetails movie, long profileID);

        Observable<Movie> findMovieByServerID(int serverID, long profileID);
    }
}
