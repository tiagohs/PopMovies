package br.com.tiagohs.popmovies.interceptor;

import com.android.volley.VolleyError;

import br.com.tiagohs.popmovies.model.movie.CollectionDetails;
import br.com.tiagohs.popmovies.server.ResponseListener;
import br.com.tiagohs.popmovies.server.methods.MoviesServer;

public class MovieCollectionInterceptorImpl implements MovieCollectionInterceptor, ResponseListener<CollectionDetails> {

    private MoviesServer mMoviesServer;
    private onMovieCollectionListener mListener;

    public MovieCollectionInterceptorImpl(onMovieCollectionListener listener) {
        mMoviesServer = new MoviesServer();
        mListener = listener;
    }

    @Override
    public void getMovieCollections(String tag, int collectionID) {
        mMoviesServer.getMovieCollections(tag, collectionID, this);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        mListener.onMovieCollectionRequestError(error);
    }

    @Override
    public void onResponse(CollectionDetails response) {
        mListener.onMovieCollectionRequestSucess(response);
    }
}
