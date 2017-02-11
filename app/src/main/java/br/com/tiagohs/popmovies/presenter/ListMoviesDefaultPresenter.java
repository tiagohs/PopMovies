package br.com.tiagohs.popmovies.presenter;

import android.content.Context;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.List;
import java.util.Map;

import br.com.tiagohs.popmovies.data.repository.MovieRepository;
import br.com.tiagohs.popmovies.data.repository.ProfileRepository;
import br.com.tiagohs.popmovies.model.dto.MovieListDTO;
import br.com.tiagohs.popmovies.model.movie.MovieDetails;
import br.com.tiagohs.popmovies.util.enumerations.Sort;
import br.com.tiagohs.popmovies.view.ListMoviesDefaultView;

public interface ListMoviesDefaultPresenter extends BasePresenter<ListMoviesDefaultView> {

    void getMovies(int id, Sort typeList, String tag, Map<String, String> parameters);

    void setMovieRepository(MovieRepository movieRepository);
    void setProfileRepository(ProfileRepository profileRepository);
    void setProfileID(long profileID);

    void getMovieDetails(int movieID, boolean isSaved, boolean isFavorite, int status, String tag, int position);


}
