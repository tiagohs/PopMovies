package br.com.tiagohs.popmovies.util;

import android.animation.Animator;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.pnikosis.materialishprogress.ProgressWheel;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import br.com.tiagohs.popmovies.util.enumerations.ImageSize;

public class ImageUtils {


    public static void loadByCircularImage(Context context, String path, final ImageView imageView, String name, ImageSize imageSize) {
        ColorGenerator generator = ColorGenerator.MATERIAL;

        TextDrawable drawable1 = TextDrawable.builder()
                .beginConfig()
                .withBorder(4)
                .endConfig()
                .buildRound(String.valueOf(name.charAt(0)), generator.getRandomColor());

        Picasso.with(context)
                .load("http://image.tmdb.org/t/p/" + imageSize.getSize() + path)
                .placeholder(drawable1)
                .error(drawable1)
                .into(imageView);

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

    public static void loadWithRevealAnimation(Context context, String path, final ImageView imageView, int imageError, ImageSize imageSize) {

        Picasso.with(context)
                .load("http://image.tmdb.org/t/p/" + imageSize.getSize() + "/" + path)
                .error(imageError)
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {

                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                            int cx = (imageView.getLeft() + imageView.getRight()) / 2;
                            int cy = (imageView.getTop() + imageView.getBottom()) / 2;
                            int finalRadius = Math.max(imageView.getWidth(), imageView.getHeight());

                            Animator anim = ViewAnimationUtils.createCircularReveal(imageView, cx, cy, 0, finalRadius);
                            anim.start();
                        }

                    }

                    @Override
                    public void onError() {

                    }
                });
    }

    public static void load(Context context, String path, final ImageView imageView, int imageError, ImageSize imageSize) {
        Picasso.with(context)
                .load("http://image.tmdb.org/t/p/" + imageSize.getSize() + "/" + path)
                .error(imageError)
                .into(imageView);

    }

    public static void load(Context context, String path, final ImageView imageView, String name, ImageSize imageSize) {
        ColorGenerator generator = ColorGenerator.MATERIAL;

        TextDrawable drawable1 = TextDrawable.builder()
                .beginConfig()
                .withBorder(4)
                .endConfig()
                .buildRoundRect(String.valueOf(name.charAt(0)), generator.getRandomColor(), 10);

        Picasso.with(context)
                .load("http://image.tmdb.org/t/p/" + imageSize.getSize() + "/" + path)
                .placeholder(drawable1)
                .error(drawable1)
                .into(imageView);
    }

    public static void load(Context context, String path, final ImageView imageView, String name, ImageSize imageSize, final ViewGroup rodapeImage) {
        ColorGenerator generator = ColorGenerator.MATERIAL;
        rodapeImage.setVisibility(View.GONE);

        TextDrawable drawable1 = TextDrawable.builder()
                .beginConfig()
                .withBorder(4)
                .endConfig()
                .buildRoundRect(String.valueOf(name.charAt(0)), generator.getRandomColor(), 10);

        Picasso.with(context)
                .load("http://image.tmdb.org/t/p/" + imageSize.getSize() + "/" + path)
                .placeholder(drawable1)
                .error(drawable1)
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        rodapeImage.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onError() {
                        rodapeImage.setVisibility(View.VISIBLE);
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
