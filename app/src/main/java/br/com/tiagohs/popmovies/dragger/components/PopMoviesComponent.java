package br.com.tiagohs.popmovies.dragger.components;

import br.com.tiagohs.popmovies.dragger.modules.AppModule;
import br.com.tiagohs.popmovies.dragger.modules.GeneralModule;
import br.com.tiagohs.popmovies.dragger.modules.InterceptorModule;
import br.com.tiagohs.popmovies.dragger.modules.NetModule;
import br.com.tiagohs.popmovies.dragger.modules.PresenterModule;
import br.com.tiagohs.popmovies.dragger.modules.RepositoryModule;
import br.com.tiagohs.popmovies.dragger.scopes.PerFragment;
import br.com.tiagohs.popmovies.ui.view.activity.LoginActivity;
import br.com.tiagohs.popmovies.ui.view.activity.MovieDetailActivity;
import br.com.tiagohs.popmovies.ui.view.activity.PerfilActivity;
import br.com.tiagohs.popmovies.ui.view.activity.PersonDetailActivity;
import br.com.tiagohs.popmovies.ui.view.activity.SearchActivity;
import br.com.tiagohs.popmovies.ui.view.activity.SignUpActivity;
import br.com.tiagohs.popmovies.ui.view.fragment.GenresFragment;
import br.com.tiagohs.popmovies.ui.view.fragment.LancamentosSemanaFragment;
import br.com.tiagohs.popmovies.ui.view.fragment.ListMoviesDefaultFragment;
import br.com.tiagohs.popmovies.ui.view.fragment.ListPersonsDefaultFragment;
import br.com.tiagohs.popmovies.ui.view.fragment.MovieDetailsMidiaFragment;
import br.com.tiagohs.popmovies.ui.view.fragment.MovieDetailsOverviewFragment;
import br.com.tiagohs.popmovies.ui.view.fragment.PerfilEditFragment;
import br.com.tiagohs.popmovies.ui.view.fragment.PerfilEstatisticasFragment;
import br.com.tiagohs.popmovies.ui.view.fragment.PerfilFilmesFragment;
import br.com.tiagohs.popmovies.ui.view.fragment.SearchMoviesFragment;
import br.com.tiagohs.popmovies.ui.view.fragment.SearchPersonsFragment;
import br.com.tiagohs.popmovies.ui.view.fragment.VideosFragment;
import dagger.Component;

@PerFragment
@Component(modules = {PresenterModule.class,
                      AppModule.class,
                      NetModule.class,
                      GeneralModule.class,
                      InterceptorModule.class,
                      RepositoryModule.class})
public interface PopMoviesComponent {

    void inject(MovieDetailActivity movieDetailActivity);
    void inject(MovieDetailsMidiaFragment movieDetailActivity);
    void inject(SearchActivity searchActivity);
    void inject(GenresFragment genresFragment);
    void inject(ListMoviesDefaultFragment listMoviesDefaultFragment);
    void inject(PersonDetailActivity personDetailActivity);
    void inject(MovieDetailsOverviewFragment movieDetailsOverviewFragment);
    void inject(ListPersonsDefaultFragment listPersonsDefaultFragment);
    void inject(PerfilActivity perfilActivity);
    void inject(VideosFragment videosFragment);
    void inject(SearchMoviesFragment searchMoviesFragment);
    void inject(SearchPersonsFragment searchMoviesFragment);
    void inject(PerfilEstatisticasFragment perfilEstatisticasFragment);
    void inject(PerfilEditFragment perfilEditFragment);
    void inject(LancamentosSemanaFragment lancamentosSemanaFragment);
    void inject(LoginActivity loginActivity);
    void inject(SignUpActivity signUpActivity);
    void inject(PerfilFilmesFragment perfilFilmesFragment);

}
