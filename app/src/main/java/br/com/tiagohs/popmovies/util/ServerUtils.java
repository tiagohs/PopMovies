package br.com.tiagohs.popmovies.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Uri;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class ServerUtils {

    public static String getUrl(String baseUrl, Map<String, String> queryParams) {
        Uri.Builder urlBuilder = Uri.parse(baseUrl).buildUpon();
        if (queryParams != null) {
            for (String paramName : queryParams.keySet()) {
                urlBuilder.appendQueryParameter(paramName, queryParams.get(paramName));
            }
        }

        return urlBuilder.build().toString();
    }

    public static byte[] getUrlBytes(String urlSpec) throws IOException {
        URL url = new URL(urlSpec);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new IOException(connection.getResponseMessage() +
                        ": with " +
                        urlSpec);
            }
            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }
            out.close();
            return out.toByteArray();
        } finally {
            connection.disconnect();
        }
    }

    public static String getUrlString(String urlSpec) throws IOException {
        return new String(getUrlBytes(urlSpec));
    }

    public static boolean isNetworkConnected(Context context){
        try{
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            boolean isNetworkAvailable = cm.getActiveNetworkInfo() != null;
            boolean isNetworkConnected = isNetworkAvailable &&
                    cm.getActiveNetworkInfo().isConnected();
            return isNetworkConnected;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
