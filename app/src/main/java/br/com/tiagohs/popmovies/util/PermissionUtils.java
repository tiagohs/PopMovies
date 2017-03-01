package br.com.tiagohs.popmovies.util;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;
import java.util.List;

import br.com.tiagohs.popmovies.R;

public class PermissionUtils {
    public static final String[] permissions = new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                                             Manifest.permission.READ_EXTERNAL_STORAGE};

    public static boolean validate(Activity context) {
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

    public static boolean validatePermission(final Activity activity, final String permission, String messageError) {
        if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                new MaterialDialog.Builder(activity)
                        .title(activity.getString(R.string.permission_error_title))
                        .content(messageError)
                        .positiveText(R.string.btn_ok)
                        .negativeText(R.string.btn_no_thanks)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                ActivityCompat.requestPermissions(activity, new String[]{permission}, 0);
                            }
                        })
                        .show();
                return false;
            } else {
                ActivityCompat.requestPermissions(activity, new String[]{permission}, 0);
            }
        } else {
            return true;
        }

        return false;
    }

    public static boolean onRequestPermissionsResultValidate(@NonNull int[] grantResults, int requestCode) {

       if (requestCode == 0)
            return grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED;

       return false;
    }
}
