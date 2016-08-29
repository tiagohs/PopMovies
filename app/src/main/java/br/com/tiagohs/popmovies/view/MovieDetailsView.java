package br.com.tiagohs.popmovies.view;

import br.com.tiagohs.popmovies.model.Movie.MovieDetails;
import br.com.tiagohs.popmovies.model.response.VideosResponse;

/**
 * Created by Tiago Henrique on 25/08/2016.
 */
public interface MovieDetailsView {

    void updateUI(MovieDetails movie);
    void updateVideos(VideosResponse videos);
}
