package br.com.tiagohs.popmovies.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.MenuItem;
import android.view.View;

import br.com.tiagohs.popmovies.R;
import br.com.tiagohs.popmovies.view.fragment.WebViewFragment;

public class WebViewActivity extends BaseActivity {
    public static final String ARG_URL = "br.com.tiagohs.popmovies.url";
    public static final String ARG_TITLE = "br.com.tiagohs.popmovies.title_page";

    private String mUrl;
    private String mTitle;

    public static Intent newIntent(Context context, String url, String titlePage) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra(ARG_URL, url);
        intent.putExtra(ARG_TITLE, titlePage);

        return intent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        mUrl = getIntent().getStringExtra(ARG_URL);
        mTitle = getIntent().getStringExtra(ARG_TITLE);
    }

    @Override
    protected void onStart() {
        super.onStart();

        setActionBarTitle(mTitle);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.content_fragment);

        if (fragment == null) {
            fm.beginTransaction()
                    .add(R.id.content_fragment, WebViewFragment.newInstance(mUrl))
                    .commit();
        }
    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    public void setActionBarSubTitle(String subtitle) {
        getSupportActionBar().setSubtitle(subtitle);
    }


    @Override
    protected View.OnClickListener onSnackbarClickListener() {
        return null;
    }

    @Override
    protected int getMenuLayoutID() {
        return 0;
    }

    @Override
    protected int getActivityBaseViewID() {
        return R.layout.activity_default;
    }

}
