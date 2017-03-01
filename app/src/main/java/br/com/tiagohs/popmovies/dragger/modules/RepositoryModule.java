package br.com.tiagohs.popmovies.dragger.modules;

import android.app.Application;

import br.com.tiagohs.popmovies.ui.contracts.PerfilEditContract;
import br.com.tiagohs.popmovies.database.DatabaseManager;
import br.com.tiagohs.popmovies.database.PopMoviesDB;
import br.com.tiagohs.popmovies.database.repository.GenreRepository;
import br.com.tiagohs.popmovies.database.repository.GenreRepositoryImpl;
import br.com.tiagohs.popmovies.database.repository.MovieRepository;
import br.com.tiagohs.popmovies.database.repository.MovieRepositoryImpl;
import br.com.tiagohs.popmovies.database.repository.ProfileRepository;
import br.com.tiagohs.popmovies.database.repository.ProfileRepositoryImpl;
import br.com.tiagohs.popmovies.database.repository.UserRepository;
import br.com.tiagohs.popmovies.database.repository.UserRepositoryImpl;
import br.com.tiagohs.popmovies.ui.interceptor.PerfilEditInterceptor;
import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {

    @Provides
    public MovieRepository providesMovieRepository(GenreRepository generRepository, DatabaseManager databaseManager) {
        return new MovieRepositoryImpl(generRepository, databaseManager);
    }

    @Provides
    public ProfileRepository providesProfileRepository(Application application, UserRepository userRepository, DatabaseManager databaseManager) {
        return new ProfileRepositoryImpl(application.getApplicationContext(), userRepository, databaseManager);
    }

    @Provides
    public GenreRepository providesGenreRepository(DatabaseManager databaseManager) {
        return new GenreRepositoryImpl(databaseManager);
    }

    @Provides
    public UserRepository providesUserRepository(DatabaseManager databaseManager) {
        return new UserRepositoryImpl(databaseManager);
    }

    @Provides
    public PerfilEditContract.PerfilEditInterceptor providesPerfilEditInterceptor(ProfileRepository profileRepository) {
        return new PerfilEditInterceptor(profileRepository);
    }
}
