package br.com.tiagohs.popmovies.presenter;

import android.view.View;

import com.android.volley.VolleyError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.tiagohs.popmovies.interceptor.DiscoverInterceptor;
import br.com.tiagohs.popmovies.interceptor.DiscoverInterceptorImpl;
import br.com.tiagohs.popmovies.interceptor.PersonMoviesInterceptor;
import br.com.tiagohs.popmovies.interceptor.PersonMoviesInterceptorImpl;
import br.com.tiagohs.popmovies.model.credits.CreditMovieBasic;
import br.com.tiagohs.popmovies.model.movie.Movie;
import br.com.tiagohs.popmovies.model.response.GenericListResponse;
import br.com.tiagohs.popmovies.model.response.MovieResponse;
import br.com.tiagohs.popmovies.model.response.PersonMoviesResponse;
import br.com.tiagohs.popmovies.server.PopMovieServer;
import br.com.tiagohs.popmovies.server.ResponseListener;
import br.com.tiagohs.popmovies.util.DTOUtils;
import br.com.tiagohs.popmovies.util.enumerations.Sort;
import br.com.tiagohs.popmovies.view.ListMoviesDefaultView;

public class ListMoviesDefaultPresenterImpl implements ListMoviesDefaultPresenter,
        ResponseListener<GenericListResponse<Movie>>,
        PersonMoviesInterceptor.onPersonMoviesListener,
        DiscoverInterceptor.onDiscoverListener {

    private ListMoviesDefaultView mListMoviesDefaultView;
    private PopMovieServer mPopMovieServer;

    private PersonMoviesInterceptor mPersonMoviesInterceptor;
    private DiscoverInterceptor mDiscoverInterceptor;

    private int mCurrentPage;
    private int mTotalPages;

    public ListMoviesDefaultPresenterImpl() {
        mCurrentPage = 0;
    }

    @Override
    public void setView(ListMoviesDefaultView view) {
        mListMoviesDefaultView = view;
        mPopMovieServer = PopMovieServer.getInstance();

        mPersonMoviesInterceptor = new PersonMoviesInterceptorImpl(this);
        mDiscoverInterceptor = new DiscoverInterceptorImpl(this);
    }

    @Override
    public void getMovies(int id, Sort typeList, Map<String, String> parameters) {
        mListMoviesDefaultView.setProgressVisibility(View.VISIBLE);

        if (mListMoviesDefaultView.isInternetConnected()) {
            choiceTypeList(id, typeList, parameters);
            mListMoviesDefaultView.setRecyclerViewVisibility(isFirstPage() ? View.GONE : View.VISIBLE);
        } else {
            noConnectionError();
        }
    }

    private void choiceTypeList(int id, Sort typeList, Map<String, String> parameters) {

        switch (typeList) {
            case GENEROS:
                mPopMovieServer.getMoviesByGenres(id, ++mCurrentPage, this);
                break;
            case COMPANY:

                break;
            case DISCOVER:
                mDiscoverInterceptor.getMovies(++mCurrentPage, parameters);
                break;
            case KEYWORDS:
                mPopMovieServer.getMoviesByKeywords(id, ++mCurrentPage, parameters, this);
                break;
            case SIMILARS:
                mPopMovieServer.getMovieSimilars(id, ++mCurrentPage, this);
                break;
            case PERSON_MOVIES_CARRER:
            case PERSON_CONHECIDO_POR:
                mPersonMoviesInterceptor.getPersonMovies(typeList, id, ++mCurrentPage, new HashMap<String, String>());
                break;
        }
    }


    private void noConnectionError() {

        if (mListMoviesDefaultView.isAdded())
            mListMoviesDefaultView.onError("Sem Conexão");

        mListMoviesDefaultView.setProgressVisibility(View.GONE);

        if (mCurrentPage == 0) {
            mListMoviesDefaultView.setRecyclerViewVisibility(View.GONE);
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(GenericListResponse<Movie> response) {
        mCurrentPage = response.getPage();
        mTotalPages = response.getTotalPage();

        if (isFirstPage()) {
            mListMoviesDefaultView.setListMovies(DTOUtils.createMovieListDTO(response.getResults()), hasMorePages());
            mListMoviesDefaultView.setupRecyclerView();
        } else {
            mListMoviesDefaultView.addAllMovies(DTOUtils.createMovieListDTO(response.getResults()), hasMorePages());
            mListMoviesDefaultView.updateAdapter();
        }

        mListMoviesDefaultView.setProgressVisibility(View.GONE);
        mListMoviesDefaultView.setRecyclerViewVisibility(View.VISIBLE);
    }

    private boolean isFirstPage() {
        return mCurrentPage == 1;
    }

    private boolean hasMorePages() {
        return mCurrentPage < mTotalPages;
    }

    @Override
    public void onPersonMoviesRequestSucess(PersonMoviesResponse moviesResponse) {
        moviesResponse.getMoviesByCast().addAll(moviesResponse.getMoviesByCrew());
        GenericListResponse<Movie> response = new GenericListResponse<>();
        response.setId(moviesResponse.getId());
        response.setResults(createMovies(moviesResponse.getMoviesByCast()));
        response.setTotalPage(1);
        response.setTotalResults(1);
        response.setPage(1);

        onResponse(response);
    }

    private List<Movie> createMovies(List<CreditMovieBasic> listMovies) {
        List<Movie> list = new ArrayList<>();

        for (CreditMovieBasic m : listMovies) {
            Movie movie = new Movie();
            movie.setId(m.getId());
            movie.setTitle(m.getTitle());
            movie.setPosterPath(m.getArtworkPath());
            list.add(movie);
        }

        return list;
    }

    @Override
    public void onPersonMoviesRequestError(VolleyError error) {
        onErrorResponse(error);
    }

    @Override
    public void onDiscoverRequestSucess(MovieResponse moviesResponse) {
        GenericListResponse<Movie> response = new GenericListResponse<>();
        response.setResults(moviesResponse.getResults());
        response.setPage(moviesResponse.getPage());
        response.setTotalPage(moviesResponse.getTotalPages());
        response.setTotalResults(moviesResponse.getTotalResults());

        onResponse(response);
    }

    @Override
    public void onDiscoverRequestError(VolleyError error) {
        onErrorResponse(error);
    }
}
