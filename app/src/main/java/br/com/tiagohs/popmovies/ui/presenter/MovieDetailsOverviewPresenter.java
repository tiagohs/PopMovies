package br.com.tiagohs.popmovies.ui.presenter;

import android.view.View;

import java.util.Calendar;
import java.util.List;

import br.com.tiagohs.popmovies.model.Ranking;
import br.com.tiagohs.popmovies.model.db.MovieDB;
import br.com.tiagohs.popmovies.model.dto.MovieListDTO;
import br.com.tiagohs.popmovies.model.movie.Movie;
import br.com.tiagohs.popmovies.model.movie.MovieDetails;
import br.com.tiagohs.popmovies.model.response.RankingResponse;
import br.com.tiagohs.popmovies.ui.contracts.MovieDetailsOverviewContract;
import br.com.tiagohs.popmovies.util.DTOUtils;
import br.com.tiagohs.popmovies.util.DateUtils;
import br.com.tiagohs.popmovies.util.MovieUtils;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class MovieDetailsOverviewPresenter extends BasePresenter<MovieDetailsOverviewContract.MoviesDetailsOverviewView, MovieDetailsOverviewContract.MovieDetailsOverviewInterceptor> implements MovieDetailsOverviewContract.MovieDetailsOverviewPresenter {
    private static final String TAG = MovieDetailsOverviewPresenter.class.getSimpleName();

    private long mProfileID;

    public MovieDetailsOverviewPresenter(MovieDetailsOverviewContract.MovieDetailsOverviewInterceptor movieDetailsOverviewInterceptor, CompositeDisposable subscribes) {
        super(movieDetailsOverviewInterceptor, subscribes);
    }

    @Override
    public void getMoviesRankings(String imdbID) {
        mView.setRankingProgressVisibility(View.VISIBLE);

        if (mView.isInternetConnected()) {
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
                mSubscribers.add(d);
            }

            @Override
            public void onNext(RankingResponse rankings) {
                onResponse(rankings);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                mView.onErrorGetRankings();
                mView.setRankingProgressVisibility(View.GONE);
                mView.setRankingContainerVisibility(View.GONE);
                mView.setTomatoesConsensusContainerVisibility(View.GONE);
                mView.setTomatoesReviewsVisibility(View.GONE);
            }

            @Override
            public void onComplete() {

            }
        };
    }

    public void onResponse(RankingResponse response) {

        if (mView.isAdded()) {
            mView.setRankingContainerVisibility(View.VISIBLE);

            mView.setMovieRankings(response);

            if (isEmpty(response.getImdbRanting()))
                mView.setImdbRakingContainerVisibility(View.GONE);
            else
                mView.updateIMDB(response.getImdbRanting(), response.getImdbVotes());

            for(Ranking ranking : response.getRatings()) {
                if (ranking.getSource().equals("Rotten Tomatoes")) {
                    if (isEmpty(ranking.getValue())) {
                        mView.setTomatoesRakingContainerVisibility(View.GONE);
                        mView.setTomatoesReviewsVisibility(View.GONE);
                    } else {
                        mView.updateTomatoes(ranking.getValue(), response.getTomatoReviews());
                    }

                }
            }

            if (isEmpty(response.getMetascoreRating()))
                mView.setMetascoreRakingContainerVisibility(View.GONE);
            else
                mView.updateMetascore(response.getMetascoreRating());

            if (isEmpty(response.getTomatoConsensus()))
                mView.setTomatoesConsensusContainerVisibility(View.GONE);
            else {
                mView.setTomatoesConsensusContainerVisibility(View.VISIBLE);
                mView.updateTomatoesConsensus(response.getTomatoConsensus());
            }

            if (response.getTomatoURL() == null)
                mView.setTomatoesRakingContainerVisibility(View.GONE);
            else
                mView.setTomatoesReviewsVisibility(View.VISIBLE);

            if (isEmpty(response.getImdbRanting()) && isEmpty(response.getTomatoRating()) && isEmpty(response.getMetascoreRating())) {
                mView.setRankingContainerVisibility(View.GONE);
            }

            mView.setRankingProgressVisibility(View.GONE);
            mView.updateNomeacoes(response.getAwards());
        }

    }

    @Override
    public void getMovieCollections(int collectionID) {

        if (mView.isInternetConnected()) {
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
                mSubscribers.add(d);
            }

            @Override
            public void onNext(List<Movie> movies) {
                mView.setCollections(movies);
            }

            @Override
            public void onError(Throwable e) {
                mView.onErrorInServer();
                mView.setCollectionsVisibility(View.GONE);
            }

            @Override
            public void onComplete() {}
        };
    }

    public void setProfileID(long profileID) {
        this.mProfileID = profileID;
    }

    @Override
    public void onClickWantSee(MovieDetails movie, boolean buttonStage) {

        if (buttonStage) {
            mSubscribers.add(mInterceptor.saveMovie(new MovieDB(movie.getId(), MovieDB.STATUS_WANT_SEE, movie.getRuntime(), movie.getPosterPath(),
                                    movie.getTitle(), movie.isFavorite(), movie.getVoteCount(), mProfileID,
                                    Calendar.getInstance(), DateUtils.formateStringToCalendar(movie.getReleaseDate()),
                                    DateUtils.getYearByDate(movie.getReleaseDate()), MovieUtils.genreToGenreDB(movie.getGenres())))
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe());
        } else {
            mSubscribers.add(mInterceptor.deleteMovieByServerID(movie.getId(), mProfileID)
                       .observeOn(AndroidSchedulers.mainThread())
                       .subscribe());
        }

    }

    @Override
    public void onClickDontWantSee(MovieDetails movie, boolean buttonStage) {

        if (buttonStage) {
            mSubscribers.add(mInterceptor.saveMovie(new MovieDB(movie.getId(), MovieDB.STATUS_DONT_WANT_SEE, movie.getRuntime(), movie.getPosterPath(),
                                    movie.getTitle(), movie.isFavorite(), movie.getVoteCount(), mProfileID,
                                    Calendar.getInstance(), DateUtils.formateStringToCalendar(movie.getReleaseDate()),
                                    DateUtils.getYearByDate(movie.getReleaseDate()), MovieUtils.genreToGenreDB(movie.getGenres())))
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe());
        } else {
            mSubscribers.add(mInterceptor.deleteMovieByServerID(movie.getId(), mProfileID)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe());
        }

    }

    public void setMovieFavorite(MovieDetails movie) {
        mSubscribers.add(mInterceptor.saveMovie(new MovieDB(movie.getId(), MovieDB.STATUS_WATCHED, movie.getRuntime(), movie.getPosterPath(),
                                movie.getTitle(), movie.isFavorite(), movie.getVoteCount(), mProfileID,
                                Calendar.getInstance(), DateUtils.formateStringToCalendar(movie.getReleaseDate()),
                                DateUtils.getYearByDate(movie.getReleaseDate()), MovieUtils.genreToGenreDB(movie.getGenres())))
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
