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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.com.tiagohs.popmovies.App;
import br.com.tiagohs.popmovies.R;
import br.com.tiagohs.popmovies.model.Movie;
import br.com.tiagohs.popmovies.presenter.ListMoviesPresenter;
import br.com.tiagohs.popmovies.util.ImageSize;
import br.com.tiagohs.popmovies.util.ImageUtils;
import br.com.tiagohs.popmovies.view.ListMovieView;
import br.com.tiagohs.popmovies.view.activity.ListMoviesActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ListMoviesFragment extends BaseFragment implements ListMovieView {
    public enum Sort{ POPULARES, FAVORITOS, LANCAMENTOS };

    private static final String TAG = ListMoviesFragment.class.getSimpleName();

    @Inject
    ListMoviesPresenter presenter;

    private List<Movie> mListMovies;
    private int mCurrentPage;
    private int mTotalPages;

    private GridLayoutManager mGridLayoutManager;
    private ListMoviesAdapter mListMoviesAdapter;
    private Callbacks mCallbacks;

    @BindView(R.id.list_movies_recycler_view)
    RecyclerView mRecyclerView;

    public static ListMoviesFragment newInstance(Sort sort) {
        return new ListMoviesFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallbacks = (Callbacks) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }


    public interface Callbacks {
        void onMovieSelected(Movie movie, ImageView posterMovie);
    }

    public ListMoviesFragment() {
        mCurrentPage = 1;
        mListMovies = new ArrayList<>();
    }

    @Override
    protected int getViewID() {
        return R.layout.fragment_list_movies;
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((App) getActivity().getApplication()).getPopMoviesComponent().inject(this);

        presenter.setView(this);

        if (isInternetConnected()) {
            presenter.getMovies(mCurrentPage);


            int columnCount = getResources().getInteger(R.integer.movies_columns);
            mGridLayoutManager = new GridLayoutManager(getActivity(), columnCount);
            mRecyclerView.setLayoutManager(mGridLayoutManager);
            mRecyclerView.addOnScrollListener(new EndlessRecyclerView(mGridLayoutManager) {

                @Override
                public void onLoadMore(int current_page) {
                    if(mCurrentPage < mTotalPages)
                        presenter.getMovies(++mCurrentPage);
                }
            });
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());

            setupAdapter();
        } else {
            Log.i(TAG, "Não há conexão com a Internet.");
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

    @Override
    public void onError(String msg) {
        Snackbar snackbar = Snackbar
                            .make(getCoordinatorLayout(), msg, Snackbar.LENGTH_INDEFINITE)
                            .setAction(getString(R.string.tentar_novamente), new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    presenter.getMovies(mCurrentPage);
                                }
                            });

        snackbar.setActionTextColor(Color.RED);
        snackbar.show();
    }

    public CoordinatorLayout getCoordinatorLayout() {
        return ((ListMoviesActivity) getActivity()).getCoordinatorLayout();
    }

    private void setupAdapter() {

        if (mListMoviesAdapter == null) {
            mListMoviesAdapter = new ListMoviesAdapter(getActivity(), mListMovies);
            mRecyclerView.setAdapter(mListMoviesAdapter);
        } else {
            mListMoviesAdapter.notifyDataSetChanged();
        }
    }

    class ListMoviesAdapter extends RecyclerView.Adapter<ListMoviesViewHolder> {
        private List<Movie> list;
        private Context mContext;

        public ListMoviesAdapter(Context context, List<Movie> list) {
            this.list = list;
            this.mContext = context;
        }

        @Override
        public ListMoviesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View view = layoutInflater.inflate(R.layout.list_item_movies, parent, false);

            return new ListMoviesViewHolder(mContext, view);
        }

        @Override
        public void onBindViewHolder(ListMoviesViewHolder holder, int position) {
            holder.bindMovie(list.get(position));
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

    }
    class ListMoviesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.poster_movie)
        ImageView mImageView;

        @BindView(R.id.movie_title)
        TextView mTitle;

        @BindView(R.id.movie_subtitle)
        TextView mSubtitle;

        private Context mContext;
        private Movie mMovie;

        public ListMoviesViewHolder(final Context context, View itemView) {
            super(itemView);
            mContext = context;
            itemView.setOnClickListener(this);

            ButterKnife.bind(this, itemView);
        }

        public void bindMovie(Movie movie) {
            mMovie = movie;

            ImageUtils.load(getActivity(), movie.getPoster_path(), mImageView, ImageSize.LOGO_185);
            mTitle.setText(movie.getTitle());
            mSubtitle.setText(mContext.getString(R.string.title_original) + ": " + movie.getOriginal_title());
        }

        @Override
        public void onClick(View view) {
            Log.i(TAG, "Clicado!");
            mCallbacks.onMovieSelected(mMovie, mImageView);
        }
    }
}
