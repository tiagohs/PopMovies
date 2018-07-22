package br.com.tiagohs.popmovies.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.view.View;

import com.pnikosis.materialishprogress.ProgressWheel;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import br.com.tiagohs.popmovies.R;

public class ShareUtils {
    private static final String IMAGE_SHARE_TYPE = "image/jpeg";
    private static final String IMAGE_SHARE_CHOOSER_SEND = "send";

    public static void shareImageWithText(Context context, String pathToImage, String textExtra) {
        ImageUtils.fixMediaDir();

        try {
            Uri imageUri = Uri.parse(pathToImage);

            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);

            if (null != textExtra)
                shareIntent.putExtra(Intent.EXTRA_TEXT, textExtra);

            shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
            shareIntent.setType(IMAGE_SHARE_TYPE);
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            context.startActivity(Intent.createChooser(shareIntent, IMAGE_SHARE_CHOOSER_SEND));
        } catch (Exception ex) {
            ViewUtils.createToastMessage(context, context.getString(R.string.erro_unexpected));
        }

    }

    public static void shareImage(final Context context, String imageURL, final String imageName, final ProgressWheel progress) {
        progress.setVisibility(View.VISIBLE);

        Picasso.get()
                .load(imageURL)
                .into(new Target() {
                        @Override public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                            Intent i = new Intent(Intent.ACTION_SEND);
                            i.setType("image/*");
                            i.putExtra(Intent.EXTRA_STREAM, getLocalBitmapUri(context, bitmap, imageName));
                            context.startActivity(Intent.createChooser(i, context.getString(R.string.share_title)));
                            progress.setVisibility(View.GONE);
                        }

                        @Override
                        public void onBitmapFailed(Exception e, Drawable errorDrawable) {}

                        @Override public void onPrepareLoad(Drawable placeHolderDrawable) {}

        });
    }

    private static Uri getLocalBitmapUri(final Context context, Bitmap bmp, String imageName) {
        Uri bmpUri = null;
        try {
            File file =  new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), imageName);
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.close();
            bmpUri = Uri.fromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmpUri;
    }
}
