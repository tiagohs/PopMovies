package br.com.tiagohs.popmovies.ui.contracts;

import java.util.List;

import br.com.tiagohs.popmovies.model.db.MovieDB;
import br.com.tiagohs.popmovies.model.dto.MovieListDTO;
import br.com.tiagohs.popmovies.model.movie.Movie;
import br.com.tiagohs.popmovies.model.movie.MovieDetails;
import br.com.tiagohs.popmovies.model.response.RankingResponse;
import br.com.tiagohs.popmovies.ui.presenter.IPresenter;
import br.com.tiagohs.popmovies.ui.view.IView;
import io.reactivex.Observable;

public class MovieDetailsOverviewContract {

    public interface MovieDetailsOverviewInterceptor {

        Observable<RankingResponse> getMovieRankings(String imdbID);
        Observable<List<Movie>> getMovieCollections(int collectionID);

        Observable<Long> saveMovie(MovieDB movie);
        Observable<Integer> deleteMovieByServerID(long serverID, long profileID);

    }

    public interface MovieDetailsOverviewPresenter extends IPresenter<MoviesDetailsOverviewView> {

        void getMoviesRankings(String imdbID);
        void getMovieCollections(int collectionID);

        List<MovieListDTO> getSimilaresMovies(List<MovieDetails> movies);

        void setProfileID(long profileID);
        void setMovieFavorite(MovieDetails movie);

        void onClickWantSee(MovieDetails movie, boolean buttonStage);
        void onClickDontWantSee(MovieDetails movie, boolean buttonStage);
    }

    public interface MoviesDetailsOverviewView extends IView {

        void setMovie(MovieDetails movie);
        void setMovieRankings(RankingResponse movieRankings);

        void updateIMDB(String ranking, String votes);
        void updateTomatoes(String ranking, String votes);
        void updateMetascore(String ranking);
        void updateTomatoesConsensus(String tomatoConsensus);
        void updateNomeacoes(String nomeacoes);

        void setImdbRakingContainerVisibility(int visibilityState);
        void setTomatoesRakingContainerVisibility(int visibilityState);
        void setMetascoreRakingContainerVisibility(int visibilityState);
        void setTomatoesConsensusContainerVisibility(int visibilityState);
        void setSimilaresVisibility(int visibilityState);

        void setRankingProgressVisibility(int visibility);
        void setRankingContainerVisibility(int visibility);
        void setTomatoesReviewsVisibility(int visibility);

        void setCollections(List<Movie> movies);

        void setCollectionsVisibility(int visibility);

        void onErrorGetRankings();
    }
}
