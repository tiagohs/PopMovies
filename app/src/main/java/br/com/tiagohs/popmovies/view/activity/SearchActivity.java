package br.com.tiagohs.popmovies.view.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import br.com.tiagohs.popmovies.R;
import br.com.tiagohs.popmovies.view.adapters.SearchTabAdapter;
import br.com.tiagohs.popmovies.view.callbacks.SearchCallback;
import butterknife.BindView;

public class SearchActivity extends BaseActivity implements SearchView.OnQueryTextListener {
    private static final String TAG = SearchActivity.class.getSimpleName();
    private static final String ARG_QUERY = "br.com.tiagohs.popmovies.query";

    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;

    @BindView(R.id.search_view_pager)
    ViewPager mViewPager;

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

        if (savedInstanceState != null) {
            mQuery = savedInstanceState.getString(ARG_QUERY);

            if (mSearchView != null)
                mSearchView.setQuery(mQuery, false);

        }
        mSearchTabAdapter = new SearchTabAdapter(getSupportFragmentManager(), getResources().getStringArray(R.array.search_tab_array));
        mCallback = (SearchCallback) mSearchTabAdapter.getCurrentFragment(0);

        mViewPager.setAdapter(mSearchTabAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mCallback = (SearchCallback) mSearchTabAdapter.getCurrentFragment(tab.getPosition());
                if (mQuery != null)
                    onQueryTextSubmit(mQuery);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                mCallback = (SearchCallback) mSearchTabAdapter.getCurrentFragment(tab.getPosition());
                if (mQuery != null)
                    onQueryTextSubmit(mQuery);
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();

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
            mCallback.onQueryChanged(intent.getStringExtra(SearchManager.QUERY), true);

            //Suggestion
        } else if (Intent.ACTION_VIEW.equals(intent.getAction())) {
            Toast.makeText(this, "Ação! Suggestion: ", Toast.LENGTH_SHORT).show();
        }
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
        if (mCallback != null)
            mCallback.onQueryChanged(query, true);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        mQuery = newText;
        Log.i(TAG, "TAB" + mCallback );
        if (mCallback != null)
            mCallback.onQueryChanged(newText, true);
        return false;
    }

}
