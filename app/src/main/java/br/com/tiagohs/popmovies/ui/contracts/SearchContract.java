package br.com.tiagohs.popmovies.ui.contracts;

import android.widget.ImageView;

import java.io.Serializable;
import java.util.List;

import br.com.tiagohs.popmovies.model.db.MovieDB;
import br.com.tiagohs.popmovies.model.movie.Movie;
import br.com.tiagohs.popmovies.model.movie.MovieDetails;
import br.com.tiagohs.popmovies.model.person.PersonFind;
import br.com.tiagohs.popmovies.model.response.GenericListResponse;
import br.com.tiagohs.popmovies.ui.presenter.IPresenter;
import br.com.tiagohs.popmovies.util.enumerations.SearchType;
import br.com.tiagohs.popmovies.ui.view.IView;
import io.reactivex.Observable;

public class SearchContract {

    public interface SearchInterceptor {

        Observable<GenericListResponse<Movie>> searchMovies(String query,
                                                            Boolean includeAdult,
                                                            Integer searchYear,
                                                            Integer primaryReleaseYear,
                                                            Integer currentPage);

        Observable<GenericListResponse<PersonFind>> searchPerson(String query,
                                                                 Boolean includeAdult,
                                                                 Integer currentPage);

        Observable<MovieDetails> getMovieDetails(int movieID, String[] appendToResponse);

        Observable<Long> saveMovie(MovieDB movie);
        Observable<Integer> deleteMovieByServerID(long serverID, long profileID);
        Observable<Movie> findMovieByServerID(int serverID, long profileID);
    }

    public interface SearchPresenter extends IPresenter<SearchContract.SearchMoviesView>, Serializable {

        void searchMovies(String query,
                          Boolean includeAdult,
                          Integer searchYear,
                          String tag,
                          Integer primaryReleaseYear, boolean isNewSearch);

        void searchPersons(String query,
                           Boolean includeAdult,
                           String tag,
                           SearchType searchType, boolean isNewSearch);

        void getMovieDetails(int movieID, boolean buttonStage, String tag);
        Observable<Boolean> isJaAssistido(int movieID);

        void setProfileID(long profileID);

        void setMovieView(SearchMoviesView searchMoviesView);
        void setPersonView(SearchPersonsView searchPersonsView);
    }

    public interface SearchMoviesView extends IView {

        void onMovieSelected(int movieID, ImageView posterMovie);

        void setListMovies(List<Movie> listMovies, boolean hasMorePages);
        void addAllMovies(List<Movie> listMovies, boolean hasMorePages);

        void setNenhumFilmeEncontradoVisibility(int visibility);
        void setupRecyclerView();
        void updateAdapter();
    }

    public interface SearchPersonsView extends IView {

        void setListPersons(List<PersonFind> listPersons, boolean hasMorePages);
        void addAllPersons(List<PersonFind> listPersons, boolean hasMorePages);

        void setNenhumaPessoaEncontradoVisibility(int visibility);
        void setupRecyclerView();
        void updateAdapter();
    }

}
