package br.com.tiagohs.popmovies.presenter;

import dagger.Module;
import dagger.Provides;

@Module
public class PresenterModule {

    @Provides
    public ListMoviesPresenter providesListMoviesPresenter() {
        return new ListMoviesPresenterImpl();
    }

    @Provides
    public MovieDetailsPresenter providesMovieDetailsPresenter() {
        return new MovieDetailsPresenterImpl();
    }

    @Provides
    public MovieDetailsMidiaPresenter providesMovieDetailsMidiaPresenter() {
        return new MovieDetailsMidiaPresenterImpl();
    }

    @Provides
    public SearchPresenter providesSearchPresenter() {
        return new SearchPresenterImpl();
    }
}
