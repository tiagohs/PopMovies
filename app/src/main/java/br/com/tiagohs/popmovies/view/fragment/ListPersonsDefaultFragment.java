package br.com.tiagohs.popmovies.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.LinearLayout;

import com.pnikosis.materialishprogress.ProgressWheel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.com.tiagohs.popmovies.R;
import br.com.tiagohs.popmovies.model.dto.PersonListDTO;
import br.com.tiagohs.popmovies.presenter.ListPersonsDefaultPresenter;
import br.com.tiagohs.popmovies.util.enumerations.Sort;
import br.com.tiagohs.popmovies.view.ListPersonsDefaultView;
import br.com.tiagohs.popmovies.view.activity.ListsDefaultActivity;
import br.com.tiagohs.popmovies.view.adapters.PersonAdapter;
import br.com.tiagohs.popmovies.view.callbacks.PersonCallbacks;
import butterknife.BindView;

public class ListPersonsDefaultFragment extends BaseFragment implements ListPersonsDefaultView {
    private static final String ARG_PERSONS = "persons";
    private static final String ARG_PERSON_SORT = "person_sort";
    private static final String ARG_TYPE_LAYOUT = "layout_manager";
    private static final String ARG_NUM_COLUNAS = "colunas";
    private static final String ARG_ORIENTATION = "orientation";
    private static final String ARG_REVERSE_LAYOUT= "reverseLayout";
    private static final String ARG_SPAN_COUNT = "spanCount";
    private static final String ARG_LAYOUT_ID = "layoutID";

    @BindView(R.id.list_person_recycler_view)
    RecyclerView mPersonsRecyclerView;

    @BindView(R.id.list_person_principal_progress)
    ProgressWheel mPersonProgress;

    @Inject
    ListPersonsDefaultPresenter mPresenter;

    private List<PersonListDTO> mPersonCredit;
    private boolean hasMorePages;
    private PersonCallbacks mCallbacks;
    private PersonAdapter mPersonAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Sort mSort;

    private int mOrientation;
    private ListsDefaultActivity.TypeListLayout mTypeListLayout;
    private int mColunas;
    private int mSpanCount;
    private boolean mReverseLayout;

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

    public static ListPersonsDefaultFragment newInstance(List<PersonListDTO> persons, Bundle bundle) {
        if (bundle == null)
            bundle = new Bundle();

        bundle.putSerializable(ARG_PERSONS, (ArrayList<PersonListDTO>) persons);

        ListPersonsDefaultFragment listPersonsDefaultFragment = new ListPersonsDefaultFragment();
        listPersonsDefaultFragment.setArguments(bundle);
        return listPersonsDefaultFragment;
    }

    public static ListPersonsDefaultFragment newInstance(Sort sortPerson, Bundle bundle) {
        if (bundle == null)
            bundle = new Bundle();

        bundle.putSerializable(ARG_PERSON_SORT, sortPerson);

        ListPersonsDefaultFragment listPersonsDefaultFragment = new ListPersonsDefaultFragment();
        listPersonsDefaultFragment.setArguments(bundle);
        return listPersonsDefaultFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallbacks = (PersonCallbacks) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getApplicationComponent().inject(this);
        mPresenter.setView(this);

        mSort = (Sort) getArguments().getSerializable(ARG_PERSON_SORT);
        mPersonCredit = (ArrayList<PersonListDTO>) getArguments().getSerializable(ARG_PERSONS);
        mColunas = getArguments().getInt(ARG_NUM_COLUNAS, 2);
        mTypeListLayout = (ListsDefaultActivity.TypeListLayout) getArguments().getSerializable(ARG_TYPE_LAYOUT);
        mOrientation = getArguments().getInt(ARG_NUM_COLUNAS, LinearLayout.HORIZONTAL);
        mReverseLayout = getArguments().getBoolean(ARG_REVERSE_LAYOUT);
        mSpanCount = getArguments().getInt(ARG_SPAN_COUNT);
    }

    @Override
    public void onStart() {
        super.onStart();

        if (mSort != null)
            mPresenter.getPersons(mSort);
         else {
            setupRecyclerView();
            setProgressVisibility(View.GONE);
        }

    }

    public void setListMovies(List<PersonListDTO> listMovies, boolean hasMorePages) {
        mPersonCredit = listMovies;
        this.hasMorePages = hasMorePages;
    }

    public void addAllMovies(List<PersonListDTO> listMovies, boolean hasMorePages) {
        mPersonCredit.addAll(listMovies);
        this.hasMorePages = hasMorePages;
    }

    public void setupRecyclerView() {
        setupLayoutManager();
        mPersonsRecyclerView.addOnScrollListener(createOnScrollListener());
        setupAdapter();
    }

    public void updateAdapter() {
        mPersonAdapter.notifyDataSetChanged();
    }

    private void setupAdapter() {
        if (mPersonAdapter == null) {
            mPersonAdapter = new PersonAdapter(getActivity(), mPersonCredit == null ? new ArrayList<PersonListDTO>() : mPersonCredit, mCallbacks);
            mPersonsRecyclerView.setAdapter(mPersonAdapter);
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

        mPersonsRecyclerView.setLayoutManager(mLayoutManager);
    }

    private RecyclerView.OnScrollListener createOnScrollListener() {
        return new EndlessRecyclerView(mLayoutManager) {

            @Override
            public void onLoadMore(int current_page) {
                if(hasMorePages) {
                    onStart();
                }

            }
        };
    }


    @Override
    protected int getViewID() {
        return R.layout.fragment_list_persons_default;
    }

    @Override
    protected View.OnClickListener onSnackbarClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onStart();
                mSnackbar.dismiss();
            }
        };
    }

    @Override
    public void setProgressVisibility(int visibityState) {
        mPersonProgress.setVisibility(visibityState);
    }

    @Override
    public void setRecyclerViewVisibility(int visibilityState) {
        mPersonsRecyclerView.setVisibility(visibilityState);
    }
}
