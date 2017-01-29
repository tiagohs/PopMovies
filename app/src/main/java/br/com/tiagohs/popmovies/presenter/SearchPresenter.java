package br.com.tiagohs.popmovies.presenter;

import android.content.Context;

import java.io.Serializable;

import br.com.tiagohs.popmovies.data.repository.MovieRepository;
import br.com.tiagohs.popmovies.model.movie.MovieDetails;
import br.com.tiagohs.popmovies.util.enumerations.SearchType;
import br.com.tiagohs.popmovies.view.SearchMoviesView;
import br.com.tiagohs.popmovies.view.SearchPersonsView;
import br.com.tiagohs.popmovies.view.SearchView;

public interface SearchPresenter extends BasePresenter<SearchView>, Serializable {

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
    boolean isJaAssistido(int movieID);

    void setMovieRepository(MovieRepository movieRepository);
    void setProfileID(long profileID);

    void setMovieView(SearchMoviesView searchMoviesView);
    void setPersonView(SearchPersonsView searchPersonsView);
}
