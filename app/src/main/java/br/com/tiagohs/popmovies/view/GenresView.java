package br.com.tiagohs.popmovies.view;

import java.util.List;

import br.com.tiagohs.popmovies.model.movie.Genre;

public interface GenresView extends BaseView{

    void updateView(List<Genre> genres);
}
