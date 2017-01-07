package br.com.tiagohs.popmovies.view.activity;

import android.app.ActivityOptions;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.pnikosis.materialishprogress.ProgressWheel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.com.tiagohs.popmovies.R;
import br.com.tiagohs.popmovies.model.movie.Movie;
import br.com.tiagohs.popmovies.presenter.SearchPresenter;
import br.com.tiagohs.popmovies.view.adapters.SearchAdapter;
import br.com.tiagohs.popmovies.view.fragment.EndlessRecyclerView;
import butterknife.BindView;

public class SearchActivity extends BaseActivity implements br.com.tiagohs.popmovies.view.SearchView, SearchView.OnQueryTextListener {
    private static final String TAG = SearchActivity.class.getSimpleName();
    private static final String ARG_QUERY = "br.com.tiagohs.popmovies.query";

    @BindView(R.id.list_movies_recycler_view)       RecyclerView mResultsRecyclerView;
    @BindView(R.id.search_progress)                 ProgressWheel mProgress;
    @BindView(R.id.nenhum_filme_encontrado)         TextView mNenhumFilmeEncontrado;

    @Inject
    SearchPresenter mSearchPresenter;

    private SearchView mSearchView;

    private boolean hasMorePages;
    private String mQuery;
    private List<Movie> mListMovies = new ArrayList<>();

    private SearchAdapter mSearchAdapter;
    private LinearLayoutManager mLinearLayout;

    public static Intent newIntent(Context context) {
        return new Intent(context, SearchActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getApplicationComponent().inject(this);

        mSearchPresenter.setView(this);
        mSearchPresenter.setContext(this);

        if (savedInstanceState != null) {
            mQuery = savedInstanceState.getString(ARG_QUERY);
            search(mQuery, true);

            if (mSearchView != null)
                mSearchView.setQuery(mQuery, false);

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (mSearchPresenter != null)
            mSearchPresenter.onCancellRequest(this, TAG);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(ARG_QUERY, mQuery);
        super.onSaveInstanceState(outState);
    }


    @Override
    protected void onNewIntent(Intent intent) {
        //Quando Clicamos no botão search.
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            search(intent.getStringExtra(SearchManager.QUERY), true);

            //Suggestion
        } else if (Intent.ACTION_VIEW.equals(intent.getAction())) {
            Toast.makeText(this, "Ação! Suggestion: ", Toast.LENGTH_SHORT).show();
        }
    }

    private void search(String query, boolean isNewSearch) {
        mSearchPresenter.searchMovies(query, false, null, TAG, null, isNewSearch);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search_activity, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        mSearchView = (SearchView) menu.findItem(R.id.menu_item_search).getActionView();

        // Assumes current activity is the searchable activity
        mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        mSearchView.setQueryHint("Procurar");
        mSearchView.setIconifiedByDefault(false);
        mSearchView.setOnQueryTextListener(this);
        mSearchView.setFocusable(true);
        mSearchView.setQuery(mQuery != null ? mQuery : "", false);

        return true;
    }

    @Override
    protected View.OnClickListener onSnackbarClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search(mQuery, true);
                mSnackbar.dismiss();
            }
        };
    }

    @Override
    protected int getMenuLayoutID() {
        return 0;
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
        mLinearLayout = new LinearLayoutManager(this);
        mResultsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mResultsRecyclerView.addOnScrollListener(createOnScrollListener());
        setupAdapter();
    }

    public void updateAdapter() {
        mSearchAdapter.notifyDataSetChanged();
    }

    private void setupAdapter() {
        mSearchAdapter = new SearchAdapter(this, mListMovies, this, mSearchPresenter);
        mResultsRecyclerView.setAdapter(mSearchAdapter);
    }

    private RecyclerView.OnScrollListener createOnScrollListener() {
        return new EndlessRecyclerView(mLinearLayout) {

            @Override
            public void onLoadMore(int current_page) {
                if(hasMorePages)
                    search(mQuery, false);
            }
        };
    }

    public void onMovieSelected(int movieID, ImageView posterMovie) {

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions transitionActivityOptions = ActivityOptions
                    .makeSceneTransitionAnimation(SearchActivity.this, posterMovie, getString(R.string.poster_movie));
            startActivity(MovieDetailActivity.newIntent(this, movieID), transitionActivityOptions.toBundle());
        } else {
            startActivity(MovieDetailActivity.newIntent(this, movieID));
        }

    }

    @Override
    protected int getActivityBaseViewID() {
        return R.layout.activity_search;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        search(query, true);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        mQuery = newText;
        search(newText, true);
        return false;
    }

    @Override
    public void setProgressVisibility(int visibityState) {
        mProgress.setVisibility(visibityState);
    }

    @Override
    public boolean isAdded() {
        return this != null;
    }

    public void setNenhumFilmeEncontradoVisibility(int visibility) {
        mNenhumFilmeEncontrado.setVisibility(visibility);
    }
}
