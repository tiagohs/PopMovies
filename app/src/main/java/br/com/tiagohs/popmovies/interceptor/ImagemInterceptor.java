package br.com.tiagohs.popmovies.interceptor;

import com.android.volley.VolleyError;

import br.com.tiagohs.popmovies.model.response.ImageResponse;

/**
 * Created by Tiago Henrique on 28/08/2016.
 */
public interface ImagemInterceptor {

    interface onImagemListener {

        void onImageRequestSucess(ImageResponse imageResponse);
        void onImageRequestError(VolleyError error);
    }

    void getImagens(int movieID);
}
