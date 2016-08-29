package br.com.tiagohs.popmovies;

import android.app.Application;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.OkUrlFactory;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;


public class App extends Application {
    public static final String TAG = App.class.getSimpleName();
    private static App instance;

    private PopMoviesComponent mPopMoviesComponent;
    private RequestQueue mRequestQueue;
    @Override
    public void onCreate() {
        super.onCreate();
        mPopMoviesComponent = DaggerPopMoviesComponent.builder().build();

        instance = this;
        initPicasso();
    }

    private void initPicasso() {
        Picasso.Builder builder = new Picasso.Builder(this);
        builder.downloader(new OkHttpDownloader(this,Integer.MAX_VALUE));

        Picasso built = builder.build();
        built.setIndicatorsEnabled(false);
        built.setLoggingEnabled(true);

        Picasso.setSingletonInstance(built);
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

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
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
