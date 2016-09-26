package br.com.tiagohs.popmovies.presenter;

import android.view.View;

import com.android.volley.VolleyError;

import br.com.tiagohs.popmovies.model.response.RankingResponse;
import br.com.tiagohs.popmovies.server.PopMovieServer;
import br.com.tiagohs.popmovies.server.ResponseListener;
import br.com.tiagohs.popmovies.view.MoviesDetailsOverviewView;

public class MovieDetailsOverviewPresenterImpl implements MovieDetailsOverviewPresenter, ResponseListener<RankingResponse> {

    private PopMovieServer mPopMovieServer;
    private MoviesDetailsOverviewView mMoviesDetailsOverviewView;

    public MovieDetailsOverviewPresenterImpl() {
        mPopMovieServer = PopMovieServer.getInstance();
    }

    @Override
    public void getMoviesRankings(String imdbID) {
        mPopMovieServer.getMovieRankings(imdbID, this);
    }

    @Override
    public void setView(MoviesDetailsOverviewView view) {
        mMoviesDetailsOverviewView = view;
    }

    @Override
    public void onErrorResponse(VolleyError error) {

        if (mMoviesDetailsOverviewView.isAdded()) {
            mMoviesDetailsOverviewView.setProgressVisibility(View.GONE);
            mMoviesDetailsOverviewView.setRankingContainerVisibility(View.GONE);
            mMoviesDetailsOverviewView.setTomatoesConsensusContainerVisibility(View.GONE);
        }
    }

    @Override
    public void onResponse(RankingResponse response) {

        if (mMoviesDetailsOverviewView.isAdded()) {
            mMoviesDetailsOverviewView.setMovieRankings(response);

            if (response.getImdbRanting() == null || response.getImdbRanting().equals("N/A"))
                mMoviesDetailsOverviewView.setImdbRakingContainerVisibility(View.GONE);
            else
                mMoviesDetailsOverviewView.updateIMDB(response.getImdbRanting(), response.getImdbVotes());

            if (response.getTomatoRating() == null || response.getTomatoRating().equals("N/A"))
                mMoviesDetailsOverviewView.setTomatoesRakingContainerVisibility(View.GONE);
            else
                mMoviesDetailsOverviewView.updateTomatoes(response.getTomatoRating(), response.getTomatoReviews());

            if (response.getMetascoreRating() == null || response.getMetascoreRating().equals("N/A"))
                mMoviesDetailsOverviewView.setMetascoreRakingContainerVisibility(View.GONE);
            else
                mMoviesDetailsOverviewView.updateMetascore(response.getMetascoreRating());

            if (response.getTomatoConsensus() == null || response.getTomatoConsensus().equals("N/A"))
                mMoviesDetailsOverviewView.setTomatoesConsensusContainerVisibility(View.GONE);
            else
                mMoviesDetailsOverviewView.updateTomatoesConsensus(response.getTomatoConsensus());

            mMoviesDetailsOverviewView.setRankingProgressVisibility(View.GONE);
            mMoviesDetailsOverviewView.setRankingContainerVisibility(View.VISIBLE);
            mMoviesDetailsOverviewView.updateNomeacoes(response.getAwards());
        }

    }
}
