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

    @Provides
    public GenresPresenter providesGenresPresenter() {
        return new GenresPresenterImpl();
    }

    @Provides
    public ListMoviesDefaultPresenter providesMoviesByGenrePresenter() {
        return new ListMoviesDefaultPresenterImpl();
    }

    @Provides
    public PersonDetailPresenter providesPersonDetailPresenter() {
        return new PersonDetailPresenterImpl();
    }

    @Provides
    public MovieDetailsOverviewPresenter providesMovieDetailsOverviewPresenter() {
        return new MovieDetailsOverviewPresenterImpl();
    }

    @Provides
    public MovieDetailsReviewsPresenter providesMovieDetailsReviewsPresenter() {
        return new MovieDetailsReviewsPresenterImpl();
    }

    @Provides
    public ListPersonsDefaultPresenter providesListPersonsDefaultPresenter() {
        return new ListPersonsDefaultPresenterImpl();
    }

}
