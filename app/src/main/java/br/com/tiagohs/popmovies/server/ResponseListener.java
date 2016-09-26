package br.com.tiagohs.popmovies.server;

import com.android.volley.Response;

public interface ResponseListener<T> extends Response.Listener<T>, Response.ErrorListener {
}
