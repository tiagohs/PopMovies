package br.com.tiagohs.popmovies.presenter;

import android.app.Activity;
import android.view.View;

import com.android.volley.VolleyError;

import java.util.ArrayList;
import java.util.List;

import br.com.tiagohs.popmovies.App;
import br.com.tiagohs.popmovies.interceptor.SearchMoviesInterceptor;
import br.com.tiagohs.popmovies.interceptor.SearchMoviesInterceptorImpl;
import br.com.tiagohs.popmovies.interceptor.SearchPersonInterceptor;
import br.com.tiagohs.popmovies.interceptor.SearchPersonInterceptorImpl;
import br.com.tiagohs.popmovies.model.credits.MediaBasic;
import br.com.tiagohs.popmovies.model.movie.Movie;
import br.com.tiagohs.popmovies.model.response.GenericListResponse;
import br.com.tiagohs.popmovies.util.enumerations.SearchType;
import br.com.tiagohs.popmovies.view.SearchView;

public class SearchPresenterImpl implements SearchPresenter, SearchPersonInterceptor.onSearchPersonListener,
                                            SearchMoviesInterceptor.onSearchMoviesListener {

    private SearchView mSearchView;
    private SearchMoviesInterceptor mSearchMoviesInterceptor;
    private SearchPersonInterceptor mSearchPersonInterceptor;

    private int mCurrentPage;
    private int mTotalPages;

    private boolean mIsNewSearch;

    public SearchPresenterImpl() {
        mCurrentPage = 1;

        mSearchMoviesInterceptor = new SearchMoviesInterceptorImpl(this);
        mSearchPersonInterceptor = new SearchPersonInterceptorImpl(this);
    }

    @Override
    public void setView(SearchView view) {
        this.mSearchView = view;
    }

    @Override
    public void onCancellRequest(Activity activity, String tag) {
        ((App) activity.getApplication()).cancelAll(tag);
    }

    public void searchMovies(String query,
                             Boolean includeAdult,
                             Integer searchYear, String tag,
                             Integer primaryReleaseYear, boolean isNewSearch) {
        mIsNewSearch = isNewSearch;

        if (mSearchView.isInternetConnected()) {
            mSearchMoviesInterceptor.searchMovies(query, includeAdult,
                                                  searchYear, primaryReleaseYear, tag,
                                                  mIsNewSearch ? mCurrentPage : ++mCurrentPage);
        } else
            noConnectionError();
    }

    private void noConnectionError() {
        if (mSearchView.isAdded())
            mSearchView.onError("Sem Conexão");

        mSearchView.setProgressVisibility(View.GONE);
    }

    public void searchPersons(String query,
                              Boolean includeAdult, String tag,
                              SearchType searchType, boolean isNewSearch) {

        if (mSearchView.isInternetConnected()) {
            mSearchView.setProgressVisibility(View.VISIBLE);
            mSearchPersonInterceptor.searchPersons(query, includeAdult, searchType, tag,
                                                   isNewSearch ? mCurrentPage : ++mCurrentPage);
        } else
            noConnectionError();
    }


    @Override
    public void onSearchMoviesRequestSucess(GenericListResponse<Movie> response) {
        mCurrentPage = response.getPage();
        mTotalPages = response.getTotalPage();
        mSearchView.setNenhumFilmeEncontradoVisibility(View.GONE);

        if (mIsNewSearch) {
            if (response.getResults().isEmpty()) {
                mSearchView.setNenhumFilmeEncontradoVisibility(View.VISIBLE);
                setupResponseMovies(new ArrayList<Movie>());
            } else {
                setupResponseMovies(response.getResults());
            }
        } else {
            mSearchView.addAllMovies(response.getResults(), hasMorePages());
            mSearchView.updateAdapter();
        }

        mSearchView.setProgressVisibility(View.GONE);
    }

    private void setupResponseMovies(List<Movie> movies) {
        mSearchView.setListMovies(movies, hasMorePages());
        mSearchView.setupRecyclerView();
    }

    private boolean hasMorePages() {
        return mCurrentPage < mTotalPages;
    }

    @Override
    public void onSearchMoviesRequestError(VolleyError error) {
        mSearchView.setNenhumFilmeEncontradoVisibility(View.GONE);

        switch (error.networkResponse.statusCode) {
            case 422:
                setupResponseMovies(new ArrayList<Movie>());
                break;
            default:
                noConnectionError();
        }
    }

    @Override
    public void onSearchPersonRequestSucess(GenericListResponse<MediaBasic> imageResponse) {

    }

    @Override
    public void onSearchPersonRequestError(VolleyError error) {

    }
}
