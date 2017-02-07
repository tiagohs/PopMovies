package br.com.tiagohs.popmovies.server;

import android.util.ArrayMap;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class PopMovieRequest<T> extends Request<T> {
    private static final String TAG = PopMovieRequest.class.getSimpleName();
    private static final int DURATION_TIME_OUT  = 5000;
    private static final int MAX_RETRY = 0;

    private Map<String, String> mParameters;
    private Map<String, String> headers;
    private ResponseListener<T> listener;
    private TypeReference<T> typeReference;
    private ObjectMapper mObjectMapper;

    public PopMovieRequest(int method, String url, Map<String, String> headers, Map<String, String> parameters, TypeReference<T> typeToken, ResponseListener<T> listener) {
        super(method, url, listener);

        this.mParameters = parameters;
        this.headers = headers;
        this.typeReference = typeToken;
        this.listener = listener;

        setRetryPolicy(new DefaultRetryPolicy(DURATION_TIME_OUT, MAX_RETRY, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    //Realização do Request
    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            mObjectMapper = new ObjectMapper();
            T dataResponse = mObjectMapper.readValue(json, typeReference);

            return Response.success(dataResponse, HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
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
        return mParameters;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return headers != null ? headers : super.getHeaders();
    }
}
