package br.com.tiagohs.popmovies.view.callbacks;

import br.com.tiagohs.popmovies.model.dto.FilterValuesDTO;

public interface FiltersMoviesCallbacks {

    void onFilterChanged(FilterValuesDTO filters);
}
