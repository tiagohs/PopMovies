package br.com.tiagohs.popmovies.ui.interceptor;

import java.util.List;

import javax.inject.Inject;

import br.com.tiagohs.popmovies.ui.contracts.GenresContract;
import br.com.tiagohs.popmovies.model.movie.Genre;
import br.com.tiagohs.popmovies.model.response.GenresResponse;
import br.com.tiagohs.popmovies.server.methods.MoviesMethod;
import br.com.tiagohs.popmovies.util.MovieUtils;
import io.reactivex.Observable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class GenresInterceptor implements GenresContract.GenresInterceptor {

    private MoviesMethod mMoviesMethod;

    @Inject
    public GenresInterceptor(MoviesMethod moviesMethod) {
        mMoviesMethod = moviesMethod;
    }

    @Override
    public Observable<List<Genre>> getGenres() {
        return mMoviesMethod.getGenres()
                            .subscribeOn(Schedulers.io())
                            .map(onMapGenres());
    }

    private Function<GenresResponse, List<Genre>> onMapGenres() {
        return new Function<GenresResponse, List<Genre>>() {
            @Override
            public List<Genre> apply(GenresResponse genresResponse) throws Exception {
                int[] genresID = MovieUtils.getAllGenrerIDs();
                int[] genresBackgroundID = MovieUtils.getAllGenrerBackgroundResoucers();

                for (Genre genre : genresResponse.getGenres()) {
                    for (int cont = 0; cont < genresID.length; cont++) {
                        if (genre.getId() == genresID[cont]) {
                            genre.setImgPath(genresBackgroundID[cont]);
                            break;
                        }
                    }
                }

                return genresResponse.getGenres();
            }
        };
    }
}
