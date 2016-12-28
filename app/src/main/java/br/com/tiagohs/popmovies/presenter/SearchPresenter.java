package br.com.tiagohs.popmovies.presenter;

import android.content.Context;

import br.com.tiagohs.popmovies.model.movie.MovieDetails;
import br.com.tiagohs.popmovies.util.enumerations.SearchType;
import br.com.tiagohs.popmovies.view.SearchView;

public interface SearchPresenter extends BasePresenter<SearchView> {

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
    void setContext(Context context);
}
