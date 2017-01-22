package br.com.tiagohs.popmovies;

import android.support.v7.widget.SearchView;

import javax.inject.Singleton;

import br.com.tiagohs.popmovies.presenter.PresenterModule;
import br.com.tiagohs.popmovies.view.activity.MovieDetailActivity;
import br.com.tiagohs.popmovies.view.activity.PerfilActivity;
import br.com.tiagohs.popmovies.view.activity.PerfilEditActivity;
import br.com.tiagohs.popmovies.view.activity.PersonDetailActivity;
import br.com.tiagohs.popmovies.view.activity.SearchActivity;
import br.com.tiagohs.popmovies.view.activity.VideosActivity;
import br.com.tiagohs.popmovies.view.callbacks.SearchCallback;
import br.com.tiagohs.popmovies.view.fragment.GenresFragment;
import br.com.tiagohs.popmovies.view.fragment.ListMoviesDefaultFragment;
import br.com.tiagohs.popmovies.view.fragment.ListPersonsDefaultFragment;
import br.com.tiagohs.popmovies.view.fragment.MovieDetailsMidiaFragment;
import br.com.tiagohs.popmovies.view.fragment.MovieDetailsOverviewFragment;
import br.com.tiagohs.popmovies.view.fragment.MovieDetailsReviewsFragment;
import br.com.tiagohs.popmovies.view.fragment.PerfilEstatisticasFragment;
import br.com.tiagohs.popmovies.view.fragment.SearchMoviesFragment;
import br.com.tiagohs.popmovies.view.fragment.SearchPersonsFragment;
import br.com.tiagohs.popmovies.view.fragment.VideosFragment;
import dagger.Component;

@Singleton
@Component(modules = {PresenterModule.class})
public interface PopMoviesComponent {

    void inject(MovieDetailActivity movieDetailActivity);
    void inject(MovieDetailsMidiaFragment movieDetailActivity);
    void inject(SearchActivity searchActivity);
    void inject(GenresFragment genresFragment);
    void inject(ListMoviesDefaultFragment listMoviesDefaultFragment);
    void inject(PersonDetailActivity personDetailActivity);
    void inject(MovieDetailsOverviewFragment movieDetailsOverviewFragment);
    void inject(MovieDetailsReviewsFragment movieDetailsReviewsFragment);
    void inject(ListPersonsDefaultFragment listPersonsDefaultFragment);
    void inject(PerfilActivity perfilActivity);
    void inject(VideosFragment videosFragment);
    void inject(SearchMoviesFragment searchMoviesFragment);
    void inject(SearchPersonsFragment searchMoviesFragment);
    void inject(PerfilEstatisticasFragment perfilEstatisticasFragment);
    void inject(PerfilEditActivity perfilEditActivity);
}
