package br.com.tiagohs.popmovies.view.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import br.com.tiagohs.popmovies.R;
import br.com.tiagohs.popmovies.view.activity.WebViewActivity;
import butterknife.BindView;

public class WebViewFragment extends BaseFragment {

    @BindView(R.id.web_view_content)
    WebView mWebView;

    @BindView(R.id.web_view_progress)
    ProgressBar mWebViewProgress;

    private String mUrl;

    public static WebViewFragment newInstance(String url) {
        Bundle bundle = new Bundle();
        bundle.putString(WebViewActivity.ARG_URL, url);

        WebViewFragment webViewFragment = new WebViewFragment();
        webViewFragment.setArguments(bundle);

        return webViewFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        mUrl = getArguments().getString(WebViewActivity.ARG_URL);
    }

    @Override
    public void onStart() {
        super.onStart();

        if (isInternetConnected()) {
            configurateWebView();
            startUrl();
        } else {
            onError(getString(R.string.no_internet));
        }

    }


    private void configurateWebView() {
        mWebView.setWebChromeClient(createChromeCliente());
        mWebView.setWebViewClient(new PopMoviesBrowser());
        mWebView.getSettings().setLoadsImagesAutomatically(true);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
    }

    private void startUrl() {
        mWebView.loadUrl(mUrl);
    }

    private WebChromeClient createChromeCliente() {
        return new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress < 100)
                    mWebViewProgress.setProgress(newProgress);

            }
        };
    }


    @Override
    protected int getViewID() {
        return R.layout.fragment_web_view;
    }

    @Override
    protected View.OnClickListener onSnackbarClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                configurateWebView();
                startUrl();
                mSnackbar.dismiss();
            }
        };
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_web_view, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {
            case R.id.action_reflesh:
                configurateWebView();
                startUrl();
                return true;
            case android.R.id.home:
                getWebViewActivity().finish();
                return true;
            case R.id.menu_open_navegador:
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse(mUrl)));
                return true;
            default:
                return false;
        }
    }

    public WebViewActivity getWebViewActivity() {
        return (WebViewActivity) getActivity();
    }

    private class PopMoviesBrowser extends WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            mWebViewProgress.setVisibility(View.VISIBLE);
            if (isAdded())
                getWebViewActivity().setActionBarSubTitle(mWebView.getUrl());
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            mWebViewProgress.setVisibility(View.GONE);
            if (isAdded()) {
                getWebViewActivity().setActionBarSubTitle(view.getTitle());
            }
        }
    }
}
