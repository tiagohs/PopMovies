package br.com.tiagohs.popmovies.presenter;

import br.com.tiagohs.popmovies.view.GenresView;
import dagger.Module;
import dagger.Provides;

@Module
public class PresenterModule {

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
    public ListPersonsDefaultPresenter providesListPersonsDefaultPresenter() {
        return new ListPersonsDefaultPresenterImpl();
    }

    @Provides
    public PerfilPresenter providesPerfilPresenter() {
        return new PerfilPresenterImpl();
    }

    @Provides
    public VideosPresenter providesVideosPresenter() {
        return new VideosPresenterImpl();
    }

    @Provides
    public PerfilEstatisticasPresenter providesPerfilEstatisticasPresenter() {
        return new PerfilEstatisticasPresenterImpl();
    }

    @Provides
    public PerfilEditPresenter providesPerfilEditPresenter() {
        return new PerfilEditPresenterImpl();
    }

    @Provides
    public LancamentosSemanaPresenter providesLancamentosSemanaPresenter() {
        return new LancamentosSemanaPresenterImpl();
    }
}
