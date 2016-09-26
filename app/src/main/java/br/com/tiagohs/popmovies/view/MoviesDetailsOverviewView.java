package br.com.tiagohs.popmovies.view;

import br.com.tiagohs.popmovies.model.movie.MovieDetails;
import br.com.tiagohs.popmovies.model.response.RankingResponse;

public interface MoviesDetailsOverviewView extends BaseView {

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
    boolean isAdded();


}
