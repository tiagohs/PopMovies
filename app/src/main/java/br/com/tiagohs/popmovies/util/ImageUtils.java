package br.com.tiagohs.popmovies.util;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import br.com.tiagohs.popmovies.R;

public class ImageUtils {

    public static void loadByCircularImage(Context context, String path, ImageView imageView, int imageDefault, ImageSize imageSize) {

        if (path == null) {
            Picasso.with(context)
                    .load(R.mipmap.ic_person)
                    .noFade()
                    .into(imageView);
        } else {
            Picasso.with(context)
                    .load("http://image.tmdb.org/t/p/" + imageSize.getSize() + path)
                    .noFade()
                    .into(imageView);
        }
    }

    public static void load(Context context, String path, ImageView imageView, ImageSize imageSize) {

        Picasso.with(context)
                .load("http://image.tmdb.org/t/p/" + imageSize.getSize() + "/" + path)
                .into(imageView);
    }

    public static void load(Context context, String url, ImageView imageView) {

        Picasso.with(context)
                .load(url)
                .into(imageView);
    }

}
