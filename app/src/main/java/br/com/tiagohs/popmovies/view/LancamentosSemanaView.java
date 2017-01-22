package br.com.tiagohs.popmovies.view;

import java.util.Map;

/**
 * Created by Tiago on 22/01/2017.
 */

public interface LancamentosSemanaView extends BaseView {

    void updateListMovies(Map<String, String> lancamentosParameters);
    void updateDateText(String dateMin, String dateMax);
}
