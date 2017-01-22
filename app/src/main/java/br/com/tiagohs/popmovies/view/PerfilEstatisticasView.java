package br.com.tiagohs.popmovies.view;

import java.util.Calendar;
import java.util.List;

import br.com.tiagohs.popmovies.model.dto.GenrerMoviesDTO;

/**
 * Created by Tiago on 15/01/2017.
 */

public interface PerfilEstatisticasView extends BaseView {

    void setTotalHorasAssistidas(long duracaoTotal);
    void setTotalFilmesAssistidos(int totalFilmesAssistidos);
    void configurateGraphic(List<GenrerMoviesDTO> genres);
    void setTotalsMovies(long totalWatched, long totalFavorite, long totalWantSee, long totalDontWantSee);
    void setDadosPessoais(String pais, Calendar birthday, String genero);
    void setDescricao(String descricao);
}
