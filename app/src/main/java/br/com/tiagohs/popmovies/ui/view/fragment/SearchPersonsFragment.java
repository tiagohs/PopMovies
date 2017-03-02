package br.com.tiagohs.popmovies.ui.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.pnikosis.materialishprogress.ProgressWheel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.com.tiagohs.popmovies.R;
import br.com.tiagohs.popmovies.ui.contracts.SearchContract;
import br.com.tiagohs.popmovies.model.person.PersonFind;
import br.com.tiagohs.popmovies.ui.tools.EndlessRecyclerView;
import br.com.tiagohs.popmovies.ui.view.activity.PersonDetailActivity;
import br.com.tiagohs.popmovies.ui.adapters.SearchPersonAdapter;
import br.com.tiagohs.popmovies.ui.callbacks.PersonCallbacks;
import br.com.tiagohs.popmovies.ui.callbacks.SearchCallback;
import br.com.tiagohs.popmovies.util.PrefsUtils;
import butterknife.BindView;

public class SearchPersonsFragment extends BaseFragment implements SearchCallback, SearchContract.SearchPersonsView, PersonCallbacks {
    private static final String TAG = SearchPersonsFragment.class.getSimpleName();

    @BindView(R.id.list_person_recycler_view)       RecyclerView mResultsRecyclerView;
    @BindView(R.id.search_progress)                 ProgressWheel mProgress;
    @BindView(R.id.nenhuma_pessoa_encontrado)       TextView mNenhumaPessoaEncontrado;

    @Inject
    SearchContract.SearchPresenter mSearchPresenter;

    private boolean hasMorePages;
    private String mQuery;
    private List<PersonFind> mListPerson;

    private SearchPersonAdapter mSearchAdapter;
    private LinearLayoutManager mLinearLayout;

    public static SearchPersonsFragment newInstace() {
        SearchPersonsFragment searchPersonsFragment = new SearchPersonsFragment();
        return searchPersonsFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getApplicationComponent().inject(this);

        mListPerson = new ArrayList<>();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mSearchPresenter.setProfileID(PrefsUtils.getCurrentProfile(getContext()).getProfileID());
        mSearchPresenter.setPersonView(this);
        setRetainInstance(true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mSearchPresenter.onUnbindView();
    }

    @Override
    protected int getViewID() {
        return R.layout.fragment_search_persons;
    }

    @Override
    protected View.OnClickListener onSnackbarClickListener() {
        return null;
    }

    @Override
    public void onQueryChanged(String query, boolean isNewSearch) {
        mQuery = query;

        mSearchPresenter.searchPersons(query, false, TAG, null, isNewSearch);
    }

    @Override
    public void setListPersons(List<PersonFind> listPersons, boolean hasMorePages) {
        mListPerson = listPersons;
        this.hasMorePages = hasMorePages;
    }

    @Override
    public void addAllPersons(List<PersonFind> listPersons, boolean hasMorePages) {
        mListPerson.addAll(listPersons);
        this.hasMorePages = hasMorePages;
    }

    @Override
    public void setNenhumaPessoaEncontradoVisibility(int visibility) {
        mNenhumaPessoaEncontrado.setVisibility(visibility);
    }

    @Override
    public void setupRecyclerView() {
        mLinearLayout = new LinearLayoutManager(getActivity());
        mResultsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mResultsRecyclerView.addOnScrollListener(createOnScrollListener());
        setupAdapter();
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

    private void setupAdapter() {

        if (mSearchAdapter == null) {
            mSearchAdapter = new SearchPersonAdapter(getActivity(), mListPerson, this);
            mResultsRecyclerView.setAdapter(mSearchAdapter);
        } else
            updateAdapter();

    }

    @Override
    public void updateAdapter() {
        mSearchAdapter.setList(mListPerson);
        mSearchAdapter.notifyDataSetChanged();
    }

    @Override
    public void setProgressVisibility(int visibityState) {
        mProgress.setVisibility(visibityState);
    }

    @Override
    public void onClickPerson(int personID) {
        startActivity(PersonDetailActivity.newIntent(getContext(), personID));
    }
}
