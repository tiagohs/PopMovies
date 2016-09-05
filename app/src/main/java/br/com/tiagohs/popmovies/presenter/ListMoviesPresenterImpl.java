package br.com.tiagohs.popmovies.presenter;

import com.android.volley.VolleyError;

import java.util.ArrayList;
import java.util.List;

import br.com.tiagohs.popmovies.model.dto.MovieListDTO;
import br.com.tiagohs.popmovies.model.movie.Movie;
import br.com.tiagohs.popmovies.model.response.MovieResponse;
import br.com.tiagohs.popmovies.server.PopMovieServer;
import br.com.tiagohs.popmovies.view.ListMovieView;

public class ListMoviesPresenterImpl implements ListMoviesPresenter {
    private static final String TAG = ListMoviesPresenterImpl.class.getSimpleName();

    private ListMovieView mListMovieView;
    private PopMovieServer mPopMovieServer;

    public ListMoviesPresenterImpl() {
        mPopMovieServer = PopMovieServer.getInstance();
    }

    @Override
    public void setView(ListMovieView view) {
        this.mListMovieView = view;
    }

    public void getMovies(int currentPage) {
        mPopMovieServer.getPopularMovies(currentPage, this);
        mListMovieView.showDialogProgress();
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        mListMovieView.onError("Problemas com a Internet.");
        mListMovieView.hideDialogProgress();
    }

    @Override
    public void onResponse(MovieResponse movies) {
        List<MovieListDTO> moviesDTO = new ArrayList<>();

        for (Movie movie : movies.getResults()) {
            moviesDTO.add(new MovieListDTO(movie.getId(), movie.getPosterPath(), movie.getVoteAverage()));
        }

        mListMovieView.atualizarView(movies.getPage(), movies.getTotalPages(), moviesDTO);
        mListMovieView.hideDialogProgress();
    }

}
