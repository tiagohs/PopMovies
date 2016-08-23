package br.com.tiagohs.popmovies.util;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class ImageUtils {

    public static void load(Context context, String path, ImageView imageView, ImageSize imageSize) {

        Picasso.with(context)
                .load("http://image.tmdb.org/t/p/" + imageSize.getSize() + "/" + path)
                .into(imageView);
    }
}
