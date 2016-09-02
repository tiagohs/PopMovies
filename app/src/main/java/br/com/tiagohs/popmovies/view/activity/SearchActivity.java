package br.com.tiagohs.popmovies.view.activity;

import android.app.ActivityOptions;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.pnikosis.materialishprogress.ProgressWheel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.com.tiagohs.popmovies.R;
import br.com.tiagohs.popmovies.model.Movie.Movie;
import br.com.tiagohs.popmovies.presenter.SearchPresenter;
import br.com.tiagohs.popmovies.view.adapters.SearchAdapter;
import br.com.tiagohs.popmovies.view.fragment.EndlessRecyclerView;
import butterknife.BindView;

public class SearchActivity extends BaseActivity implements br.com.tiagohs.popmovies.view.SearchView, SearchView.OnQueryTextListener {

    @BindView(R.id.list_movies_recycler_view)
    RecyclerView mResultsRecyclerView;

    @BindView(R.id.search_progress)
    ProgressWheel mProgress;

    @Inject
    SearchPresenter mSearchPresenter;

    private int mCurrentPage = 1;
    private int mTotalPages;
    private String mQuery;
    private List<Movie> mListMovies = new ArrayList<>();

    private SearchAdapter mSearchAdapter;


    public static Intent newIntent(Context context) {
        return new Intent(context, SearchActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getApplicationComponent().inject(this);

        mSearchPresenter.setView(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        onConfigureRecyclerView();
    }

    private void onConfigureRecyclerView() {
        int columnCount = getResources().getInteger(R.integer.movies_columns);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mResultsRecyclerView.setLayoutManager(linearLayoutManager);
        mResultsRecyclerView.addOnScrollListener(new EndlessRecyclerView(linearLayoutManager) {

            @Override
            public void onLoadMore(int current_page) {
                if(mCurrentPage < mTotalPages)
                    search(mQuery, ++mCurrentPage);
            }
        });

        mResultsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        setupAdapter();

    }

    public void showProgressBar() {
        mProgress.setVisibility(View.VISIBLE);
    }

    public void hideProgressBar() {
        mProgress.setVisibility(View.GONE);
    }

    @Override
    public void atualizarView(int currentPage, int totalPages, List<Movie> listMovies) {
        if (mCurrentPage > 1)
            mListMovies.addAll(listMovies);
        else
            mSearchAdapter.setList(listMovies);

        mCurrentPage = currentPage;
        mTotalPages = totalPages;
        
        setupAdapter();
    }

    private void setupAdapter() {

        if (mSearchAdapter == null) {
            mSearchAdapter = new SearchAdapter(this, mListMovies, this);
            mResultsRecyclerView.setAdapter(mSearchAdapter);
        } else {
            mSearchAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        //Quando Clicamos no botão search.
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            search(intent.getStringExtra(SearchManager.QUERY), 1);

        //Suggestion
        } else if (Intent.ACTION_VIEW.equals(intent.getAction())) {
            Toast.makeText(this, "Ação! Suggestion: ", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search_activity, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.menu_item_search).getActionView();

        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setQueryHint("Procurar");
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(this);
        searchView.setFocusable(true);

        return true;
    }

    private void search(String query, int currentPage) {
        mCurrentPage = currentPage;
        mSearchPresenter.searchMovies(query, false, null, null, mCurrentPage);
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
        mQuery = query;
        mSearchPresenter.searchMovies(query, false, null, null, null);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        search(newText, 1);
        return false;
    }

}
