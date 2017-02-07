package br.com.tiagohs.popmovies;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.OkUrlFactory;
import com.squareup.picasso.LruCache;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import io.fabric.sdk.android.Fabric;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Executors;


public class App extends Application {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = " jdkgQcLjuDjupwwKRbtUoIAwE";
    private static final String TWITTER_SECRET = "XaXjpdRIQTybYvGYH8p9FesYKwNcJ8glGxo02XT96FwgNNtgNI";

    public static final String TAG = App.class.getSimpleName();
    private static App instance;

    private PopMoviesComponent mPopMoviesComponent;
    private RequestQueue mRequestQueue;

    private LruCache picassoCache;
    
    @Override
    public void onCreate() {
        super.onCreate();
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        mPopMoviesComponent = DaggerPopMoviesComponent.builder().build();

        instance = this;
        initPicasso();
    }

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    private void initPicasso() {
        Picasso.Builder builder = new Picasso.Builder(this);
        picassoCache = new LruCache(this);
        builder.memoryCache(picassoCache);
        Picasso.setSingletonInstance(builder.build());

        Picasso built = builder.build();
        built.setIndicatorsEnabled(false);
        built.setLoggingEnabled(true);

        //Picasso.setSingletonInstance(built);
    }

    public void clearCache() {
        picassoCache.clear();
    }

    public PopMoviesComponent getPopMoviesComponent() {
        return mPopMoviesComponent;
    }

    public static App getInstance() {
        return instance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext(), new OkHttpStack());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(tag);
        getRequestQueue().add(req);
    }

    public void cancelAll(String tag) {
        Log.i(TAG, "Cancell!" + tag);
        mRequestQueue.cancelAll(tag);
    }

    private class OkHttpStack extends HurlStack {

        private final OkUrlFactory okUrlFactory;

        public OkHttpStack() {
            this(new OkUrlFactory(new OkHttpClient()));
        }
        public OkHttpStack(OkUrlFactory okUrlFactory) {
            if (okUrlFactory == null) {
                throw new NullPointerException("Client must not be null.");
            }
            this.okUrlFactory = okUrlFactory;
        }
        @Override
        protected HttpURLConnection createConnection(URL url) throws IOException {
            return okUrlFactory.open(url);
        }
    }
}
