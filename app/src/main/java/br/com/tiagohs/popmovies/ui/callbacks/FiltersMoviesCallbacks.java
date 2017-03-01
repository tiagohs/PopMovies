package br.com.tiagohs.popmovies.ui.callbacks;

import br.com.tiagohs.popmovies.model.dto.FilterValuesDTO;

public interface FiltersMoviesCallbacks {

    void onFilterChanged(FilterValuesDTO filters);
    void onFilterReset();
}
