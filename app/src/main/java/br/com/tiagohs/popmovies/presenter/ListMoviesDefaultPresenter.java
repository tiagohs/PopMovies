package br.com.tiagohs.popmovies.presenter;

import android.content.Context;

import java.util.List;
import java.util.Map;

import br.com.tiagohs.popmovies.model.dto.MovieListDTO;
import br.com.tiagohs.popmovies.model.movie.MovieDetails;
import br.com.tiagohs.popmovies.util.enumerations.Sort;
import br.com.tiagohs.popmovies.view.ListMoviesDefaultView;

public interface ListMoviesDefaultPresenter extends BasePresenter<ListMoviesDefaultView> {

    void getMovies(int id, Sort typeList, String tag, Map<String, String> parameters);
<<<<<<< HEAD
    void setContext(Context context);

=======
>>>>>>> origin/master
}
