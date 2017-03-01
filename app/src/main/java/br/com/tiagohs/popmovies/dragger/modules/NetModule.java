package br.com.tiagohs.popmovies.dragger.modules;

import android.app.Application;

import com.squareup.okhttp.Cache;

import br.com.tiagohs.popmovies.dragger.scopes.PerFragment;
import br.com.tiagohs.popmovies.server.interceptor.ServerInterceptor;
import br.com.tiagohs.popmovies.server.methods.MoviesMethod;
import br.com.tiagohs.popmovies.server.services.MoviesService;
import br.com.tiagohs.popmovies.server.methods.PersonsMethod;
import br.com.tiagohs.popmovies.server.services.PersonsService;
import br.com.tiagohs.popmovies.ui.tools.SharedPreferenceManager;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Module
public class NetModule {

    String mBaseUrl;

    public NetModule(String baseUrl) {
        this.mBaseUrl = baseUrl;
    }

    @Provides
    Cache provideOkHttpCache(Application application) {
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        Cache cache = new Cache(application.getCacheDir(), cacheSize);
        return cache;
    }

    @Provides
    OkHttpClient provideOkHttpClient(ServerInterceptor serverInterceptor) {
        OkHttpClient okClient = new OkHttpClient.Builder()
                                .addInterceptor(serverInterceptor)
                                .build();

        return okClient;
    }

    @PerFragment
    @Provides
    ServerInterceptor providesServerInterceptor() {
        return new ServerInterceptor();
    }

    @Provides
    Retrofit provideRetrofit(OkHttpClient okHttpClient, RxJava2CallAdapterFactory rxJava2CallAdapterFactory) {
        Retrofit retrofit = new Retrofit.Builder()
                    .addConverterFactory(JacksonConverterFactory.create())
                    .addCallAdapterFactory(rxJava2CallAdapterFactory)
                    .baseUrl(mBaseUrl)
                    .client(okHttpClient)
                    .build();

        return retrofit;
    }

    @PerFragment
    @Provides
    MoviesService provideMovieService(Retrofit retrofit) {
        return retrofit.create(MoviesService.class);
    }

    @PerFragment
    @Provides
    PersonsService providesPersonService(Retrofit retrofit) {
        return retrofit.create(PersonsService.class);
    }

    @Provides
    MoviesMethod providesMovieMethod(MoviesService moviesService, SharedPreferenceManager sharedPreferenceManager) {
        return new MoviesMethod(moviesService, sharedPreferenceManager);
    }

    @Provides
    PersonsMethod procidesPersonsMethod(PersonsService personsService, SharedPreferenceManager sharedPreferenceManager) {
        return new PersonsMethod(personsService, sharedPreferenceManager);
    }

    @Provides
    RxJava2CallAdapterFactory provideRxJava2CallAdapterFactory() {
        return RxJava2CallAdapterFactory.create();
    }

}
