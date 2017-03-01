package br.com.tiagohs.popmovies.dragger.modules;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import br.com.tiagohs.popmovies.database.DatabaseManager;
import br.com.tiagohs.popmovies.database.PopMoviesDB;
import br.com.tiagohs.popmovies.ui.tools.SharedPreferenceManager;
import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

@Module
public class GeneralModule {

    @Provides
    SharedPreferences providesSharedPreferences(Application application) {
        return PreferenceManager.getDefaultSharedPreferences(application);
    }

    @Provides
    SharedPreferenceManager providesSharedPreferenceManager(SharedPreferences sharedPreferences) {
        return new SharedPreferenceManager(sharedPreferences);
    }

    @Provides
    CompositeDisposable providesCompositeDisposable() {
        return new CompositeDisposable();
    }

    @Provides
    public PopMoviesDB providesPopMoviesDB(Application application) {
        return new PopMoviesDB(application.getApplicationContext());
    }

    @Provides
    public DatabaseManager providesDatabaseManager(PopMoviesDB popMoviesDB) {
        DatabaseManager.initializeInstance(popMoviesDB);
        return DatabaseManager.getInstance();
    }

}
