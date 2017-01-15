package br.com.tiagohs.popmovies.presenter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import android.view.View;

import com.android.volley.VolleyError;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.tiagohs.popmovies.App;
import br.com.tiagohs.popmovies.data.PopMoviesDB;
import br.com.tiagohs.popmovies.data.repository.MovieRepository;
import br.com.tiagohs.popmovies.interceptor.MovieDetailsInterceptor;
import br.com.tiagohs.popmovies.interceptor.MovieDetailsInterceptorImpl;
import br.com.tiagohs.popmovies.interceptor.VideoInterceptor;
import br.com.tiagohs.popmovies.interceptor.VideoInterceptorImpl;
import br.com.tiagohs.popmovies.model.credits.MediaCreditCrew;
import br.com.tiagohs.popmovies.model.db.MovieDB;
import br.com.tiagohs.popmovies.model.dto.ItemListDTO;
import br.com.tiagohs.popmovies.model.movie.Movie;
import br.com.tiagohs.popmovies.model.movie.MovieDetails;
import br.com.tiagohs.popmovies.model.response.VideosResponse;
import br.com.tiagohs.popmovies.server.methods.MoviesServer;
import br.com.tiagohs.popmovies.util.MovieUtils;
import br.com.tiagohs.popmovies.util.PrefsUtils;
import br.com.tiagohs.popmovies.util.enumerations.SubMethod;
import br.com.tiagohs.popmovies.view.MovieDetailsView;

public class MovieDetailsPresenterImpl implements MovieDetailsPresenter, VideoInterceptor.onVideoListener, MovieDetailsInterceptor.onMovieDetailsListener {
    private static final String TAG = MovieDetailsPresenterImpl.class.getSimpleName();

    private MovieDetailsView mMovieDetailsView;

    private MovieRepository mMovieRepository;
    private VideoInterceptor mVideoInterceptor;

    private String mTag;
    private Context mContext;

    private MovieDetailsInterceptor mMovieDetailsInterceptor;

    private String[] mMovieParameters = new String[]{SubMethod.CREDITS.getValue(), SubMethod.RELEASE_DATES.getValue(),
            SubMethod.SIMILAR.getValue(), SubMethod.KEYWORDS.getValue(),
            SubMethod.VIDEOS.getValue(), SubMethod.TRANSLATIONS.getValue()};

    public MovieDetailsPresenterImpl() {
        mVideoInterceptor = new VideoInterceptorImpl(this);
        mMovieDetailsInterceptor = new MovieDetailsInterceptorImpl(this);
    }

    @Override
    public void setView(MovieDetailsView view) {
        this.mMovieDetailsView = view;
    }

    @Override
    public void onCancellRequest(Activity activity, String tag) {
        ((App) activity.getApplication()).cancelAll(tag);
    }

    @Override
    public void getMovieDetails(int movieID, String tag) {
        mTag = tag;

        mMovieDetailsView.setProgressVisibility(View.VISIBLE);

        if (mMovieDetailsView.isInternetConnected()) {
            mMovieDetailsInterceptor.getMovieDetails(movieID, mMovieParameters, tag);
            mMovieDetailsView.setTabsVisibility(View.GONE);
        } else {
            noConnectionError();
        }

    }

    @Override
    public void onClickJaAssisti(MovieDetails movie, boolean buttonStage) {

        if (buttonStage)
            mMovieRepository.saveMovie(new MovieDB(movie.getId(), MovieDB.STATUS_WATCHED, movie.getRuntime(), movie.getPosterPath(),
                    movie.getTitle(), movie.isFavorite(), movie.getVoteCount(), PrefsUtils.getCurrentProfile(mContext).getProfileID(),
                    Calendar.getInstance(), MovieUtils.formateStringToCalendar(movie.getReleaseDate()),
                    MovieUtils.getYearByDate(movie.getReleaseDate()), MovieUtils.genreToGenreDB(movie.getGenres())));
        else
            mMovieRepository.deleteMovieByServerID(movie.getId(), PrefsUtils.getCurrentProfile(mContext).getProfileID());

    }

    @Override
    public void setContext(Context context) {
        mContext = context;
        mMovieRepository = new MovieRepository(mContext);
    }

    private void noConnectionError() {
        if (mMovieDetailsView.isAdded()) {
            mMovieDetailsView.onError("Sem Conexao");
            mMovieDetailsView.setProgressVisibility(View.GONE);
        }

    }

    public void getVideos(MovieDetails movie) {
        if (mMovieDetailsView.isInternetConnected())
            mVideoInterceptor.getVideos(movie.getId(), mTag, movie.getOriginalLanguage());
        else
            noConnectionError();

    }

    private List<ItemListDTO> getDirectorsDTO(List<MediaCreditCrew> crews) {
        List<ItemListDTO> list = new ArrayList<>();

        for (MediaCreditCrew crew : crews) {
            if (crew.getDepartment().equals("Directing") && !list.contains(new ItemListDTO(crew.getId())))
                list.add(new ItemListDTO(crew.getId(), crew.getName()));
        }

        return list;
    }


    @Override
    public void onVideoRequestSucess(VideosResponse videosResponse) {
        if (!videosResponse.getVideos().isEmpty())
            mMovieDetailsView.updateVideos(videosResponse);
        else
            mMovieDetailsView.setPlayButtonVisibility(View.GONE);
    }

    @Override
    public void onVideoRequestError(VolleyError error) {
        noConnectionError();
    }

    @Override
    public boolean isAdded() {
        return mMovieDetailsView.isAdded();
    }

    @Override
    public void onMovieDetailsRequestSucess(MovieDetails movieDetails) {
        if (movieDetails.getVideos().isEmpty())
            getVideos(movieDetails);
        long profileID = PrefsUtils.getCurrentProfile(mContext).getProfileID();

        movieDetails.setFavorite(mMovieRepository.isFavoriteMovie(profileID, movieDetails.getId()));

        if (mMovieRepository.isWachedMovie(profileID, movieDetails.getId()))
            mMovieDetailsView.setJaAssistido();
        else if (mMovieRepository.isWantSeeMovie(profileID, movieDetails.getId()))
            movieDetails.setStatusDB(MovieDB.STATUS_WANT_SEE);

        if (movieDetails.getRuntime() == 0)
            mMovieDetailsView.setDuracaoMovieVisibility(View.GONE);

        mMovieDetailsView.setupDirectorsRecyclerView(getDirectorsDTO(movieDetails.getCrew()));
        mMovieDetailsView.updateUI(movieDetails);
        mMovieDetailsView.setupTabs();
        mMovieDetailsView.setProgressVisibility(View.GONE);
        mMovieDetailsView.setTabsVisibility(View.VISIBLE);
    }

    @Override
    public void onMovieDetailsRequestError(VolleyError error) {
        noConnectionError();
    }
}
