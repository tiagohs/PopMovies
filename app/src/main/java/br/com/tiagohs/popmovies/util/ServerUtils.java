package br.com.tiagohs.popmovies.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ServerUtils {

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

    public static boolean isWifiConnected(Context context) {
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            Network[] network = connManager.getAllNetworks();
            if (network != null && network.length > 0) {
                for (int i = 0; i < network.length; i++) {
                    NetworkInfo networkInfo = connManager.getNetworkInfo(network[i]);
                    int networkType = networkInfo.getType();

                    if (ConnectivityManager.TYPE_WIFI == networkType)
                        return true;
                }
            }
        } else {
            NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            return mWifi.isConnected();
        }

        return false;
    }
}
