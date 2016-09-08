package br.com.tiagohs.popmovies.view.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.pnikosis.materialishprogress.ProgressWheel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.com.tiagohs.popmovies.R;
import br.com.tiagohs.popmovies.model.dto.MovieListDTO;
import br.com.tiagohs.popmovies.presenter.ListMoviesPresenter;
import br.com.tiagohs.popmovies.view.ListMovieView;
import br.com.tiagohs.popmovies.view.adapters.ListMoviesAdapter;
import br.com.tiagohs.popmovies.view.callbacks.ListMoviesCallbacks;
import butterknife.BindView;
import butterknife.OnClick;

public class ListMoviesFragment extends BaseFragment implements ListMovieView {
    public enum Sort{ POPULARES, FAVORITOS };

    private static final String TAG = ListMoviesFragment.class.getSimpleName();

    @Inject
    ListMoviesPresenter mPresenter;

    @BindView(R.id.list_movies_principal_progress)
    ProgressWheel mPrincipalProgress;

    @BindView(R.id.img_background_no_connection)
    ImageView mBackgroundNoConnectionImage;

    private List<MovieListDTO> mListMovies;

    private GridLayoutManager mGridLayoutManager;
    private ListMoviesAdapter mListMoviesAdapter;
    private ListMoviesCallbacks mCallbacks;
    private boolean hasMorePages;

    @BindView(R.id.list_movies_recycler_view)
    RecyclerView mRecyclerView;

    public static ListMoviesFragment newInstance(Sort sort) {
        return new ListMoviesFragment();
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

    public ListMoviesFragment() {
        hasMorePages = false;
        mListMovies = new ArrayList<>();
    }

    @Override
    protected int getViewID() {
        return R.layout.fragment_list_movies;
    }

    @Override
    protected View.OnClickListener onSnackbarClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.getMovies();
                mSnackbar.dismiss();
            }
        };
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getApplicationComponent().inject(this);

        mPresenter.setView(this);
    }

    @Override
    public void onStart() {
        super.onStart();

        mPresenter.getMovies();
    }

    public void setProgressVisibility(int visibityState) {
        mPrincipalProgress.setVisibility(visibityState);
    }

    public void setRecyclerViewVisibility(int visibilityState) {
        mRecyclerView.setVisibility(visibilityState);
    }

    public void setBackgroundNoConnectionImageVisibility(int visibilityState) {
        mBackgroundNoConnectionImage.setVisibility(visibilityState);
    }

    public void setListMovies(List<MovieListDTO> listMovies, boolean hasMorePages) {
        mListMovies = listMovies;
        this.hasMorePages = hasMorePages;
    }

    public void addAllMovies(List<MovieListDTO> listMovies, boolean hasMorePages) {
        mListMovies.addAll(listMovies);
        this.hasMorePages = hasMorePages;
    }

    public void setupRecyclerView() {
        setupLayoutManager();
        mRecyclerView.addOnScrollListener(createOnScrollListener());
        setupAdapter();
    }

    public void updateAdapter() {
        mListMoviesAdapter.notifyDataSetChanged();
    }

    private void setupAdapter() {
        mListMoviesAdapter = new ListMoviesAdapter(getActivity(), mListMovies, mCallbacks, R.layout.item_list_movies);
        mRecyclerView.setAdapter(mListMoviesAdapter);
    }

    private void setupLayoutManager() {
        if (mGridLayoutManager == null) {
            mGridLayoutManager = new GridLayoutManager(getActivity(), getResources().getInteger(R.integer.movies_columns));
            mRecyclerView.setLayoutManager(mGridLayoutManager);
        }
    }

    private RecyclerView.OnScrollListener createOnScrollListener() {
        return new EndlessRecyclerView(mGridLayoutManager) {

            @Override
            public void onLoadMore(int current_page) {
                Log.i(TAG, "Result: " + hasMorePages);
                if(hasMorePages)
                    mPresenter.getMovies();
            }
        };
    }

    @OnClick(R.id.img_background_no_connection)
    public void onClickImageNoConnection() {
        mPresenter.getMovies();
        mSnackbar.dismiss();
    }



}
