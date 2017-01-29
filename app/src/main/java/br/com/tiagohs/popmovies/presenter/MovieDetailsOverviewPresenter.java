package br.com.tiagohs.popmovies.presenter;

import android.content.Context;

import java.util.List;

import br.com.tiagohs.popmovies.data.repository.MovieRepository;
import br.com.tiagohs.popmovies.model.dto.MovieListDTO;
import br.com.tiagohs.popmovies.model.movie.MovieDetails;
import br.com.tiagohs.popmovies.view.MoviesDetailsOverviewView;

public interface MovieDetailsOverviewPresenter extends BasePresenter<MoviesDetailsOverviewView> {

    void getMoviesRankings(String imdbID, String tag);
    List<MovieListDTO> getSimilaresMovies(List<MovieDetails> movies);
    void setMovieRepository(MovieRepository movieRepository);
    void setProfileID(long profileID);
    void setMovieFavorite(MovieDetails movie);
    void onClickWantSee(MovieDetails movie, boolean buttonStage);
    void onClickDontWantSee(MovieDetails movie, boolean buttonStage);
}
