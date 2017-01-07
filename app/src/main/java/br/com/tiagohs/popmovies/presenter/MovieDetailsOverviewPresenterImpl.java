package br.com.tiagohs.popmovies.presenter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;

import com.android.volley.VolleyError;

import java.util.Calendar;
import java.util.List;

import br.com.tiagohs.popmovies.App;
import br.com.tiagohs.popmovies.R;
import br.com.tiagohs.popmovies.data.repository.MovieRepository;
import br.com.tiagohs.popmovies.model.db.MovieDB;
import br.com.tiagohs.popmovies.model.dto.MovieListDTO;
import br.com.tiagohs.popmovies.model.movie.MovieDetails;
import br.com.tiagohs.popmovies.model.response.RankingResponse;
import br.com.tiagohs.popmovies.server.ResponseListener;
import br.com.tiagohs.popmovies.server.methods.MoviesServer;
import br.com.tiagohs.popmovies.util.DTOUtils;
import br.com.tiagohs.popmovies.util.MovieUtils;
import br.com.tiagohs.popmovies.util.PrefsUtils;
import br.com.tiagohs.popmovies.util.ViewUtils;
import br.com.tiagohs.popmovies.view.MoviesDetailsOverviewView;

public class MovieDetailsOverviewPresenterImpl implements MovieDetailsOverviewPresenter, ResponseListener<RankingResponse> {
    private static final String TAG = MovieDetailsOverviewPresenterImpl.class.getSimpleName();

    private MoviesServer mMoviesServer;
    private MoviesDetailsOverviewView mMoviesDetailsOverviewView;
    private MovieRepository mMovieRepository;
    private Context mContext;

    public MovieDetailsOverviewPresenterImpl() {
        mMoviesServer = new MoviesServer();
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

    private void noConnectionError() {
        if (mMoviesDetailsOverviewView.isAdded()) {
            mMoviesDetailsOverviewView.onError("Sem Conexao");
            mMoviesDetailsOverviewView.setRankingProgressVisibility(View.GONE);
        }

    }

    @Override
    public void setView(MoviesDetailsOverviewView view) {
        mMoviesDetailsOverviewView = view;
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
    public void onErrorResponse(VolleyError error) {

        Log.i(TAG, "onError");

        if (mMoviesDetailsOverviewView.isAdded()) {
            mMoviesDetailsOverviewView.setRankingProgressVisibility(View.GONE);
            mMoviesDetailsOverviewView.setRankingContainerVisibility(View.GONE);
            mMoviesDetailsOverviewView.setTomatoesConsensusContainerVisibility(View.GONE);
            mMoviesDetailsOverviewView.setTomatoesReviewsVisibility(View.GONE);
        }
    }

    public void setMovieFavorite(MovieDetails movie) {
        mMovieRepository.saveMovie(new MovieDB(movie.getId(), MovieDB.STATUS_WATCHED, movie.getRuntime(), movie.getPosterPath(),
                movie.getTitle(), movie.isFavorite(), movie.getVoteCount(), PrefsUtils.getCurrentProfile(mContext).getProfileID(),
                Calendar.getInstance(), MovieUtils.formateStringToCalendar(movie.getReleaseDate()),
                MovieUtils.getYearByDate(movie.getReleaseDate()), MovieUtils.genreToGenreDB(movie.getGenres())));
    }

    @Override
    public void onResponse(RankingResponse response) {

        if (mMoviesDetailsOverviewView.isAdded()) {
            mMoviesDetailsOverviewView.setMovieRankings(response);

            if (response.getImdbRanting() == null || response.getImdbRanting().equals("N/A"))
                mMoviesDetailsOverviewView.setImdbRakingContainerVisibility(View.GONE);
            else
                mMoviesDetailsOverviewView.updateIMDB(response.getImdbRanting(), response.getImdbVotes());

            if (response.getTomatoRating() == null || response.getTomatoRating().equals("N/A")) {
                mMoviesDetailsOverviewView.setTomatoesRakingContainerVisibility(View.GONE);
                mMoviesDetailsOverviewView.setTomatoesReviewsVisibility(View.GONE);
            } else
                mMoviesDetailsOverviewView.updateTomatoes(response.getTomatoRating(), response.getTomatoReviews());

            if (response.getMetascoreRating() == null || response.getMetascoreRating().equals("N/A"))
                mMoviesDetailsOverviewView.setMetascoreRakingContainerVisibility(View.GONE);
            else
                mMoviesDetailsOverviewView.updateMetascore(response.getMetascoreRating());

            if (response.getTomatoConsensus() == null || response.getTomatoConsensus().equals("N/A"))
                mMoviesDetailsOverviewView.setTomatoesConsensusContainerVisibility(View.GONE);
            else
                mMoviesDetailsOverviewView.updateTomatoesConsensus(response.getTomatoConsensus());

            if (response.getTomatoURL() == null)
                mMoviesDetailsOverviewView.setTomatoesRakingContainerVisibility(View.GONE);

            mMoviesDetailsOverviewView.setRankingProgressVisibility(View.GONE);
            mMoviesDetailsOverviewView.setRankingContainerVisibility(View.VISIBLE);
            mMoviesDetailsOverviewView.updateNomeacoes(ViewUtils.isEmptyValue(response.getAwards()) ? mContext.getString(R.string.nao_disponivel) : response.getAwards());
        }

    }

    public List<MovieListDTO> getSimilaresMovies(List<MovieDetails> movies) {
        return DTOUtils.createMovieDetailsListDTO(movies);
    }
}
