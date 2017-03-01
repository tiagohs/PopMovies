package br.com.tiagohs.popmovies.dragger.modules;

import br.com.tiagohs.popmovies.ui.contracts.GenresContract;
import br.com.tiagohs.popmovies.ui.contracts.ListMoviesDefaultContract;
import br.com.tiagohs.popmovies.ui.contracts.ListPersonsDefaultContract;
import br.com.tiagohs.popmovies.ui.contracts.LoginContract;
import br.com.tiagohs.popmovies.ui.contracts.MovieDetailsContract;
import br.com.tiagohs.popmovies.ui.contracts.MovieDetailsMidiaContract;
import br.com.tiagohs.popmovies.ui.contracts.MovieDetailsOverviewContract;
import br.com.tiagohs.popmovies.ui.contracts.PerfilContract;
import br.com.tiagohs.popmovies.ui.contracts.PerfilEstatisticasContract;
import br.com.tiagohs.popmovies.ui.contracts.PerfilFilmesContract;
import br.com.tiagohs.popmovies.ui.contracts.PersonDetailContract;
import br.com.tiagohs.popmovies.ui.contracts.SearchContract;
import br.com.tiagohs.popmovies.ui.contracts.VideosContract;
import br.com.tiagohs.popmovies.database.repository.MovieRepository;
import br.com.tiagohs.popmovies.database.repository.ProfileRepository;
import br.com.tiagohs.popmovies.ui.interceptor.GenresInterceptor;
import br.com.tiagohs.popmovies.ui.interceptor.ListMoviesDefaultInterceptor;
import br.com.tiagohs.popmovies.ui.interceptor.ListPersonsDefaultInterceptor;
import br.com.tiagohs.popmovies.ui.interceptor.LoginInterceptor;
import br.com.tiagohs.popmovies.ui.interceptor.MovieDetailsInterceptor;
import br.com.tiagohs.popmovies.ui.interceptor.MovieDetailsMidiaInterceptor;
import br.com.tiagohs.popmovies.ui.interceptor.MovieDetailsOverviewInterceptor;
import br.com.tiagohs.popmovies.ui.interceptor.PerfilEstatisticasInterceptor;
import br.com.tiagohs.popmovies.ui.interceptor.PerfilFilmesInterceptor;
import br.com.tiagohs.popmovies.ui.interceptor.PerfilInterceptor;
import br.com.tiagohs.popmovies.ui.interceptor.PersonDetailInterceptor;
import br.com.tiagohs.popmovies.ui.interceptor.SearchInterceptor;
import br.com.tiagohs.popmovies.ui.interceptor.VideoInterceptor;
import br.com.tiagohs.popmovies.server.methods.MoviesMethod;
import br.com.tiagohs.popmovies.server.methods.PersonsMethod;
import dagger.Module;
import dagger.Provides;

@Module
public class InterceptorModule {

    @Provides
    public ListMoviesDefaultContract.ListMoviesDefaultInterceptor providesListMoviesDefaultInterceptor(MoviesMethod moviesMethod, PersonsMethod personsMethod,
                                                                                                       MovieRepository movieRepository, ProfileRepository profileRepository) {
        return new ListMoviesDefaultInterceptor(moviesMethod, personsMethod, movieRepository, profileRepository);
    }

    @Provides
    public GenresContract.GenresInterceptor providesGenresInterceptor(MoviesMethod moviesMethod) {
        return new GenresInterceptor(moviesMethod);
    }

    @Provides
    public ListPersonsDefaultContract.ListPersonsDefaultInterceptor providesListPersonsDefaultInterceptor(PersonsMethod personsMethod) {
        return new ListPersonsDefaultInterceptor(personsMethod);
    }

    @Provides
    public MovieDetailsMidiaContract.MovieDetailsMidiaInterceptor providesMovieDetailsMidiaInterceptor(MoviesMethod moviesMethod) {
        return new MovieDetailsMidiaInterceptor(moviesMethod);
    }

    @Provides
    public MovieDetailsOverviewContract.MovieDetailsOverviewInterceptor providesMovieDetailsOverviewInterceptor(MoviesMethod moviesMethod, MovieRepository movieRepository) {
        return new MovieDetailsOverviewInterceptor(moviesMethod, movieRepository);
    }

    @Provides
    public MovieDetailsContract.MovieDetailsInterceptor providesMovieDetailsInterceptor(MoviesMethod moviesMethod, MovieRepository movieRepository) {
        return new MovieDetailsInterceptor(moviesMethod, movieRepository);
    }

    @Provides
    public VideosContract.VideoInterceptor providesVideoInterceptor(MoviesMethod moviesMethod) {
        return new VideoInterceptor(moviesMethod);
    }

    @Provides
    public PerfilContract.PerfilInterceptor providesPerfilInterceptor(MoviesMethod moviesMethod, ProfileRepository profileRepository, MovieRepository movieRepository) {
        return new PerfilInterceptor(moviesMethod, profileRepository, movieRepository);
    }

    @Provides
    public PerfilEstatisticasContract.PerfilEstatisticasInterceptor providesPerfilEstatisticasInterceptor(ProfileRepository profileRepository) {
        return new PerfilEstatisticasInterceptor(profileRepository);
    }

    @Provides
    public PersonDetailContract.PersonDetailInterceptor providesPersonDetailInterceptor(PersonsMethod personsMethod) {
        return new PersonDetailInterceptor(personsMethod);
    }

    @Provides
    public SearchContract.SearchInterceptor providesSearchInterceptor(MoviesMethod moviesMethod, PersonsMethod personsMethod, MovieRepository movieRepository) {
        return new SearchInterceptor(moviesMethod, personsMethod, movieRepository);
    }

    @Provides
    public LoginContract.LoginInterceptor providesLoginInterceptor(ProfileRepository profileRepository) {
        return new LoginInterceptor(profileRepository);
    }

    @Provides
    public PerfilFilmesContract.PerfilFilmesInterceptor providesPerfilFilmesInterceptor(ProfileRepository profileRepository) {
        return new PerfilFilmesInterceptor(profileRepository);
    }
}
