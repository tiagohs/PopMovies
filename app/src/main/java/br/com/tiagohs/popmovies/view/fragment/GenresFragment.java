package br.com.tiagohs.popmovies.view.fragment;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.com.tiagohs.popmovies.App;
import br.com.tiagohs.popmovies.R;
import br.com.tiagohs.popmovies.model.movie.Genre;
import br.com.tiagohs.popmovies.presenter.GenresPresenter;
import br.com.tiagohs.popmovies.view.GenresView;
import br.com.tiagohs.popmovies.view.adapters.GenresListAdapter;
import br.com.tiagohs.popmovies.view.callbacks.GenresCallbacks;
import butterknife.BindView;

/**
 * Created by Tiago Henrique on 03/09/2016.
 */
public class GenresFragment extends BaseFragment implements GenresView {

    @BindView(R.id.list_movies_recycler_view)
    RecyclerView mGenresRecyclerView;

    @Inject
    GenresPresenter mGenresPresenter;

    private GenresListAdapter mGenresListAdapter;
    private List<Genre> mGenreList;
    private GenresCallbacks mCallbacks;

    public static GenresFragment newInstance() {
        return new GenresFragment();
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
    public void onStart() {
        super.onStart();
        ((App) getActivity().getApplication()).getPopMoviesComponent().inject(this);

        mGenresPresenter.setView(this);

        mGenresPresenter.getGenres();
    }

    @Override
    public void onResume() {
        super.onResume();

        mGenresRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        setupAdapter();
    }

    @Override
    protected int getViewID() {
        return R.layout.fragment_genres;
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
}
