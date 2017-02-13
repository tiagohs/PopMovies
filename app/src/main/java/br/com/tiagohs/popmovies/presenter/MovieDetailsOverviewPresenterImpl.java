package br.com.tiagohs.popmovies.presenter;

import android.view.View;

import com.android.volley.VolleyError;

import java.util.Calendar;
import java.util.List;

import br.com.tiagohs.popmovies.data.repository.MovieRepository;
import br.com.tiagohs.popmovies.interceptor.MovieCollectionInterceptor;
import br.com.tiagohs.popmovies.interceptor.MovieCollectionInterceptorImpl;
import br.com.tiagohs.popmovies.model.movie.CollectionDetails;
import br.com.tiagohs.popmovies.model.db.MovieDB;
import br.com.tiagohs.popmovies.model.dto.MovieListDTO;
import br.com.tiagohs.popmovies.model.movie.MovieDetails;
import br.com.tiagohs.popmovies.model.response.RankingResponse;
import br.com.tiagohs.popmovies.server.ResponseListener;
import br.com.tiagohs.popmovies.server.methods.MoviesServer;
import br.com.tiagohs.popmovies.util.DTOUtils;
import br.com.tiagohs.popmovies.util.MovieUtils;
import br.com.tiagohs.popmovies.view.MoviesDetailsOverviewView;

public class MovieDetailsOverviewPresenterImpl implements MovieDetailsOverviewPresenter, ResponseListener<RankingResponse>, MovieCollectionInterceptor.onMovieCollectionListener {
    private static final String TAG = MovieDetailsOverviewPresenterImpl.class.getSimpleName();

    private MoviesServer mMoviesServer;
    private MovieCollectionInterceptor mMovieCollectionInterceptor;

    private MoviesDetailsOverviewView mMoviesDetailsOverviewView;
    private MovieRepository mMovieRepository;

    private long mProfileID;

    public MovieDetailsOverviewPresenterImpl() {
        mMoviesServer = new MoviesServer();
        mMovieCollectionInterceptor = new MovieCollectionInterceptorImpl(this);
    }

    @Override
    public void getMoviesRankings(String imdbID, String tag) {
        mMoviesDetailsOverviewView.setRankingProgressVisibility(View.VISIBLE);

        if (mMoviesDetailsOverviewView.isInternetConnected()) {
            mMoviesServer.getMovieRankings(imdbID, tag, this);
        } else {
            noConnectionError();
        }

    }

    @Override
    public void getMovieCollections(int collectionID, String tag) {

        if (mMoviesDetailsOverviewView.isInternetConnected()) {
            mMovieCollectionInterceptor.getMovieCollections(tag, collectionID);
        } else {
            noConnectionError();
        }
    }


    public void setMovieRepository(MovieRepository movieRepository) {
        this.mMovieRepository = movieRepository;
    }

    public void setProfileID(long profileID) {
        this.mProfileID = profileID;
    }

    private void noConnectionError() {
        if (mMoviesDetailsOverviewView.isAdded()) {
            mMoviesDetailsOverviewView.onErrorNoConnection();
            mMoviesDetailsOverviewView.setRankingProgressVisibility(View.GONE);
        }

    }

    @Override
    public void setView(MoviesDetailsOverviewView view) {
        mMoviesDetailsOverviewView = view;
    }

    @Override
    public void onClickWantSee(MovieDetails movie, boolean buttonStage) {

        if (buttonStage)
            mMovieRepository.saveMovie(new MovieDB(movie.getId(), MovieDB.STATUS_WANT_SEE, movie.getRuntime(), movie.getPosterPath(),
                    movie.getTitle(), movie.isFavorite(), movie.getVoteCount(), mProfileID,
                    Calendar.getInstance(), MovieUtils.formateStringToCalendar(movie.getReleaseDate()),
                    MovieUtils.getYearByDate(movie.getReleaseDate()), MovieUtils.genreToGenreDB(movie.getGenres())));
        else
            mMovieRepository.deleteMovieByServerID(movie.getId(), mProfileID);

    }

    @Override
    public void onClickDontWantSee(MovieDetails movie, boolean buttonStage) {

        if (buttonStage)
            mMovieRepository.saveMovie(new MovieDB(movie.getId(), MovieDB.STATUS_DONT_WANT_SEE, movie.getRuntime(), movie.getPosterPath(),
                    movie.getTitle(), movie.isFavorite(), movie.getVoteCount(), mProfileID,
                    Calendar.getInstance(), MovieUtils.formateStringToCalendar(movie.getReleaseDate()),
                    MovieUtils.getYearByDate(movie.getReleaseDate()), MovieUtils.genreToGenreDB(movie.getGenres())));
        else
            mMovieRepository.deleteMovieByServerID(movie.getId(), mProfileID);

    }

    @Override
    public void onErrorResponse(VolleyError error) {

        if (mMoviesDetailsOverviewView.isAdded()) {
            mMoviesDetailsOverviewView.onErrorGetRankings();
            mMoviesDetailsOverviewView.setRankingProgressVisibility(View.GONE);
            mMoviesDetailsOverviewView.setRankingContainerVisibility(View.GONE);
            mMoviesDetailsOverviewView.setTomatoesConsensusContainerVisibility(View.GONE);
            mMoviesDetailsOverviewView.setTomatoesReviewsVisibility(View.GONE);
        }
    }

    public void setMovieFavorite(MovieDetails movie) {
        mMovieRepository.saveMovie(new MovieDB(movie.getId(), MovieDB.STATUS_WATCHED, movie.getRuntime(), movie.getPosterPath(),
                movie.getTitle(), movie.isFavorite(), movie.getVoteCount(), mProfileID,
                Calendar.getInstance(), MovieUtils.formateStringToCalendar(movie.getReleaseDate()),
                MovieUtils.getYearByDate(movie.getReleaseDate()), MovieUtils.genreToGenreDB(movie.getGenres())));
    }

    @Override
    public void onResponse(RankingResponse response) {
        mMoviesDetailsOverviewView.setRankingContainerVisibility(View.VISIBLE);

        if (mMoviesDetailsOverviewView.isAdded()) {
            mMoviesDetailsOverviewView.setMovieRankings(response);

            if (isEmpty(response.getImdbRanting()))
                mMoviesDetailsOverviewView.setImdbRakingContainerVisibility(View.GONE);
            else
                mMoviesDetailsOverviewView.updateIMDB(response.getImdbRanting(), response.getImdbVotes());

            if (isEmpty(response.getTomatoRating())) {
                mMoviesDetailsOverviewView.setTomatoesRakingContainerVisibility(View.GONE);
                mMoviesDetailsOverviewView.setTomatoesReviewsVisibility(View.GONE);
            } else
                mMoviesDetailsOverviewView.updateTomatoes(response.getTomatoRating(), response.getTomatoReviews());

            if (isEmpty(response.getMetascoreRating()))
                mMoviesDetailsOverviewView.setMetascoreRakingContainerVisibility(View.GONE);
            else
                mMoviesDetailsOverviewView.updateMetascore(response.getMetascoreRating());

            if (isEmpty(response.getTomatoConsensus()))
                mMoviesDetailsOverviewView.setTomatoesConsensusContainerVisibility(View.GONE);
            else {
                mMoviesDetailsOverviewView.setTomatoesConsensusContainerVisibility(View.VISIBLE);
                mMoviesDetailsOverviewView.updateTomatoesConsensus(response.getTomatoConsensus());
            }


            if (response.getTomatoURL() == null)
                mMoviesDetailsOverviewView.setTomatoesRakingContainerVisibility(View.GONE);

            if (isEmpty(response.getImdbRanting()) && isEmpty(response.getTomatoRating()) && isEmpty(response.getMetascoreRating())) {
                mMoviesDetailsOverviewView.setRankingContainerVisibility(View.GONE);
            }

            mMoviesDetailsOverviewView.setRankingProgressVisibility(View.GONE);
            mMoviesDetailsOverviewView.updateNomeacoes(response.getAwards());
        }

    }

    public boolean isEmpty(String value) {
        return value == null || value.equals("N/A");
    }

    public List<MovieListDTO> getSimilaresMovies(List<MovieDetails> movies) {
        return DTOUtils.createMovieDetailsListDTO(movies);
    }

    @Override
    public void onMovieCollectionRequestSucess(CollectionDetails response) {
        mMoviesDetailsOverviewView.setCollections(response.getMovies());
    }

    @Override
    public void onMovieCollectionRequestError(VolleyError error) {
        mMoviesDetailsOverviewView.setCollectionsVisibility(View.GONE);
    }
}
