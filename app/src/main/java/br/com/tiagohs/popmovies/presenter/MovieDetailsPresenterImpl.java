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

public class MovieDetailsPresenterImpl implements MovieDetailsPresenter, VideoInterceptor.onVideoListener {
    private static final String TAG = MovieDetailsPresenterImpl.class.getSimpleName();

    private MovieDetailsView mMovieDetailsView;

    private MoviesServer mMoviesServer;

    private MovieRepository mMovieRepository;
    private VideoInterceptor mVideoInterceptor;

    private String mOriginalLanguage;

    private MovieDetails mMovieDetails;
    private int mMovieID;
    private String mTag;
    private Context mContext;

    private String[] mMovieParameters = new String[]{SubMethod.CREDITS.getValue(), SubMethod.RELEASE_DATES.getValue(),
            SubMethod.SIMILAR.getValue(), SubMethod.KEYWORDS.getValue(),
            SubMethod.VIDEOS.getValue(), SubMethod.TRANSLATIONS.getValue()};

    public MovieDetailsPresenterImpl() {
        mMoviesServer = new MoviesServer();
        mVideoInterceptor = new VideoInterceptorImpl(this);
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
        mMovieID = movieID;
        mTag = tag;

        mMovieDetailsView.setProgressVisibility(View.VISIBLE);

        if (mMovieDetailsView.isInternetConnected()) {
            mMoviesServer.getMovieDetails(movieID, mMovieParameters, tag, this);
            mMovieDetailsView.setTabsVisibility(View.GONE);
        } else {
            noConnectionError();
        }

    }

    @Override
    public void onClickJaAssisti(MovieDetails movie, boolean buttonStage) {

        if (buttonStage)
            mMovieRepository.saveMovie(new MovieDB(movie.getId(), movie.getPosterPath(), movie.isFavorite(), movie.getVoteCount(), movie.getTitle(), Calendar.getInstance(), PrefsUtils.getCurrentUser(mContext).getProfileID(), movie.getRuntime()));
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

    public void getVideos(String tag) {
        mTag = tag;

        mVideoInterceptor.getVideos(mMovieDetails.getId(), tag, mMovieDetails.getOriginalLanguage());
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        noConnectionError();
    }

    @Override
    public void onResponse(MovieDetails response) {
        if (mOriginalLanguage == null)
            mMovieDetails = response;

        if (mMovieDetailsView.isAdded()) {
            if ((MovieUtils.isEmptyValue(response.getOverview()) || response.getRuntime() == 0) && mOriginalLanguage == null) {

                mOriginalLanguage = response.getOriginalLanguage();
                mMoviesServer.getMovieDetails(mMovieID, mMovieParameters, mTag, mOriginalLanguage, this);

            } else {

                if (mOriginalLanguage != null) {
                    if (MovieUtils.isEmptyValue(mMovieDetails.getOverview())) {
                        mMovieDetails.setOverview(response.getOverview());
                    }
                    if (mMovieDetails.getRuntime() == 0)
                        mMovieDetails.setRuntime(response.getRuntime());

                    initUpdates(mMovieDetails);
                } else {
                    initUpdates(response);
                }

            }
        }

        Log.i(TAG, "Add: " + mMovieDetailsView.isAdded());
    }

    private void initUpdates(MovieDetails movieDetails) {
        Log.i(TAG, "Chegou em initUpdates");
        if (movieDetails.getVideos().isEmpty())
            getVideos(mTag);

        Movie movieAssistido = mMovieRepository.findMovieByServerID(movieDetails.getId(), PrefsUtils.getCurrentUser(mContext).getProfileID());

        if (movieAssistido != null) {
            movieDetails.setJaAssistido(true);
            movieDetails.setFavorite(movieAssistido.isFavorite());
            mMovieDetailsView.setJaAssistido();
        }

        mMovieDetailsView.setupDirectorsRecyclerView(getDirectorsDTO(movieDetails.getCrew()));
        mMovieDetailsView.updateUI(movieDetails);
        mMovieDetailsView.setupTabs();
        mMovieDetailsView.setProgressVisibility(View.GONE);
        mMovieDetailsView.setTabsVisibility(View.VISIBLE);
    }

    private List<ItemListDTO> getDirectorsDTO(List<MediaCreditCrew> crews) {
        List<ItemListDTO> list = new ArrayList<>();

        for (MediaCreditCrew crew : crews) {
            if (crew.getDepartment().equals("Directing") && !list.contains(crew))
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
}
