package br.com.tiagohs.popmovies.server;

import com.android.volley.Response;

/**
 * Created by Tiago Henrique on 22/08/2016.
 */
public interface ResponseListener<T> extends Response.Listener<T>, Response.ErrorListener {
}
