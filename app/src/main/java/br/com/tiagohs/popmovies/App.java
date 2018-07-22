package br.com.tiagohs.popmovies;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.ads.MobileAds;
import com.squareup.picasso.LruCache;
import com.squareup.picasso.Picasso;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import br.com.tiagohs.popmovies.dragger.components.DaggerPopMoviesComponent;
import br.com.tiagohs.popmovies.dragger.components.PopMoviesComponent;
import br.com.tiagohs.popmovies.dragger.modules.AppModule;
import br.com.tiagohs.popmovies.dragger.modules.NetModule;
import io.fabric.sdk.android.Fabric;

public class App extends Application {

    public static final String TAG = App.class.getSimpleName();
    private static App instance;

    private PopMoviesComponent mPopMoviesComponent;

    private LruCache picassoCache;
    
    @Override
    public void onCreate() {
        super.onCreate();

        MultiDex.install(getApplicationContext());

        TwitterAuthConfig authConfig = new TwitterAuthConfig(BuildConfig.TWITTER_KEY, BuildConfig.TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig), new Crashlytics());

        mPopMoviesComponent = DaggerPopMoviesComponent.builder()
                              .appModule(new AppModule(this))
                              .netModule(new NetModule("http://api.themoviedb.org/3/"))
                              .build();

        MobileAds.initialize(this, BuildConfig.ADMOB_ID);
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

}
