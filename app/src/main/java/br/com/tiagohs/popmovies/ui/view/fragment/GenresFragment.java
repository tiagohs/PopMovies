package br.com.tiagohs.popmovies.ui.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.pnikosis.materialishprogress.ProgressWheel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.com.tiagohs.popmovies.R;
import br.com.tiagohs.popmovies.ui.contracts.GenresContract;
import br.com.tiagohs.popmovies.model.movie.Genre;
import br.com.tiagohs.popmovies.ui.adapters.GenresListAdapter;
import br.com.tiagohs.popmovies.ui.callbacks.GenresCallbacks;
import butterknife.BindView;

public class GenresFragment extends BaseFragment implements GenresContract.GenresView {
    private static final String TAG = GenresFragment.class.getSimpleName();

    @BindView(R.id.list_movies_recycler_view)       RecyclerView mGenresRecyclerView;
    @BindView(R.id.list_movies_principal_progress)  ProgressWheel mProgress;

    @Inject
    GenresContract.GenresPresenter mGenresPresenter;

    private GenresListAdapter mGenresListAdapter;
    private List<Genre> mGenreList;
    private GenresCallbacks mCallbacks;

    public static GenresFragment newInstance() {
        GenresFragment genresFragment = new GenresFragment();
        return genresFragment;
    }

    public GenresFragment() {
        mGenreList = new ArrayList<>();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallbacks = (GenresCallbacks) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getApplicationComponent().inject(this);

        mGenresPresenter.onBindView(this);

        mGenresRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        setupAdapter();
        mGenresPresenter.getGenres();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mGenresPresenter.onUnbindView();
    }

    @Override
    protected int getViewID() {
        return R.layout.fragment_genres;
    }

    @Override
    protected View.OnClickListener onSnackbarClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGenresPresenter.getGenres();
                mSnackbar.dismiss();
            }
        };
    }

    @Override
    public void updateView(List<Genre> genres) {
        mGenresListAdapter.setGeneros(genres);
        setupAdapter();
    }

    private void setupAdapter() {

        if (mGenresListAdapter == null) {
            mGenresListAdapter = new GenresListAdapter(getActivity(), mGenreList, mCallbacks);
            mGenresRecyclerView.setAdapter(mGenresListAdapter);
        } else {
            mGenresListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void setProgressVisibility(int visibityState) {
        mProgress.setVisibility(visibityState);
    }
}
