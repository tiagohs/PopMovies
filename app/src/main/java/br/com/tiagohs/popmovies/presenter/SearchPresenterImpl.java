package br.com.tiagohs.popmovies.presenter;

import com.android.volley.VolleyError;

import br.com.tiagohs.popmovies.interceptor.SearchMoviesInterceptor;
import br.com.tiagohs.popmovies.interceptor.SearchMoviesInterceptorImpl;
import br.com.tiagohs.popmovies.interceptor.SearchPersonInterceptor;
import br.com.tiagohs.popmovies.interceptor.SearchPersonInterceptorImpl;
import br.com.tiagohs.popmovies.model.Movie.Movie;
import br.com.tiagohs.popmovies.model.credits.MediaBasic;
import br.com.tiagohs.popmovies.model.response.GenericListResponse;
import br.com.tiagohs.popmovies.util.enumerations.SearchType;
import br.com.tiagohs.popmovies.view.SearchView;

public class SearchPresenterImpl implements SearchPresenter, SearchPersonInterceptor.onSearchPersonListener, SearchMoviesInterceptor.onSearchMoviesListener {
    private static final String TAG = SearchPresenterImpl.class.getSimpleName();

    private SearchView mSearchView;
    private SearchMoviesInterceptor mSearchMoviesInterceptor;
    private SearchPersonInterceptor mSearchPersonInterceptor;

    public SearchPresenterImpl() {
        mSearchMoviesInterceptor = new SearchMoviesInterceptorImpl(this);
        mSearchPersonInterceptor = new SearchPersonInterceptorImpl(this);
    }

    @Override
    public void setView(SearchView view) {
        this.mSearchView = view;
    }

    public void searchMovies(String query,
                             Boolean includeAdult,
                             Integer searchYear,
                             Integer primaryReleaseYear,
                             Integer currentPage) {
        mSearchMoviesInterceptor.searchMovies(query, includeAdult, searchYear, primaryReleaseYear, currentPage);
    }

    public void searchPersons(String query,
                              Boolean includeAdult,
                              SearchType searchType,
                              Integer currentPage) {
        mSearchView.showProgressBar();
        mSearchPersonInterceptor.searchPersons(query, includeAdult, searchType, currentPage);
    }


    @Override
    public void onSearchMoviesRequestSucess(GenericListResponse<Movie> imageResponse) {
        mSearchView.hideProgressBar();
        mSearchView.atualizarView(imageResponse.getPage(), imageResponse.getTotalPage(), imageResponse.getResults());
    }

    @Override
    public void onSearchMoviesRequestError(VolleyError error) {

    }

    @Override
    public void onSearchPersonRequestSucess(GenericListResponse<MediaBasic> imageResponse) {

    }

    @Override
    public void onSearchPersonRequestError(VolleyError error) {

    }
}
