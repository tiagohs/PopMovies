package br.com.tiagohs.popmovies.server.interceptor;

import java.io.IOException;

import br.com.tiagohs.popmovies.BuildConfig;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class ServerInterceptor implements Interceptor {
    private static final int RETRY = 3;

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        Request newRequest = original;

        String hostName = original.url().host();

        Request.Builder builder = original.newBuilder();

        if (hostName.contains("themoviedb")) {
            String newUrl = original.url().toString() + "&api_key=" + BuildConfig.MOVIEDB_API;
            newRequest = builder.url(newUrl).build();
        }

        //Log.i("URL", "Url " + newRequest.url().toString());

        Response response = chain.proceed(newRequest);

        int retryCount = 0;
        while (!response.isSuccessful() && retryCount < RETRY) {
            retryCount++;
            response = chain.proceed(newRequest);
        }

        return response;
    }
}
