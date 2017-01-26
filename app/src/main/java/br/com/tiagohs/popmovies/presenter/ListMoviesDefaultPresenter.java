package br.com.tiagohs.popmovies.presenter;

import android.content.Context;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.List;
import java.util.Map;

import br.com.tiagohs.popmovies.model.dto.MovieListDTO;
import br.com.tiagohs.popmovies.model.movie.MovieDetails;
import br.com.tiagohs.popmovies.util.enumerations.Sort;
import br.com.tiagohs.popmovies.view.ListMoviesDefaultView;

public interface ListMoviesDefaultPresenter extends BasePresenter<ListMoviesDefaultView> {

    void getMovies(int id, Sort typeList, String tag, Map<String, String> parameters);
    void setContext(Context context);
    void resetValues();
    void getMovieDetails(int movieID, boolean isSaved, boolean isFavorite, int status, String tag, MaterialDialog dialog, int position);

}
