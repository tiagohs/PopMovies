package br.com.tiagohs.popmovies.ui.view.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.View;

import br.com.tiagohs.popmovies.R;
import br.com.tiagohs.popmovies.ui.adapters.SearchTabAdapter;
import br.com.tiagohs.popmovies.ui.callbacks.SearchCallback;
import br.com.tiagohs.popmovies.util.EmptyUtils;
import butterknife.BindView;

public class SearchActivity extends BaseActivity implements SearchView.OnQueryTextListener {
    private static final String TAG = SearchActivity.class.getSimpleName();

    private static final String ARG_QUERY = "br.com.tiagohs.popmovies.query";

    @BindView(R.id.tabLayout)               TabLayout mTabLayout;
    @BindView(R.id.search_view_pager)       ViewPager mViewPager;

    private SearchView mSearchView;
    private SearchCallback mCallback;

    private String mQuery;

    private SearchTabAdapter mSearchTabAdapter;

    public static Intent newIntent(Context context) {
        return new Intent(context, SearchActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getApplicationComponent().inject(this);

        if (EmptyUtils.isNotNull(savedInstanceState)) {
            mQuery = savedInstanceState.getString(ARG_QUERY);

            if (EmptyUtils.isNotNull(mSearchView))
                mSearchView.setQuery(mQuery, false);

        }

        onSetupTabs();
    }

    private void onSetupTabs() {
        mSearchTabAdapter = new SearchTabAdapter(getSupportFragmentManager(), getResources().getStringArray(R.array.search_tab_array));
        mCallback = (SearchCallback) mSearchTabAdapter.getCurrentFragment(0);

        mViewPager.setAdapter(mSearchTabAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.addOnTabSelectedListener(createTabSelectedListener());
    }

    private TabLayout.OnTabSelectedListener createTabSelectedListener() {
        return new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                onTypeSearchChange(mSearchTabAdapter.getCurrentFragment(tab.getPosition()));
                onQueryTextSubmit(mQuery);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                onTypeSearchChange(mSearchTabAdapter.getCurrentFragment(tab.getPosition()));
                onQueryTextSubmit(mQuery);
            }
        };
    }

    private void onTypeSearchChange(Fragment callback) {
        mCallback = (SearchCallback) callback;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(ARG_QUERY, mQuery);
        super.onSaveInstanceState(outState);
    }


    @Override
    protected void onNewIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction()))
            mCallback.onQueryChanged(intent.getStringExtra(SearchManager.QUERY), true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search_activity, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        mSearchView = (SearchView) menu.findItem(R.id.menu_item_search).getActionView();

        mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        mSearchView.setQueryHint(getString(R.string.action_procurar));
        mSearchView.setIconifiedByDefault(false);
        mSearchView.setOnQueryTextListener(this);
        mSearchView.setFocusable(true);
        mSearchView.requestFocus();
        mSearchView.setQuery(EmptyUtils.isNotNull(mQuery) ? mQuery : "", false);

        return true;
    }

    @Override
    protected View.OnClickListener onSnackbarClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallback.onQueryChanged(mQuery, true);
                mSnackbar.dismiss();
            }
        };
    }

    @Override
    protected int getMenuLayoutID() {
        return 0;
    }

    @Override
    protected int getActivityBaseViewID() {
        return R.layout.activity_search;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        mSearchView.clearFocus();

        if (EmptyUtils.isNotNull(mCallback) && EmptyUtils.isNotNull(query != null))
            mCallback.onQueryChanged(query, true);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        mQuery = newText;

        if (EmptyUtils.isNotNull(mCallback) && EmptyUtils.isNotNull(mQuery))
            mCallback.onQueryChanged(newText, true);

        return true;
    }

}
