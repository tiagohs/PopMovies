package br.com.tiagohs.popmovies.presenter;

import android.app.Activity;

import android.content.Context;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.VolleyError;

import org.apache.commons.collections4.ListUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.tiagohs.popmovies.App;
import br.com.tiagohs.popmovies.R;
import br.com.tiagohs.popmovies.data.PopMoviesDB;
import br.com.tiagohs.popmovies.data.repository.MovieRepository;

import br.com.tiagohs.popmovies.interceptor.DiscoverInterceptor;
import br.com.tiagohs.popmovies.interceptor.DiscoverInterceptorImpl;
import br.com.tiagohs.popmovies.interceptor.MovieDetailsInterceptor;
import br.com.tiagohs.popmovies.interceptor.MovieDetailsInterceptorImpl;
import br.com.tiagohs.popmovies.interceptor.PersonMoviesInterceptor;
import br.com.tiagohs.popmovies.interceptor.PersonMoviesInterceptorImpl;
import br.com.tiagohs.popmovies.model.credits.CreditMovieBasic;
import br.com.tiagohs.popmovies.model.db.MovieDB;
import br.com.tiagohs.popmovies.model.dto.CarrerMoviesDTO;
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
import br.com.tiagohs.popmovies.util.ViewUtils;
import br.com.tiagohs.popmovies.util.enumerations.MediaType;
import br.com.tiagohs.popmovies.util.enumerations.Sort;
import br.com.tiagohs.popmovies.view.ListMoviesDefaultView;
import br.com.tiagohs.popmovies.view.adapters.ListMoviesAdapter;

public class ListMoviesDefaultPresenterImpl implements ListMoviesDefaultPresenter,
        ResponseListener<GenericListResponse<Movie>>,
        PersonMoviesInterceptor.onPersonMoviesListener,
        DiscoverInterceptor.onDiscoverListener, MovieDetailsInterceptor.onMovieDetailsListener  {
    private static final String TAG = ListMoviesDefaultPresenterImpl.class.getSimpleName();

    private ListMoviesDefaultView mListMoviesDefaultView;
    private MoviesServer mMoviesServer;

    private MovieRepository mMovieRepository;
    private Context mContext;

    private PersonMoviesInterceptor mPersonMoviesInterceptor;
    private DiscoverInterceptor mDiscoverInterceptor;
    private MovieDetailsInterceptor mMovieDetailsInterceptor;

    private int mCurrentPage;
    private int mTotalPages;

    private int mStatus;
    private boolean isSaved;
    private boolean isFavorite;

    private int mId;
    private Sort mTypeList;
    private Map<String, String> mParameters;

    private List<List<MovieDB>> moviesDB;
    private int mListIndex = 0;

    private MaterialDialog mDialog;
    private int mPosition;

    public ListMoviesDefaultPresenterImpl() {
        mCurrentPage = 0;
        mMovieDetailsInterceptor = new MovieDetailsInterceptorImpl(this);
        moviesDB = new ArrayList<>();
    }

    public void resetValues() {
        //mListIndex = 0;
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

    public void onCancellRequest(Activity activity, String tag) {
        ((App) activity.getApplication()).cancelAll(tag);
    }

    @Override
    public void getMovies(int id, Sort typeList, String tag, Map<String, String> parameters) {
        mListMoviesDefaultView.setProgressVisibility(View.VISIBLE);

        mId = id;
        mTypeList = typeList;
        mParameters = parameters;

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
            case ASSISTIDOS:
            case QUERO_VER:
            case NAO_QUERO_VER:
                getMoviesDB(typeList);
                ++mCurrentPage;
                break;
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

    public void getMovieDetails(int movieID, boolean isSaved, boolean isFavorite, int status, String tag, MaterialDialog dialog, int position) {
        this.mDialog = dialog;
        this.mStatus = status;
        this.isSaved = isSaved;
        this.isFavorite = isFavorite;
        this.mPosition = position;

        Log.i(TAG, "Filme buscado." + movieID);

        if (mListMoviesDefaultView.isInternetConnected()) {
            mMovieDetailsInterceptor.getMovieDetails(movieID, new String[]{}, tag);
        } else {
            noConnectionError();
        }
    }

    private void getMoviesDB(Sort typeList) {
        mListMoviesDefaultView.setProgressVisibility(View.VISIBLE);
        mListMoviesDefaultView.setRecyclerViewVisibility(mListIndex == 0 ? View.GONE : View.VISIBLE);

        new MoviesSearch().execute(typeList);
    }

    private void noConnectionError() {

        if (mListMoviesDefaultView.isAdded())
            mListMoviesDefaultView.onError(R.string.no_internet);

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

        mListMoviesDefaultView.setRecyclerViewVisibility(View.VISIBLE);
        mListMoviesDefaultView.setProgressVisibility(View.GONE);
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
            if (list.contains(movie))
                continue;
            else {
                movie.setTitle(m.getTitle());
                movie.setPosterPath(m.getArtworkPath());
                list.add(movie);
            }
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

    @Override
    public void onMovieDetailsRequestSucess(MovieDetails movie) {
        long id = 0;
        boolean isDelete = false;

        if (isSaved) {
            id = mMovieRepository.saveMovie(new MovieDB(movie.getId(), mStatus, movie.getRuntime(), movie.getPosterPath(),
                    movie.getTitle(), isFavorite, movie.getVoteCount(), PrefsUtils.getCurrentProfile(mContext).getProfileID(),
                    Calendar.getInstance(), MovieUtils.formateStringToCalendar(movie.getReleaseDate()),
                    MovieUtils.getYearByDate(movie.getReleaseDate()), MovieUtils.genreToGenreDB(movie.getGenres())));
        } else if ((!isSaved) && isFavorite) {
            id = mMovieRepository.saveMovie(new MovieDB(movie.getId(), mStatus, movie.getRuntime(), movie.getPosterPath(),
                    movie.getTitle(), isFavorite, movie.getVoteCount(), PrefsUtils.getCurrentProfile(mContext).getProfileID(),
                    Calendar.getInstance(), MovieUtils.formateStringToCalendar(movie.getReleaseDate()),
                    MovieUtils.getYearByDate(movie.getReleaseDate()), MovieUtils.genreToGenreDB(movie.getGenres())));
        } else {
            mMovieRepository.deleteMovieByServerID(movie.getId(), PrefsUtils.getCurrentProfile(mContext).getProfileID());
            isDelete = true;
        }

        if (isDelete)
            mListMoviesDefaultView.notifyMovieRemoved(mPosition);
        else {
            mListIndex = 0;
            getMovies(mId, mTypeList, TAG, mParameters);
        }

        if (id == -1)
            ViewUtils.createToastMessage(mContext, "Erro ao salvar Filme.");

        mDialog.dismiss();


    }

    @Override
    public void onMovieDetailsRequestError(VolleyError error) {
        mDialog.dismiss();
        ViewUtils.createToastMessage(mContext, "Erro ao Salvar o filme.");
    }

    private class MoviesSearch extends AsyncTask<Sort, Void, GenericListResponse<Movie>> {


        @Override
        protected GenericListResponse<Movie> doInBackground(Sort... sorts) {
            Sort typeList = sorts[0];
            GenericListResponse<Movie> genericListResponse = new GenericListResponse<Movie>();

            if (mListIndex == 0) {
                List<MovieDB> movies = new ArrayList<>();

                switch (typeList) {
                    case ASSISTIDOS:
                        movies = mMovieRepository.findAllMoviesWatched(PrefsUtils.getCurrentProfile(mContext).getProfileID());
                        break;
                    case FAVORITE:
                        movies = mMovieRepository.findAllFavoritesMovies(PrefsUtils.getCurrentProfile(mContext).getProfileID());
                        break;
                    case QUERO_VER:
                        movies = mMovieRepository.findAllMoviesWantSee(PrefsUtils.getCurrentProfile(mContext).getProfileID());
                        break;
                    case NAO_QUERO_VER:
                        movies = mMovieRepository.findAllMoviesDontWantSee(PrefsUtils.getCurrentProfile(mContext).getProfileID());
                        break;
                }

                if (movies.isEmpty()) {
                    genericListResponse.setResults(MovieUtils.convertMovieDBToMovie(movies));
                    genericListResponse.setPage(1);
                    return genericListResponse;
                }

                moviesDB = ListUtils.partition(movies, 12);
            }

            try {
                genericListResponse.setResults(MovieUtils.convertMovieDBToMovie(moviesDB.get(mListIndex++)));
                genericListResponse.setPage(mListIndex);
                genericListResponse.setTotalPage(moviesDB.size());
            } catch (Exception e) {
                genericListResponse.setResults(new ArrayList<Movie>());
                genericListResponse.setPage(mListIndex);
                genericListResponse.setTotalPage(moviesDB.size());
            }

            return genericListResponse;
        }

        @Override
        protected void onPostExecute(GenericListResponse<Movie> movieGenericListResponse) {
            super.onPostExecute(movieGenericListResponse);

            onResponse(movieGenericListResponse);
        }
    }
}
