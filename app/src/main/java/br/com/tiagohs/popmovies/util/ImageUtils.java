package br.com.tiagohs.popmovies.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.pnikosis.materialishprogress.ProgressWheel;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.squareup.picasso.Transformation;

import java.io.File;
import java.io.FileNotFoundException;

import br.com.tiagohs.popmovies.R;
import br.com.tiagohs.popmovies.ui.tools.BitmatCreator;
import br.com.tiagohs.popmovies.util.enumerations.ImageSize;
import jp.wasabeef.picasso.transformations.BlurTransformation;

public class ImageUtils {

    public static void fixMediaDir() {
        File sdcard = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

        if (sdcard == null)
            sdcard = Environment.getExternalStorageDirectory();

        if (sdcard != null) {
            File mediaDir = new File(sdcard, "DCIM/Camera");
            if (!mediaDir.exists()) {
                mediaDir.mkdirs();
            }
        }
    }

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

    private static void load(Context context, ImageView imageView, String url, Integer path,
                             Drawable placeholderDrawable, Integer placeholderPath,
                             Drawable errorDrawable, Integer errorPath, boolean isToFit,
                             Transformation transformation, Callback callback) {

        Picasso loader = Picasso.get();
        RequestCreator request = null;

        if (EmptyUtils.isNotNull(url))
            request = loader.load(url);
        else if (EmptyUtils.isNotNull(path))
            request = loader.load(path);
        else
            request = loader.load("http://image.tmdb.org/t/p/0");

        if (EmptyUtils.isNotNull(placeholderDrawable))
            request.placeholder(placeholderDrawable);
        else if (EmptyUtils.isNotNull(placeholderPath))
            request.placeholder(placeholderPath);

        if (EmptyUtils.isNotNull(errorDrawable))
            request.error(errorDrawable);
        else if (EmptyUtils.isNotNull(errorPath))
            request.error(errorPath);

        if (EmptyUtils.isNotNull(transformation))
            request.transform(transformation);

        if (isToFit)
            request.fit();

        if (EmptyUtils.isNotNull(callback))
            request.into(imageView, callback);
        else
            request.into(imageView);
    }

    public static TextDrawable createTextDrawable(String text, boolean isRetangulo, ImageView imageView) {
        ColorGenerator generator = ColorGenerator.MATERIAL;

        TextDrawable.IShapeBuilder configBuilder = TextDrawable.builder()
                                                                .beginConfig()
                                                                .width(imageView.getWidth())
                                                                .height(imageView.getHeight())
                                                                .toUpperCase()
                                                                .endConfig();

        if (isRetangulo)
            return configBuilder.buildRect(String.valueOf(text.charAt(0)), generator.getRandomColor());
        else
            return configBuilder.buildRound(String.valueOf(text.charAt(0)), generator.getRandomColor());

    }
    public static void loadByCircularImage(final Context context, final String path, final ImageView imageView, final String name, final ImageSize imageSize) {
        Drawable placeholderAndError = createTextDrawable(name, false, imageView);
        load(context, imageView, context.getString(R.string.url_image, imageSize.getSize(), path), null, placeholderAndError, null, placeholderAndError, null, false, null, null);
    }

    public static void load(Context context, String path, final ImageView imageView, int placeholder, int imageError, ImageSize imageSize, final ProgressWheel progress) {
        progress.setVisibility(View.VISIBLE);
        imageView.setVisibility(View.GONE);

        load(context, imageView, context.getString(R.string.url_image, imageSize.getSize(), path),
                null, null, placeholder, null,
                imageError, false, null, new Callback() {
                                        @Override
                                        public void onSuccess() {
                                            progress.setVisibility(View.GONE);
                                            imageView.setVisibility(View.VISIBLE);
                                        }

                                        @Override
                                        public void onError(Exception e) {
                                            progress.setVisibility(View.GONE);
                                            imageView.setVisibility(View.VISIBLE);
                                        }
                                    });

    }

    public static void loadWithRevealAnimation(final Context context, String path, final ImageView imageView, int imageError, ImageSize imageSize, final Callback callback) {
        load(context, imageView, context.getString(R.string.url_image, imageSize.getSize(), path), null, null, null, null,
                imageError, false, null, new Callback() {
                    @Override
                    public void onSuccess() {
                        if (!((Activity) context).isDestroyed()) {
                            AnimationsUtils.createShowCircularReveal(imageView);
                            callback.onSuccess();
                        }

                    }

                    @Override
                    public void onError(Exception e) {
                        callback.onError(e);
                    }
                });
    }

    public static void loadWithRevealAnimation(final Context context, String path, final ImageView imageView, int imageError, ImageSize imageSize) {
        load(context, imageView, context.getString(R.string.url_image, imageSize.getSize(), path), null, null, null, null,
             imageError, false, null, new Callback() {
                                        @Override
                                        public void onSuccess() {
                                            if (!((Activity) context).isDestroyed()) {
                                                AnimationsUtils.createShowCircularReveal(imageView);
                                            }

                                        }

                                        @Override
                                        public void onError(Exception e) {}
                                    });
    }

    public static void load(Context context, String path, final ImageView imageView, int imageError, ImageSize imageSize) {
        load(context, imageView, context.getString(R.string.url_image, imageSize.getSize(), path), null, null, null, null, imageError, false, null, null);

    }

    public static void load(Context context, String path, final ImageView imageView, String name, ImageSize imageSize) {
        Drawable placeholderAndError = createTextDrawable(name, true, imageView);
        load(context, imageView, context.getString(R.string.url_image, imageSize.getSize(), path), null, placeholderAndError, null, placeholderAndError, null, false, null, null);
    }

    public static void loadWithBlur(final Context context, final String path, final ImageView imageView, final String name, final ImageSize imageSize) {

        imageView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                imageView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                Drawable placeholderAndError = createTextDrawable(name, true, imageView);
                load(context, imageView, context.getString(R.string.url_image, imageSize.getSize(), path), null, placeholderAndError, null, placeholderAndError, null, false, new BlurTransformation(context), null);
            }
        });
    }

    public static void loadWithBlur(final Context context, String path, final ImageView imageView, ImageSize imageSize) {
        load(context, imageView, context.getString(R.string.url_image, imageSize.getSize(), path), null, null, null, null, null, false, new BlurTransformation(context), null);
    }

    public static void loadWithBlur(final Context context, String path, final ImageView imageView, int imageError, ImageSize imageSize) {
        load(context, imageView, context.getString(R.string.url_image, imageSize.getSize(), path), null, null, null, null, imageError, false, new BlurTransformation(context), null);
    }

    public static void load(Context context, int pathImg, int placeholder, ImageView imageView) {
        new BitmatCreator(context).loadBitmap(pathImg, imageView, BitmapFactory.decodeResource(context.getResources(),
                placeholder));
    }

    public static void loadWithBlur(Context context, int pathImg, ImageView imageView) {
        load(context, imageView, null, pathImg, null, null, null, null, true, new BlurTransformation(context), null);
    }

    public static void load(Context context, String url, int placeholder, int imageError, final ImageView imageView, final ProgressWheel progressbar) {
        progressbar.setVisibility(View.VISIBLE);

        ViewCompat.setElevation(imageView, 12);

        load(context, imageView, url, null, null, placeholder, null,
                imageError, false, null, new Callback() {
                                                @Override
                                                public void onSuccess() {
                                                    progressbar.setVisibility(View.GONE);
                                                    imageView.setVisibility(View.VISIBLE);
                                                }
                                                @Override
                                                public void onError(Exception e) {
                                                    progressbar.setVisibility(View.GONE);
                                                    imageView.setVisibility(View.VISIBLE);
                                                }
                                            });
    }

    public static void load(final Context context, final String url, final String name, final ImageView imageView, final ProgressWheel progressbar) {

        imageView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                imageView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                progressbar.setVisibility(View.VISIBLE);
                Drawable placeholderAndError = createTextDrawable(name == null ? "PopMovies" : name.toUpperCase(), true, imageView);

                ViewCompat.setElevation(imageView, 12);

                load(context, imageView, url, null, placeholderAndError, null, placeholderAndError,
                        null, false, null, new Callback() {
                            @Override
                            public void onSuccess() {
                                progressbar.setVisibility(View.GONE);
                                imageView.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onError(Exception e) {
                                progressbar.setVisibility(View.GONE);
                                imageView.setVisibility(View.VISIBLE);
                            }
                        });}
        });


    }

    public static void load(final Context context, final String url, final String name, final int imageError, final ImageView imageView, final ProgressWheel progressbar) {

        imageView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                imageView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                progressbar.setVisibility(View.VISIBLE);
                Drawable placeholderAndError = createTextDrawable(name == null ? "PopMovies" : name.toUpperCase(), true, imageView);
                ViewCompat.setElevation(imageView, 12);

                load(context, imageView, url, null, placeholderAndError, null, placeholderAndError,
                        imageError, false, null, new Callback() {
                            @Override
                            public void onSuccess() {
                                progressbar.setVisibility(View.GONE);
                                imageView.setVisibility(View.VISIBLE);
                            }
                            @Override
                            public void onError(Exception e) {
                                progressbar.setVisibility(View.GONE);
                                imageView.setVisibility(View.VISIBLE);
                            }
                        });
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
