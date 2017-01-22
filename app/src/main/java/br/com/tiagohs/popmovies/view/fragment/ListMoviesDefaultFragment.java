package br.com.tiagohs.popmovies.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.pnikosis.materialishprogress.ProgressWheel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import br.com.tiagohs.popmovies.R;
import br.com.tiagohs.popmovies.data.repository.MovieRepository;
import br.com.tiagohs.popmovies.model.dto.MovieListDTO;
import br.com.tiagohs.popmovies.presenter.ListMoviesDefaultPresenter;
import br.com.tiagohs.popmovies.util.DTOUtils;
import br.com.tiagohs.popmovies.util.enumerations.Sort;
import br.com.tiagohs.popmovies.view.ListMoviesDefaultView;
import br.com.tiagohs.popmovies.view.activity.ListsDefaultActivity;
import br.com.tiagohs.popmovies.view.adapters.ListMoviesAdapter;
import br.com.tiagohs.popmovies.view.callbacks.ListMoviesCallbacks;
import butterknife.BindView;
import butterknife.OnClick;

public class ListMoviesDefaultFragment extends BaseFragment implements ListMoviesDefaultView {
    private static final String TAG = ListMoviesDefaultFragment.class.getSimpleName();

    private static final String ARG_ID = "id";
    private static final String ARG_SORT = "sort";
    private static final String ARG_TYPE_LAYOUT = "layout_manager";
    private static final String ARG_NUM_COLUNAS = "colunas";
    private static final String ARG_ORIENTATION = "orientation";
    private static final String ARG_REVERSE_LAYOUT= "reverseLayout";
    private static final String ARG_SPAN_COUNT = "spanCount";
    private static final String ARG_LAYOUT_ID = "layoutID";
    private static final String ARG_PARAMETERS = "paramenters";
    private static final String ARG_LIST_MOVIES = "List_movies";
    private static final String ARG_FRAGMENT_LAYOUT_ID = "fragment_layout_id";

    @BindView(R.id.list_movies_recycler_view)           RecyclerView mMoviesRecyclerView;
    @BindView(R.id.list_movies_principal_progress)      ProgressWheel mPrincipalProgress;
    @Nullable @BindView(R.id.swipeRefreshLayout)        SwipeRefreshLayout mSwipeRefreshLayout;

    @Inject
    ListMoviesDefaultPresenter mPresenter;

    private RecyclerView.LayoutManager mLayoutManager;
    private ListMoviesCallbacks mCallbacks;
    private boolean hasMorePages;

    private int mID;
    private int mLayoutID;
    private int mOrientation;

    private Sort mTypeList;
    private ListsDefaultActivity.TypeListLayout mTypeListLayout;
    private int mColunas;
    private int mSpanCount;
    private boolean mReverseLayout;
    private int mFragmentLayoutId;

    private List<MovieListDTO> mListMovies;
    private List<MovieListDTO> mListMoviesPararmeter;
    private Map<String, String> mParameters;
    private ListMoviesAdapter mListMoviesAdapter;

    public static Bundle createLinearListArguments(int orientation, boolean reverseLayout) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_TYPE_LAYOUT, ListsDefaultActivity.TypeListLayout.LINEAR_LAYOUT);
        bundle.putInt(ARG_ORIENTATION, orientation);
        bundle.putBoolean(ARG_REVERSE_LAYOUT, reverseLayout);

        return bundle;
    }

    public static Bundle createGridListArguments(int colunas) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_TYPE_LAYOUT, ListsDefaultActivity.TypeListLayout.GRID_LAYOUT);
        bundle.putInt(ARG_NUM_COLUNAS, colunas);

        return bundle;
    }

    public static Bundle createStaggeredListArguments(int spanCount, int orientation) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_TYPE_LAYOUT, ListsDefaultActivity.TypeListLayout.STAGGERED);
        bundle.putInt(ARG_ORIENTATION, orientation);
        bundle.putInt(ARG_SPAN_COUNT, spanCount);

        return bundle;
    }

    public static ListMoviesDefaultFragment newInstance(Sort typeList, int layoutID, int fragmentLayoutId, List<MovieListDTO> listMovies, Bundle arguments) {

        if (arguments != null) {
            arguments.putSerializable(ARG_LIST_MOVIES, (ArrayList<MovieListDTO>) listMovies);
            arguments.putInt(ARG_LAYOUT_ID, layoutID);
            arguments.putInt(ARG_FRAGMENT_LAYOUT_ID, fragmentLayoutId);
            arguments.putSerializable(ARG_SORT, typeList);
        }

        ListMoviesDefaultFragment listMoviesDefaultFragment = new ListMoviesDefaultFragment();
        listMoviesDefaultFragment.setArguments(arguments);
        return listMoviesDefaultFragment;
    }

    public static ListMoviesDefaultFragment newInstance(Sort typeList, int fragmentLayoutId, int layoutID, Bundle arguments) {

        if (arguments != null) {
            arguments.putInt(ARG_LAYOUT_ID, layoutID);
            arguments.putInt(ARG_FRAGMENT_LAYOUT_ID, fragmentLayoutId);
            arguments.putSerializable(ARG_SORT, typeList);
        }

        ListMoviesDefaultFragment listMoviesDefaultFragment = new ListMoviesDefaultFragment();
        listMoviesDefaultFragment.setArguments(arguments);
        return listMoviesDefaultFragment;
    }

    public static ListMoviesDefaultFragment newInstance(Sort typeList, int layoutID, int fragmentLayoutId, Map<String, String> parameters, Bundle arguments) {

        if (arguments != null) {
            arguments.putInt(ARG_LAYOUT_ID, layoutID);
            arguments.putInt(ARG_FRAGMENT_LAYOUT_ID, fragmentLayoutId);
            arguments.putSerializable(ARG_PARAMETERS, (HashMap<String, String>) parameters);
            arguments.putSerializable(ARG_SORT, typeList);
        }

        ListMoviesDefaultFragment listMoviesDefaultFragment = new ListMoviesDefaultFragment();
        listMoviesDefaultFragment.setArguments(arguments);
        return listMoviesDefaultFragment;
    }

    public static ListMoviesDefaultFragment newInstance(int id, Sort typeList, int layoutID, int fragmentLayoutId, Map<String, String> parameters, Bundle arguments) {

        if (arguments != null) {
            arguments.putInt(ARG_ID, id);
            arguments.putInt(ARG_LAYOUT_ID, layoutID);
            arguments.putInt(ARG_FRAGMENT_LAYOUT_ID, fragmentLayoutId);
            arguments.putSerializable(ARG_PARAMETERS, (HashMap<String, String>) parameters);
            arguments.putSerializable(ARG_SORT, typeList);
        }

        ListMoviesDefaultFragment listMoviesDefaultFragment = new ListMoviesDefaultFragment();
        listMoviesDefaultFragment.setArguments(arguments);
        return listMoviesDefaultFragment;
    }

    public ListMoviesDefaultFragment() {
        mListMovies = new ArrayList<>();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getApplicationComponent().inject(this);

        mOrientation = getArguments().getInt(ARG_NUM_COLUNAS, LinearLayout.HORIZONTAL);
        mFragmentLayoutId = getArguments().getInt(ARG_FRAGMENT_LAYOUT_ID, R.layout.fragment_list_movies_default);
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

        mID = getArguments().getInt(ARG_ID);
        mTypeList = (Sort) getArguments().getSerializable(ARG_SORT);
        mParameters = (HashMap<String, String>) getArguments().getSerializable(ARG_PARAMETERS);
        mColunas = getArguments().getInt(ARG_NUM_COLUNAS, 2);
        mTypeListLayout = (ListsDefaultActivity.TypeListLayout) getArguments().getSerializable(ARG_TYPE_LAYOUT);
        mReverseLayout = getArguments().getBoolean(ARG_REVERSE_LAYOUT);
        mSpanCount = getArguments().getInt(ARG_SPAN_COUNT);
        mLayoutID = getArguments().getInt(ARG_LAYOUT_ID, R.layout.item_similares_movie);
        mListMoviesPararmeter = (ArrayList<MovieListDTO>) getArguments().getSerializable(ARG_LIST_MOVIES);

        mPresenter.setContext(getActivity());

        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    mPresenter.resetValues();
                    searchMovies();
                }
            });
        }

    }

    @Override
    public void onResume() {
        super.onResume();

        searchMovies();
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.resetValues();
    }

    @Override
    protected View.OnClickListener onSnackbarClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchMovies();
                mSnackbar.dismiss();
            }
        };
    }

    private void searchMovies() {

        if (mTypeList.equals(Sort.LIST_DEFAULT)) {
            updateUI();
        } else {
            mPresenter.getMovies(mID, mTypeList, TAG, mParameters);
        }
        if (mSwipeRefreshLayout != null)
            mSwipeRefreshLayout.setRefreshing(false);
    }

    private void updateUI() {
        setProgressVisibility(View.VISIBLE);
        setRecyclerViewVisibility(View.GONE);
        setListMovies(mListMoviesPararmeter, false);
        setupRecyclerView();
        setProgressVisibility(View.GONE);
        setRecyclerViewVisibility(View.VISIBLE);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.setView(this);
    }

    @Override
    protected int getViewID() {
        return mFragmentLayoutId;
    }

    public void setProgressVisibility(int visibityState) {
        mPrincipalProgress.setVisibility(visibityState);
    }

    public void setRecyclerViewVisibility(int visibilityState) {
        mMoviesRecyclerView.setVisibility(visibilityState);
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
        if (mListMoviesAdapter == null) {
            setupLayoutManager();
            mMoviesRecyclerView.addOnScrollListener(createOnScrollListener());
            mMoviesRecyclerView.setNestedScrollingEnabled(false);
            setupAdapter();
        } else
            updateAdapter();

    }

    public void updateAdapter() {
        if (mListMoviesAdapter != null) {
            mListMoviesAdapter.setList(mListMovies);
            mListMoviesAdapter.notifyDataSetChanged();
        } else
            setupRecyclerView();
    }

    private void setupAdapter() {

        if (mListMoviesAdapter == null) {
            mListMoviesAdapter = new ListMoviesAdapter(getActivity(), mListMovies, mCallbacks, mLayoutID, mPresenter);
            mMoviesRecyclerView.setAdapter(mListMoviesAdapter);
        } else
            updateAdapter();
    }

    private void setupLayoutManager() {

        switch (mTypeListLayout) {
            case GRID_LAYOUT:
                mLayoutManager = new GridLayoutManager(getActivity(), mColunas);
                break;
            case LINEAR_LAYOUT:
                mLayoutManager = new LinearLayoutManager(getActivity(), mOrientation, mReverseLayout);
                break;
            case STAGGERED:
                mLayoutManager = new StaggeredGridLayoutManager(mSpanCount, mOrientation);
                break;
            default:
                mLayoutManager = new GridLayoutManager(getActivity(), mColunas);
        }

        mMoviesRecyclerView.setLayoutManager(mLayoutManager);
    }

    private RecyclerView.OnScrollListener createOnScrollListener() {
        return new EndlessRecyclerView(mLayoutManager) {

            @Override
            public void onLoadMore(int current_page) {
                if(hasMorePages) {
                    searchMovies();
                }

            }
        };
    }

}
