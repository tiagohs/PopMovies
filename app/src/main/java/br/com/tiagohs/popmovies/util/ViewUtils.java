package br.com.tiagohs.popmovies.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;

public class ViewUtils {

    public static int getColorFromResource(Context context, int resourceID) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            return context.getColor(resourceID);
        else
            return context.getResources().getColor(resourceID);
    }

    public static Drawable getDrawableFromResource(Context context, int drawableID) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            return context.getDrawable(drawableID);
        else
            return context.getResources().getDrawable(drawableID);
    }


}
