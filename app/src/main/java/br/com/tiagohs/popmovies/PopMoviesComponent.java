package br.com.tiagohs.popmovies;

import javax.inject.Singleton;

import br.com.tiagohs.popmovies.presenter.PresenterModule;
import br.com.tiagohs.popmovies.view.activity.MovieDetailActivity;
import br.com.tiagohs.popmovies.view.activity.PersonDetailActivity;
import br.com.tiagohs.popmovies.view.activity.SearchActivity;
import br.com.tiagohs.popmovies.view.fragment.GenresFragment;
import br.com.tiagohs.popmovies.view.fragment.ListMoviesFragment;
import br.com.tiagohs.popmovies.view.fragment.MovieDetailsMidiaFragment;
import br.com.tiagohs.popmovies.view.fragment.MoviesByGenreFragment;
import dagger.Component;

@Singleton
@Component(modules = {PresenterModule.class})
public interface PopMoviesComponent {

    void inject(ListMoviesFragment listMoviesFragment);
    void inject(MovieDetailActivity movieDetailActivity);
    void inject(MovieDetailsMidiaFragment movieDetailActivity);
    void inject(SearchActivity searchActivity);
    void inject(GenresFragment genresFragment);
    void inject(MoviesByGenreFragment moviesByGenreFragment);
    void inject(PersonDetailActivity personDetailActivity);
}
