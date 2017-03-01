package br.com.tiagohs.popmovies.ui.presenter;

import android.view.View;

import java.util.Calendar;
import java.util.List;

import br.com.tiagohs.popmovies.ui.contracts.MovieDetailsOverviewContract;
import br.com.tiagohs.popmovies.model.db.MovieDB;
import br.com.tiagohs.popmovies.model.dto.MovieListDTO;
import br.com.tiagohs.popmovies.model.movie.Movie;
import br.com.tiagohs.popmovies.model.movie.MovieDetails;
import br.com.tiagohs.popmovies.model.response.RankingResponse;
import br.com.tiagohs.popmovies.util.DTOUtils;
import br.com.tiagohs.popmovies.util.MovieUtils;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class MovieDetailsOverviewPresenter implements MovieDetailsOverviewContract.MovieDetailsOverviewPresenter {
    private static final String TAG = MovieDetailsOverviewPresenter.class.getSimpleName();

    private MovieDetailsOverviewContract.MovieDetailsOverviewInterceptor mInterceptor;
    private MovieDetailsOverviewContract.MoviesDetailsOverviewView mMoviesDetailsOverviewView;

    private CompositeDisposable mSubscribes;

    private long mProfileID;

    public MovieDetailsOverviewPresenter(MovieDetailsOverviewContract.MovieDetailsOverviewInterceptor movieDetailsOverviewInterceptor, CompositeDisposable subscribes) {
        mInterceptor = movieDetailsOverviewInterceptor;
        mSubscribes = subscribes;
    }

    @Override
    public void onBindView(MovieDetailsOverviewContract.MoviesDetailsOverviewView view) {
        mMoviesDetailsOverviewView = view;
    }

    @Override
    public void onUnbindView() {
        mSubscribes.clear();
        mMoviesDetailsOverviewView = null;
    }

    @Override
    public void getMoviesRankings(String imdbID) {
        mMoviesDetailsOverviewView.setRankingProgressVisibility(View.VISIBLE);

        if (mMoviesDetailsOverviewView.isInternetConnected()) {
            mInterceptor.getMovieRankings(imdbID)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(onRankingsObserver());
        } else {
            noConnectionError();
        }

    }

    private Observer<RankingResponse> onRankingsObserver() {
        return new Observer<RankingResponse>() {
            @Override
            public void onSubscribe(Disposable d) {
                mSubscribes.add(d);
            }

            @Override
            public void onNext(RankingResponse rankings) {
                onResponse(rankings);
            }

            @Override
            public void onError(Throwable e) {
                mMoviesDetailsOverviewView.onErrorGetRankings();
                mMoviesDetailsOverviewView.setRankingProgressVisibility(View.GONE);
                mMoviesDetailsOverviewView.setRankingContainerVisibility(View.GONE);
                mMoviesDetailsOverviewView.setTomatoesConsensusContainerVisibility(View.GONE);
                mMoviesDetailsOverviewView.setTomatoesReviewsVisibility(View.GONE);
            }

            @Override
            public void onComplete() {

            }
        };
    }

    public void onResponse(RankingResponse response) {

        if (mMoviesDetailsOverviewView.isAdded()) {
            mMoviesDetailsOverviewView.setRankingContainerVisibility(View.VISIBLE);

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
            else
                mMoviesDetailsOverviewView.setTomatoesReviewsVisibility(View.VISIBLE);

            if (isEmpty(response.getImdbRanting()) && isEmpty(response.getTomatoRating()) && isEmpty(response.getMetascoreRating())) {
                mMoviesDetailsOverviewView.setRankingContainerVisibility(View.GONE);
            }

            mMoviesDetailsOverviewView.setRankingProgressVisibility(View.GONE);
            mMoviesDetailsOverviewView.updateNomeacoes(response.getAwards());
        }

    }

    @Override
    public void getMovieCollections(int collectionID) {

        if (mMoviesDetailsOverviewView.isInternetConnected()) {
            mInterceptor.getMovieCollections(collectionID)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(onCollectionsObserver());

        } else {
            noConnectionError();
        }
    }

    private Observer<List<Movie>> onCollectionsObserver() {
        return new Observer<List<Movie>>() {
            @Override
            public void onSubscribe(Disposable d) {
                mSubscribes.add(d);
            }

            @Override
            public void onNext(List<Movie> movies) {
                mMoviesDetailsOverviewView.setCollections(movies);
            }

            @Override
            public void onError(Throwable e) {
                mMoviesDetailsOverviewView.onErrorInServer();
                mMoviesDetailsOverviewView.setCollectionsVisibility(View.GONE);
            }

            @Override
            public void onComplete() {

            }
        };
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
    public void onClickWantSee(MovieDetails movie, boolean buttonStage) {

        if (buttonStage) {
            mSubscribes.add(mInterceptor.saveMovie(new MovieDB(movie.getId(), MovieDB.STATUS_WANT_SEE, movie.getRuntime(), movie.getPosterPath(),
                                    movie.getTitle(), movie.isFavorite(), movie.getVoteCount(), mProfileID,
                                    Calendar.getInstance(), MovieUtils.formateStringToCalendar(movie.getReleaseDate()),
                                    MovieUtils.getYearByDate(movie.getReleaseDate()), MovieUtils.genreToGenreDB(movie.getGenres())))
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe());
        } else {
            mSubscribes.add(mInterceptor.deleteMovieByServerID(movie.getId(), mProfileID)
                       .observeOn(AndroidSchedulers.mainThread())
                       .subscribe());
        }

    }

    @Override
    public void onClickDontWantSee(MovieDetails movie, boolean buttonStage) {

        if (buttonStage) {
            mSubscribes.add(mInterceptor.saveMovie(new MovieDB(movie.getId(), MovieDB.STATUS_DONT_WANT_SEE, movie.getRuntime(), movie.getPosterPath(),
                                    movie.getTitle(), movie.isFavorite(), movie.getVoteCount(), mProfileID,
                                    Calendar.getInstance(), MovieUtils.formateStringToCalendar(movie.getReleaseDate()),
                                    MovieUtils.getYearByDate(movie.getReleaseDate()), MovieUtils.genreToGenreDB(movie.getGenres())))
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe());
        } else {
            mSubscribes.add(mInterceptor.deleteMovieByServerID(movie.getId(), mProfileID)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe());
        }

    }

    public void setMovieFavorite(MovieDetails movie) {
        mSubscribes.add(mInterceptor.saveMovie(new MovieDB(movie.getId(), MovieDB.STATUS_WATCHED, movie.getRuntime(), movie.getPosterPath(),
                                movie.getTitle(), movie.isFavorite(), movie.getVoteCount(), mProfileID,
                                Calendar.getInstance(), MovieUtils.formateStringToCalendar(movie.getReleaseDate()),
                                MovieUtils.getYearByDate(movie.getReleaseDate()), MovieUtils.genreToGenreDB(movie.getGenres())))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe());
    }



    public boolean isEmpty(String value) {
        return value == null || value.equals("N/A");
    }

    public List<MovieListDTO> getSimilaresMovies(List<MovieDetails> movies) {
        return DTOUtils.createMovieDetailsListDTO(movies);
    }

}
