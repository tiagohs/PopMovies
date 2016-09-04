package br.com.tiagohs.popmovies.view.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.pnikosis.materialishprogress.ProgressWheel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.com.tiagohs.popmovies.R;
import br.com.tiagohs.popmovies.model.movie.Movie;
import br.com.tiagohs.popmovies.presenter.MoviesByGenrePresenter;
import br.com.tiagohs.popmovies.view.MoviesByGenreView;
import br.com.tiagohs.popmovies.view.activity.MoviesByGenreActivity;
import br.com.tiagohs.popmovies.view.adapters.ListMoviesAdapter;
import butterknife.BindView;

/**
 * Created by Tiago Henrique on 03/09/2016.
 */
public class MoviesByGenreFragment extends BaseFragment implements MoviesByGenreView {
    private static final String ARG_GENRE_ID = "genre_id";

    @BindView(R.id.movies_recycler_view)
    RecyclerView mMoviesRecyclerView;

    @BindView(R.id.search_progress)
    ProgressWheel mProgressWheel;

    @Inject
    MoviesByGenrePresenter mMoviesByGenrePresenter;

    private ListMoviesCallbacks mCallbacks;

    private int mGenreID;
    private int mCurrentPage;
    private int mTotalPages;

    private List<Movie> mListMovies;
    private ListMoviesAdapter mListMoviesAdapter;

    public static MoviesByGenreFragment newInstance(int mGenreID) {
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_GENRE_ID, mGenreID);

        MoviesByGenreFragment moviesByGenreFragment = new MoviesByGenreFragment();
        moviesByGenreFragment.setArguments(bundle);
        return moviesByGenreFragment;
    }

    public MoviesByGenreFragment() {
        mListMovies = new ArrayList<>();
        mCurrentPage = 1;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getApplicationComponent().inject(this);

        mGenreID = getArguments().getInt(ARG_GENRE_ID);

        mMoviesByGenrePresenter.setView(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallbacks = (ListMoviesCallbacks) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    @Override
    public void onStart() {
        super.onStart();

        if (isInternetConnected()) {
            mMoviesByGenrePresenter.getMoviesByGenre(mGenreID, mCurrentPage);


            int columnCount = getResources().getInteger(R.integer.movies_columns);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), columnCount);
            mMoviesRecyclerView.setLayoutManager(gridLayoutManager);
            mMoviesRecyclerView.addOnScrollListener(new EndlessRecyclerView(gridLayoutManager) {

                @Override
                public void onLoadMore(int current_page) {
                    if(mCurrentPage < mTotalPages)
                        mMoviesByGenrePresenter.getMoviesByGenre(mGenreID, ++mCurrentPage);
                }
            });
            mMoviesRecyclerView.setItemAnimator(new DefaultItemAnimator());

            setupAdapter();
        } else {
            onError("Sem Conexão.");
        }

    }

    @Override
    public void atualizarView(int currentPage, int totalPages, List<Movie> listMovies) {
        mCurrentPage = currentPage;
        mTotalPages = totalPages;

        mListMovies.addAll(listMovies);
        setupAdapter();
    }

    private void setupAdapter() {

        if (mListMoviesAdapter == null) {
            mListMoviesAdapter = new ListMoviesAdapter(getActivity(), mListMovies, mCallbacks);
            mMoviesRecyclerView.setAdapter(mListMoviesAdapter);
        } else {
            mListMoviesAdapter.notifyDataSetChanged();
        }
    }

    public void onError(String msg) {
        Snackbar snackbar = Snackbar
                .make(getCoordinatorLayout(), msg, Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(R.string.tentar_novamente), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mMoviesByGenrePresenter.getMoviesByGenre(mGenreID, mCurrentPage);
                    }
                });

        snackbar.setActionTextColor(Color.RED);
        snackbar.show();
    }

    public CoordinatorLayout getCoordinatorLayout() {
        return ((MoviesByGenreActivity) getActivity()).getCoordinatorLayout();
    }

    @Override
    protected int getViewID() {
        return R.layout.fragment_movies_by_genre;
    }

    @Override
    public void showProgressBar() {
        mProgressWheel.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        mProgressWheel.setVisibility(View.GONE);
    }
}
