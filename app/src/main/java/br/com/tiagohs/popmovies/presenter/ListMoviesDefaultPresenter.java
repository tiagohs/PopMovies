package br.com.tiagohs.popmovies.presenter;

import java.util.Map;

import br.com.tiagohs.popmovies.util.enumerations.Sort;
import br.com.tiagohs.popmovies.view.ListMoviesDefaultView;

public interface ListMoviesDefaultPresenter extends BasePresenter<ListMoviesDefaultView> {

    void getMovies(int id, Sort typeList, String tag, Map<String, String> parameters);
}
