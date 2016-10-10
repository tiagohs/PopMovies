package br.com.tiagohs.popmovies.view;

import java.util.List;

import br.com.tiagohs.popmovies.model.dto.PersonListDTO;

/**
 * Created by Tiago Henrique on 09/10/2016.
 */

public interface ListPersonsDefaultView extends BaseView {

    void setProgressVisibility(int visibityState);
    void setRecyclerViewVisibility(int visibilityState);

    void setupRecyclerView();
    void setListMovies(List<PersonListDTO> listPersons, boolean hasMorePages);
    void addAllMovies(List<PersonListDTO> listPersons, boolean hasMorePages);
    void updateAdapter();
}
