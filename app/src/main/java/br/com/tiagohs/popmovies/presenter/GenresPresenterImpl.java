package br.com.tiagohs.popmovies.presenter;

import android.app.Activity;
import android.util.Log;

import com.android.volley.VolleyError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.tiagohs.popmovies.App;
import br.com.tiagohs.popmovies.R;
import br.com.tiagohs.popmovies.model.dto.GenrerMoviesDTO;
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
    public void onCancellRequest(Activity activity, String tag) {
        ((App) activity.getApplication()).cancelAll(tag);
    }

    @Override
    public void getGenres(String tag) {

        if (mGenresView.isInternetConnected())
            mMoviesServer.getGenres(tag, this);
        else
            mGenresView.onError(R.string.no_internet);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        mGenresView.onError(R.string.genre_error_network);
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
