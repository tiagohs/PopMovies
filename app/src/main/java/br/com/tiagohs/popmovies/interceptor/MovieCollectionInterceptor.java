package br.com.tiagohs.popmovies.interceptor;

import com.android.volley.VolleyError;

import br.com.tiagohs.popmovies.model.movie.CollectionDetails;

/**
 * Created by Tiago on 11/02/2017.
 */

public interface MovieCollectionInterceptor {

    interface onMovieCollectionListener {

        void onMovieCollectionRequestSucess(CollectionDetails response);
        void onMovieCollectionRequestError(VolleyError error);
    }

    void getMovieCollections(String tag, int collectionID);
}
