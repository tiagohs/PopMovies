package br.com.tiagohs.popmovies.util;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;

import com.pnikosis.materialishprogress.ProgressWheel;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import br.com.tiagohs.popmovies.util.enumerations.ImageSize;

public class ImageUtils {


    public static void loadByCircularImage(Context context, String path, final ImageView imageView, int imagePlaceholder, int imageError, ImageSize imageSize, final ProgressWheel progress) {
        progress.setVisibility(View.VISIBLE);

        Picasso.with(context)
                .load("http://image.tmdb.org/t/p/" + imageSize.getSize() + path)
                .placeholder(imagePlaceholder)
                .error(imageError)
                .noFade()
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        progress.setVisibility(View.GONE);
                        imageView.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onError() {
                        progress.setVisibility(View.GONE);
                        imageView.setVisibility(View.VISIBLE);
                    }
                });

    }

    public static void load(Context context, String path, final ImageView imageView, int placeholder, int imageError, ImageSize imageSize, final ProgressWheel progress) {
        progress.setVisibility(View.VISIBLE);
        imageView.setVisibility(View.GONE);

        Picasso.with(context)
                .load("http://image.tmdb.org/t/p/" + imageSize.getSize() + "/" + path)
                .placeholder(placeholder)
                .error(imageError)
                .into(imageView, new Callback() {
                            @Override
                            public void onSuccess() {
                                progress.setVisibility(View.GONE);
                                imageView.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onError() {
                                progress.setVisibility(View.GONE);
                                imageView.setVisibility(View.VISIBLE);
                            }
                });

    }

    public static void load(Context context, int pathImg, int placeholder, ImageView imageView) {
        new BitmatCreator(context).loadBitmap(pathImg, imageView, BitmapFactory.decodeResource(context.getResources(),
                placeholder));
    }

    public static void load(Context context, String url, int placeholder, int imageError, final ImageView imageView, final ProgressWheel progressbar) {
        progressbar.setVisibility(View.VISIBLE);

        Picasso.with(context)
                .load(url)
                .placeholder(placeholder)
                .error(imageError)
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        progressbar.setVisibility(View.GONE);
                        imageView.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onError() {
                        progressbar.setVisibility(View.GONE);
                        imageView.setVisibility(View.VISIBLE);
                    }
                });
    }

}
