package br.com.tiagohs.popmovies.view.fragment;

import android.app.ActivityOptions;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pnikosis.materialishprogress.ProgressWheel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.com.tiagohs.popmovies.App;
import br.com.tiagohs.popmovies.R;
import br.com.tiagohs.popmovies.model.movie.Movie;
import br.com.tiagohs.popmovies.presenter.SearchPresenter;
import br.com.tiagohs.popmovies.presenter.SearchPresenterImpl;
import br.com.tiagohs.popmovies.view.SearchMoviesView;
import br.com.tiagohs.popmovies.view.activity.MovieDetailActivity;
import br.com.tiagohs.popmovies.view.adapters.SearchMovieAdapter;
import br.com.tiagohs.popmovies.view.callbacks.SearchCallback;
import butterknife.BindView;

public class SearchMoviesFragment extends BaseFragment implements SearchCallback, SearchMoviesView {
    private static final String TAG = SearchMoviesFragment.class.getSimpleName();

    private static final String ARG_PRESENTER = "br.com.tiagohs.popmovies.presenter";

    @BindView(R.id.list_movies_recycler_view)
    RecyclerView mResultsRecyclerView;
    @BindView(R.id.search_progress)
    ProgressWheel mProgress;
    @BindView(R.id.nenhum_filme_encontrado)
    TextView mNenhumFilmeEncontrado;

    @Inject
    SearchPresenter mSearchPresenter;

    private boolean hasMorePages;
    private String mQuery;
    private List<Movie> mListMovies = new ArrayList<>();

    private SearchMovieAdapter mSearchAdapter;
    private LinearLayoutManager mLinearLayout;
    public static SearchMoviesFragment newInstace() {
        SearchMoviesFragment searchMoviesFragment = new SearchMoviesFragment();
        return searchMoviesFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getApplicationComponent().inject(this);

        mSearchPresenter.setContext(getContext());
        mSearchPresenter.setMovieView(this);
    }

    @Override
    protected int getViewID() {
        return R.layout.fragment_search_movies;
    }

    public void setListMovies(List<Movie> listMovies, boolean hasMorePages) {
        mListMovies = listMovies;
        this.hasMorePages = hasMorePages;
    }

    public void addAllMovies(List<Movie> listMovies, boolean hasMorePages) {
        mListMovies.addAll(listMovies);
        this.hasMorePages = hasMorePages;
    }

    public void setupRecyclerView() {
        mLinearLayout = new LinearLayoutManager(getActivity());
        mResultsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mResultsRecyclerView.addOnScrollListener(createOnScrollListener());
        setupAdapter();
    }

    public void updateAdapter() {
        mSearchAdapter.notifyDataSetChanged();
    }

    private void setupAdapter() {
        mSearchAdapter = new SearchMovieAdapter(getActivity(), mListMovies, this, mSearchPresenter);
        mResultsRecyclerView.setAdapter(mSearchAdapter);
    }

    private RecyclerView.OnScrollListener createOnScrollListener() {
        return new EndlessRecyclerView(mLinearLayout) {

            @Override
            public void onLoadMore(int current_page) {
                if(hasMorePages)
                    onQueryChanged(mQuery, false);
            }
        };
    }

    public void onMovieSelected(int movieID, ImageView posterMovie) {

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions transitionActivityOptions = ActivityOptions
                    .makeSceneTransitionAnimation(getActivity(), posterMovie, getString(R.string.poster_movie));
            startActivity(MovieDetailActivity.newIntent(getActivity(), movieID), transitionActivityOptions.toBundle());
        } else {
            startActivity(MovieDetailActivity.newIntent(getActivity(), movieID));
        }

    }

    @Override
    public void setProgressVisibility(int visibityState) {
        mProgress.setVisibility(visibityState);
    }

    public void setNenhumFilmeEncontradoVisibility(int visibility) {
        mNenhumFilmeEncontrado.setVisibility(visibility);
    }

    @Override
    protected View.OnClickListener onSnackbarClickListener() {
        return null;
    }

    @Override
    public void onQueryChanged(String query, boolean isNewSearch) {
        mQuery = query;
        mSearchPresenter.searchMovies(query, false, null, TAG, null, isNewSearch);
    }
}
