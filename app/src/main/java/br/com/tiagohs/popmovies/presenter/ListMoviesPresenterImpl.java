package br.com.tiagohs.popmovies.presenter;

import android.view.View;

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

    private int mCurrentPage;
    private int mTotalPages;

    public ListMoviesPresenterImpl() {
        mPopMovieServer = PopMovieServer.getInstance();
        mCurrentPage = 0;
    }

    @Override
    public void setView(ListMovieView view) {
        this.mListMovieView = view;
    }

    public void getMovies() {
        mListMovieView.setProgressVisibility(View.VISIBLE);

        if (mListMovieView.isInternetConnected()) {
            mPopMovieServer.getPopularMovies(++mCurrentPage, this);
            mListMovieView.setRecyclerViewVisibility(isFirstPage() ? View.GONE : View.VISIBLE);
            mListMovieView.setBackgroundNoConnectionImageVisibility(View.GONE);
        } else {
            noConnectionError();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        noConnectionError();
    }

    private void noConnectionError() {

        mListMovieView.onError("Sem Conexao");
        mListMovieView.setProgressVisibility(View.GONE);

        if (mCurrentPage == 0) {
            mListMovieView.setRecyclerViewVisibility(View.GONE);
            mListMovieView.setBackgroundNoConnectionImageVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onResponse(MovieResponse movies) {
        mCurrentPage = movies.getPage();
        mTotalPages = movies.getTotalPages();

        if (isFirstPage()) {
            mListMovieView.setListMovies(createMovieDTO(movies.getResults()), hasMorePages());
            mListMovieView.setupRecyclerView();
        } else {
            mListMovieView.addAllMovies(createMovieDTO(movies.getResults()), hasMorePages());
            mListMovieView.updateAdapter();
        }

        mListMovieView.setProgressVisibility(View.GONE);
        mListMovieView.setRecyclerViewVisibility(View.VISIBLE);
    }

    private boolean isFirstPage() {
        return mCurrentPage == 1;
    }

    private boolean hasMorePages() {
        return mCurrentPage < mTotalPages;
    }

    private List<MovieListDTO> createMovieDTO(List<Movie> movies) {
        List<MovieListDTO> moviesDTO = new ArrayList<>();

        for (Movie movie : movies) {
            moviesDTO.add(new MovieListDTO(movie.getId(), movie.getTitle(), movie.getPosterPath(), movie.getVoteAverage()));
        }

        return moviesDTO;
    }


}
