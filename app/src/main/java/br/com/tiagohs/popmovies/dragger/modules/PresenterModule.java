package br.com.tiagohs.popmovies.dragger.modules;

import br.com.tiagohs.popmovies.ui.contracts.GenresContract;
import br.com.tiagohs.popmovies.ui.contracts.LancamentosSemanaContract;
import br.com.tiagohs.popmovies.ui.contracts.ListMoviesDefaultContract;
import br.com.tiagohs.popmovies.ui.contracts.ListPersonsDefaultContract;
import br.com.tiagohs.popmovies.ui.contracts.LoginContract;
import br.com.tiagohs.popmovies.ui.contracts.MovieDetailsContract;
import br.com.tiagohs.popmovies.ui.contracts.MovieDetailsMidiaContract;
import br.com.tiagohs.popmovies.ui.contracts.MovieDetailsOverviewContract;
import br.com.tiagohs.popmovies.ui.contracts.PerfilContract;
import br.com.tiagohs.popmovies.ui.contracts.PerfilEditContract;
import br.com.tiagohs.popmovies.ui.contracts.PerfilEstatisticasContract;
import br.com.tiagohs.popmovies.ui.contracts.PerfilFilmesContract;
import br.com.tiagohs.popmovies.ui.contracts.PersonDetailContract;
import br.com.tiagohs.popmovies.ui.contracts.SearchContract;
import br.com.tiagohs.popmovies.ui.contracts.VideosContract;
import br.com.tiagohs.popmovies.ui.interceptor.GenresInterceptor;
import br.com.tiagohs.popmovies.ui.presenter.GenresPresenter;
import br.com.tiagohs.popmovies.ui.presenter.LancamentosSemanaPresenter;
import br.com.tiagohs.popmovies.ui.presenter.ListMoviesDefaultPresenter;
import br.com.tiagohs.popmovies.ui.presenter.ListPersonsDefaultPresenter;
import br.com.tiagohs.popmovies.ui.presenter.LoginPresenter;
import br.com.tiagohs.popmovies.ui.presenter.MovieDetailsMidiaPresenter;
import br.com.tiagohs.popmovies.ui.presenter.MovieDetailsOverviewPresenter;
import br.com.tiagohs.popmovies.ui.presenter.MovieDetailsPresenter;
import br.com.tiagohs.popmovies.ui.presenter.PerfilEditPresenter;
import br.com.tiagohs.popmovies.ui.presenter.PerfilEstatisticasPresenter;
import br.com.tiagohs.popmovies.ui.presenter.PerfilFilmesPresenter;
import br.com.tiagohs.popmovies.ui.presenter.PerfilPresenter;
import br.com.tiagohs.popmovies.ui.presenter.PersonDetailPresenter;
import br.com.tiagohs.popmovies.ui.presenter.SearchPresenter;
import br.com.tiagohs.popmovies.ui.presenter.VideosPresenter;
import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

@Module
public class PresenterModule {

    @Provides
    public MovieDetailsContract.MovieDetailsPresenter providesMovieDetailsPresenter(MovieDetailsContract.MovieDetailsInterceptor movieDetailsInterceptor, CompositeDisposable compositeDisposable) {
        return new MovieDetailsPresenter(movieDetailsInterceptor, compositeDisposable);
    }

    @Provides
    public MovieDetailsMidiaContract.MovieDetailsMidiaPresenter providesMovieDetailsMidiaPresenter(MovieDetailsMidiaContract.MovieDetailsMidiaInterceptor movieDetailsMidiaInterceptor, CompositeDisposable compositeDisposable) {
        return new MovieDetailsMidiaPresenter(movieDetailsMidiaInterceptor, compositeDisposable);
    }

    @Provides
    public SearchContract.SearchPresenter providesSearchPresenter(SearchContract.SearchInterceptor searchMoviesInterceptor, CompositeDisposable subscribers) {
        return new SearchPresenter(searchMoviesInterceptor, subscribers);
    }

    @Provides
    public GenresContract.GenresPresenter providesGenresPresenter(GenresInterceptor genresInterceptor, CompositeDisposable subscribes) {
        return new GenresPresenter(genresInterceptor, subscribes);
    }

    @Provides
    public ListMoviesDefaultContract.ListMoviesDefaultPresenter providesMoviesByGenrePresenter(ListMoviesDefaultContract.ListMoviesDefaultInterceptor listMoviesDefaultInterceptor, CompositeDisposable subscribes) {
        return new ListMoviesDefaultPresenter(listMoviesDefaultInterceptor, subscribes);
    }

    @Provides
    public PersonDetailContract.PersonDetailPresenter providesPersonDetailPresenter(PersonDetailContract.PersonDetailInterceptor interceptor, CompositeDisposable subscribers) {
        return new PersonDetailPresenter(interceptor, subscribers);
    }

    @Provides
    public MovieDetailsOverviewContract.MovieDetailsOverviewPresenter providesMovieDetailsOverviewPresenter(MovieDetailsOverviewContract.MovieDetailsOverviewInterceptor movieDetailsOverviewInterceptor, CompositeDisposable subscribes) {
        return new MovieDetailsOverviewPresenter(movieDetailsOverviewInterceptor, subscribes);
    }

    @Provides
    public ListPersonsDefaultContract.ListPersonsDefaultPresenter providesListPersonsDefaultPresenter(ListPersonsDefaultContract.ListPersonsDefaultInterceptor listPersonsDefaultInterceptor, CompositeDisposable subscribes) {
        return new ListPersonsDefaultPresenter(listPersonsDefaultInterceptor, subscribes);
    }

    @Provides
    public PerfilContract.PerfilPresenter providesPerfilPresenter(PerfilContract.PerfilInterceptor perfilInterceptor, CompositeDisposable subscribers) {
        return new PerfilPresenter(perfilInterceptor, subscribers);
    }

    @Provides
    public VideosContract.VideosPresenter providesVideosPresenter(VideosContract.VideoInterceptor videoInterceptor, CompositeDisposable compositeDisposable) {
        return new VideosPresenter(videoInterceptor, compositeDisposable);
    }

    @Provides
    public PerfilEstatisticasContract.PerfilEstatisticasPresenter providesPerfilEstatisticasPresenter(PerfilEstatisticasContract.PerfilEstatisticasInterceptor interceptor, CompositeDisposable subscribers) {
        return new PerfilEstatisticasPresenter(interceptor, subscribers);
    }

    @Provides
    public PerfilEditContract.PerfilEditPresenter providesPerfilEditPresenter(PerfilEditContract.PerfilEditInterceptor interceptor, CompositeDisposable subscribers) {
        return new PerfilEditPresenter(interceptor, subscribers);
    }

    @Provides
    public LancamentosSemanaContract.LancamentosSemanaPresenter providesLancamentosSemanaPresenter() {
        return new LancamentosSemanaPresenter();
    }

    @Provides
    public LoginContract.LoginPresenter providesLoginPresenter(LoginContract.LoginInterceptor interceptor, CompositeDisposable subscribes) {
        return new LoginPresenter(interceptor, subscribes);
    }

    @Provides
    public PerfilFilmesContract.PerfilFilmesPresenter providesPerfilFilmesPresenter(PerfilFilmesContract.PerfilFilmesInterceptor interceptor, CompositeDisposable subscribers) {
        return  new PerfilFilmesPresenter(interceptor, subscribers);
    }
}
