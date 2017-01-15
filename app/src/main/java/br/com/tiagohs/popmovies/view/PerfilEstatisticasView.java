package br.com.tiagohs.popmovies.view;

/**
 * Created by Tiago on 15/01/2017.
 */

public interface PerfilEstatisticasView extends BaseView {

    void setTotalHorasAssistidas(long duracaoTotal);
    void setTotalFilmesAssistidos(int totalFilmesAssistidos);
}
