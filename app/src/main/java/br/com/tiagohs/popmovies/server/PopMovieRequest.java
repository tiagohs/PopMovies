package br.com.tiagohs.popmovies.server;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public class PopMovieRequest<T> extends Request<T> {
    private static final String TAG = PopMovieRequest.class.getSimpleName();
    private static final int DURATION_TIME_OUT  = 5000;
    private static final int MAX_RETRY = 0;

    private GsonBuilder mGsonBuilder = new GsonBuilder().setDateFormat("dd-MM-yyyy HH:mm:ss");
    private Gson mGson = mGsonBuilder.create();

    private Map<String, String> params;
    private Map<String, String> headers;
    private ResponseListener<T> listener;
    private TypeToken<T> typeToken;

    public PopMovieRequest(int method, String url, Map<String, String> headers, Map<String, String> params, TypeToken<T> typeToken, ResponseListener<T> listener) {
        super(method, url, listener);

        this.headers = headers;
        this.params = params;
        this.typeToken = typeToken;
        this.listener = listener;

        setRetryPolicy(new DefaultRetryPolicy(DURATION_TIME_OUT, MAX_RETRY, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    //Realização do Request
    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            Log.i(TAG, json);
            T dataResponse = mGson.fromJson(json, typeToken.getType());
            return Response.success(dataResponse, HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            Log.e(TAG, "Erro na recuperação do Json.", e);
            e.printStackTrace();
        }

        return null;
    }

    //Resultado do Request
    @Override
    protected void deliverResponse(T dataResponse) {
        if (listener != null) {
            listener.onResponse(dataResponse);
        }
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return headers != null ? headers : super.getHeaders();
    }
}
