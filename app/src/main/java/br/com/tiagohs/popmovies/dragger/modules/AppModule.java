package br.com.tiagohs.popmovies.dragger.modules;

import android.app.Application;

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
    public Application providesApplication() {
        return mApplication;
    }
}
