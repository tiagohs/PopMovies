package br.com.tiagohs.popmovies.presenter;

import android.app.Activity;

import com.android.volley.VolleyError;

import javax.inject.Inject;

import br.com.tiagohs.popmovies.App;
import br.com.tiagohs.popmovies.R;
import br.com.tiagohs.popmovies.model.movie.Genre;
import br.com.tiagohs.popmovies.model.response.GenresResponse;
import br.com.tiagohs.popmovies.server.ResponseListener;
import br.com.tiagohs.popmovies.server.methods.MoviesServer;
import br.com.tiagohs.popmovies.util.MovieUtils;
import br.com.tiagohs.popmovies.view.GenresView;

public class GenresPresenterImpl implements GenresPresenter, ResponseListener<GenresResponse> {
    private static final String TAG = GenresPresenterImpl.class.getSimpleName();

    private GenresView mGenresView;
    private MoviesServer mMoviesServer;

    private int[] mGenresID = MovieUtils.getAllGenrerIDs();
    private int[] mGenresBackgroundID = MovieUtils.getAllGenrerBackgroundResoucers();

    public GenresPresenterImpl() {
        mMoviesServer = new MoviesServer();
    }

    @Override
    public void setView(GenresView view) {
        this.mGenresView = view;
    }

    @Override
    public void getGenres(String tag) {

        if (mGenresView.isInternetConnected())
            mMoviesServer.getGenres(tag, this);
        else
            mGenresView.onErrorNoConnection();
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        if (mGenresView.isAdded())
            mGenresView.onErrorInServer();
    }

    @Override
    public void onResponse(GenresResponse response) {
        mGenresID = MovieUtils.getAllGenrerIDs();
        mGenresBackgroundID = MovieUtils.getAllGenrerBackgroundResoucers();

        for (Genre genre : response.getGenres()) {
            for (int cont = 0; cont < mGenresID.length; cont++) {
                if (genre.getId() == mGenresID[cont]) {
                    genre.setImgPath(mGenresBackgroundID[cont]);
                    break;
                }
            }
        }

        mGenresView.updateView(response.getGenres());
    }

}
