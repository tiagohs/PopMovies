package br.com.tiagohs.popmovies.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tiago on 25/12/2016.
 */

public class PermissionUtils {
    public static final String[] permissions = new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                                             Manifest.permission.READ_EXTERNAL_STORAGE};

    public static boolean validate(Activity context, int requestCode) {
        List<String> list = new ArrayList<>();

        for (String permission : permissions) {
            if (!(ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED))
                list.add(permission);
        }

        if (list.isEmpty())
            return true;

        String[] permissionsNegadas = new String[list.size()];
        list.toArray(permissionsNegadas);

        ActivityCompat.requestPermissions(context, permissionsNegadas, 1);
        return false;
    }
}
