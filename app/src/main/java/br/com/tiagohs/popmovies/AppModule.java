package br.com.tiagohs.popmovies;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

//Modulo para a Apllication Context
@Module
public class AppModule {
    Application mApplication;

    public AppModule(Application application) {
        this.mApplication = application;
    }

    @Provides
    @Singleton
    public Application providesApplication() {
        return mApplication;
    }
}
