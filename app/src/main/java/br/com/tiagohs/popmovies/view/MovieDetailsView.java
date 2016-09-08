package br.com.tiagohs.popmovies.view;

import java.util.List;

import br.com.tiagohs.popmovies.model.dto.ItemListDTO;
import br.com.tiagohs.popmovies.model.movie.MovieDetails;
import br.com.tiagohs.popmovies.model.response.VideosResponse;

/**
 * Created by Tiago Henrique on 25/08/2016.
 */
public interface MovieDetailsView extends BaseView {

    void setupDirectorsRecyclerView(List<ItemListDTO> list);
    void setupTabs();

    void updateUI(MovieDetails movie);
    void updateVideos(VideosResponse videos);

    void setTabsVisibility(int visibilityState);
    void setPlayButtonVisibility(int visibilityState);
}
