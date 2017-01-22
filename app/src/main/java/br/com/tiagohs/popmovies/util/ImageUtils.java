package br.com.tiagohs.popmovies.util;

import android.animation.Animator;
import android.app.Activity;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.pnikosis.materialishprogress.ProgressWheel;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;

import br.com.tiagohs.popmovies.util.enumerations.ImageSize;
import jp.wasabeef.picasso.transformations.BlurTransformation;

public class ImageUtils {

    public static Bitmap getBitmapFromPath(String path, Context context) {
        Uri imageURI = Uri.fromFile(new File(path));

        Bitmap bm = null;
        int[] sampleSizes = new int[]{5, 3, 2, 1};
        int i = 0;

        do {
            bm = decodeBitmap(context, imageURI, sampleSizes[i]);
            i++;
        } while (bm.getWidth() < 400 && i < sampleSizes.length);

        return bm;
    }

    private static Bitmap decodeBitmap(Context context, Uri theUri, int sampleSize) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = sampleSize;

        AssetFileDescriptor fileDescriptor = null;
        try {
            fileDescriptor = context.getContentResolver().openAssetFileDescriptor(theUri, "r");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Bitmap actuallyUsableBitmap = BitmapFactory.decodeFileDescriptor(
                fileDescriptor.getFileDescriptor(), null, options);

        return actuallyUsableBitmap;
    }

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

    public static void loadWithRevealAnimation(final Context context, String path, final ImageView imageView, int imageError, ImageSize imageSize) {

        Picasso.with(context)
                .load("http://image.tmdb.org/t/p/" + imageSize.getSize() + "/" + path)
                .error(imageError)
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        if (!((Activity) context).isDestroyed()) {
                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                                int cx = (imageView.getLeft() + imageView.getRight()) / 2;
                                int cy = (imageView.getTop() + imageView.getBottom()) / 2;
                                int finalRadius = Math.max(imageView.getWidth(), imageView.getHeight());

                                Animator anim = ViewAnimationUtils.createCircularReveal(imageView, cx, cy, 0, finalRadius);
                                anim.start();
                            }
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

    public static void loadWithBlur(final Context context, String path, final ImageView imageView, String name, ImageSize imageSize) {
        ColorGenerator generator = ColorGenerator.MATERIAL;

        TextDrawable drawable1 = TextDrawable.builder()
                .beginConfig()
                .withBorder(4)
                .endConfig()
                .buildRoundRect(String.valueOf(name.charAt(0)), generator.getRandomColor(), 10);

        Picasso.with(context)
                .load("http://image.tmdb.org/t/p/" + imageSize.getSize() + "/" + path)
                .placeholder(drawable1)
                .transform(new BlurTransformation(context))
                .error(drawable1)
                .into(imageView);
    }

    public static void loadWithBlur(final Context context, String path, final ImageView imageView, ImageSize imageSize) {

        Picasso.with(context)
                .load("http://image.tmdb.org/t/p/" + imageSize.getSize() + "/" + path)
                .transform(new BlurTransformation(context))
                .into(imageView);
    }

    public static void loadWithBlur(final Context context, String path, final ImageView imageView, int imageError, ImageSize imageSize) {

        Picasso.with(context)
                .load("http://image.tmdb.org/t/p/" + imageSize.getSize() + "/" + path)
                .transform(new BlurTransformation(context))
                .error(imageError)
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

    public static void loadWithBlur(Context context, int pathImg, ImageView imageView) {
        Picasso.with(context)
                .load(pathImg)
                .transform(new BlurTransformation(context))
                .into(imageView);
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

    private static final float BITMAP_SCALE = 0.4f;
    private static final float BLUR_RADIUS = 7.5f;

    public static Bitmap blur(Context context, ImageView imageView) {
        BitmapDrawable bitmapDrawable = (BitmapDrawable) imageView.getDrawable();
        Bitmap image = bitmapDrawable.getBitmap();

        int width = Math.round(image.getWidth() * BITMAP_SCALE);
        int height = Math.round(image.getHeight() * BITMAP_SCALE);

        Bitmap inputBitmap = Bitmap.createScaledBitmap(image, width, height, false);
        Bitmap outputBitmap = Bitmap.createBitmap(inputBitmap);

        RenderScript rs = RenderScript.create(context);
        ScriptIntrinsicBlur theIntrinsic = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
        Allocation tmpIn = Allocation.createFromBitmap(rs, inputBitmap);
        Allocation tmpOut = Allocation.createFromBitmap(rs, outputBitmap);
        theIntrinsic.setRadius(BLUR_RADIUS);
        theIntrinsic.setInput(tmpIn);
        theIntrinsic.forEach(tmpOut);
        tmpOut.copyTo(outputBitmap);

        return outputBitmap;
    }

}
