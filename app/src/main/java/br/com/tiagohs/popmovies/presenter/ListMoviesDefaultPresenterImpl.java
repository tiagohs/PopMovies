package br.com.tiagohs.popmovies.presenter;

import android.app.Activity;
<<<<<<< HEAD
import android.content.Context;
=======
>>>>>>> origin/master
import android.view.View;

import com.android.volley.VolleyError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.tiagohs.popmovies.App;
<<<<<<< HEAD
import br.com.tiagohs.popmovies.data.PopMoviesDB;
import br.com.tiagohs.popmovies.data.repository.MovieRepository;
=======
>>>>>>> origin/master
import br.com.tiagohs.popmovies.interceptor.DiscoverInterceptor;
import br.com.tiagohs.popmovies.interceptor.DiscoverInterceptorImpl;
import br.com.tiagohs.popmovies.interceptor.PersonMoviesInterceptor;
import br.com.tiagohs.popmovies.interceptor.PersonMoviesInterceptorImpl;
import br.com.tiagohs.popmovies.model.credits.CreditMovieBasic;
import br.com.tiagohs.popmovies.model.dto.MovieListDTO;
import br.com.tiagohs.popmovies.model.movie.Movie;
import br.com.tiagohs.popmovies.model.movie.MovieDetails;
import br.com.tiagohs.popmovies.model.response.GenericListResponse;
import br.com.tiagohs.popmovies.model.response.MovieResponse;
import br.com.tiagohs.popmovies.model.response.PersonMoviesResponse;
import br.com.tiagohs.popmovies.server.ResponseListener;
import br.com.tiagohs.popmovies.server.methods.MoviesServer;
import br.com.tiagohs.popmovies.util.DTOUtils;
import br.com.tiagohs.popmovies.util.MovieUtils;
import br.com.tiagohs.popmovies.util.PrefsUtils;
import br.com.tiagohs.popmovies.util.enumerations.Sort;
import br.com.tiagohs.popmovies.view.ListMoviesDefaultView;

public class ListMoviesDefaultPresenterImpl implements ListMoviesDefaultPresenter,
        ResponseListener<GenericListResponse<Movie>>,
        PersonMoviesInterceptor.onPersonMoviesListener,
        DiscoverInterceptor.onDiscoverListener {

    private ListMoviesDefaultView mListMoviesDefaultView;
    private MoviesServer mMoviesServer;
<<<<<<< HEAD
    private MovieRepository mMovieRepository;
    private Context mContext;
=======
>>>>>>> origin/master

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
        mMoviesServer = new MoviesServer();

        mPersonMoviesInterceptor = new PersonMoviesInterceptorImpl(this);
        mDiscoverInterceptor = new DiscoverInterceptorImpl(this);
    }

    public void setContext(Context context) {
        mContext = context;
        mMovieRepository = new MovieRepository(mContext);
    }

    @Override
    public void onCancellRequest(Activity activity, String tag) {
        ((App) activity.getApplication()).cancelAll(tag);
    }

    @Override
<<<<<<< HEAD
=======
    public void onCancellRequest(Activity activity, String tag) {
        ((App) activity.getApplication()).cancelAll(tag);
    }

    @Override
>>>>>>> origin/master
    public void getMovies(int id, Sort typeList, String tag, Map<String, String> parameters) {
        mListMoviesDefaultView.setProgressVisibility(View.VISIBLE);

        if (mListMoviesDefaultView.isInternetConnected()) {
            choiceTypeList(id, typeList, tag, parameters);
            mListMoviesDefaultView.setRecyclerViewVisibility(isFirstPage() ? View.GONE : View.VISIBLE);
        } else {
            noConnectionError();
        }
    }

    private void choiceTypeList(int id, Sort typeList, String tag, Map<String, String> parameters) {

        switch (typeList) {
            case FAVORITE:
                getFavitesMovies();
            case ASSISTIDOS:
                getMoviesAssistidos();
            case GENEROS:
                mMoviesServer.getMoviesByGenres(id, ++mCurrentPage, tag, this);
                break;
            case COMPANY:

                break;
            case DISCOVER:
                mDiscoverInterceptor.getMovies(++mCurrentPage, tag, parameters);
                break;
            case KEYWORDS:
                mMoviesServer.getMoviesByKeywords(id, ++mCurrentPage, tag, parameters, this);
                break;
            case SIMILARS:
                mMoviesServer.getMovieSimilars(id, ++mCurrentPage, tag, this);
                break;
            case PERSON_MOVIES_CARRER:
            case PERSON_CONHECIDO_POR:
                mPersonMoviesInterceptor.getPersonMovies(typeList, id, ++mCurrentPage, tag, new HashMap<String, String>());
                break;
        }
    }

    private void getMoviesAssistidos() {
        GenericListResponse<Movie> genericListResponse = new GenericListResponse<Movie>();
        genericListResponse.setPage(1);
        genericListResponse.setTotalPage(1);
        genericListResponse.setResults(mMovieRepository.findAllMovies(PrefsUtils.getCurrentUser(mContext).getProfileID()));

        onResponse(genericListResponse);
    }

    private void getFavitesMovies() {
        GenericListResponse<Movie> genericListResponse = new GenericListResponse<Movie>();
        genericListResponse.setPage(1);
        genericListResponse.setTotalPage(1);
        genericListResponse.setResults(MovieUtils.convertMovieDBToMovie(mMovieRepository.findAllFavoritesMovies(PrefsUtils.getCurrentUser(mContext).getProfileID())));

        onResponse(genericListResponse);
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
            mListMoviesDefaultView.setListMovies(DTOUtils.createMovieListDTO(mContext, response.getResults(), mMovieRepository), hasMorePages());
            mListMoviesDefaultView.setupRecyclerView();
        } else {
            mListMoviesDefaultView.addAllMovies(DTOUtils.createMovieListDTO(mContext, response.getResults(), mMovieRepository), hasMorePages());
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
