package br.com.tiagohs.popmovies.presenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Tiago Henrique on 20/08/2016.
 */
@Module
public class PresenterModule {

    @Provides
    public ListMoviesPresenter providesListMoviesPresenter() {
        return new ListMoviesPresenterImpl();
    }
}
