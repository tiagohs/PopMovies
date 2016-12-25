package br.com.tiagohs.popmovies.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import br.com.tiagohs.popmovies.R;

/**
 * Created by Tiago on 22/12/2016.
 */

public class ImageSaver {
    private static final String TAG = ImageSaver.class.getSimpleName();

    private String mDirectoryName;
    private String mFileName;
    private Context mContext;
    private boolean mExternal;

    public ImageSaver(Context context) {
        this.mContext = context;

        mDirectoryName = "images";
        mFileName = "image.png";
    }

    public ImageSaver setFileName(String mFileName) {
        this.mFileName = mFileName;
        return this;
    }

    public ImageSaver setExternal(boolean mExternal) {
        this.mExternal = mExternal;
        return this;
    }

    public ImageSaver setDirectoryName(String mDirectoryName) {
        this.mDirectoryName = mDirectoryName;
        return this;
    }

    public void save(Bitmap bitmapImage) {
        new SaveImageTask().execute(bitmapImage);
    }

    @NonNull
    private File createFile() {
        File directory;
        if(mExternal){
            Log.i(TAG, "Modo External");
            directory = getAlbumStorageDir(mDirectoryName);
        }
        else {
            Log.i(TAG, "Modo Interno");
            directory = mContext.getDir(mDirectoryName, Context.MODE_PRIVATE);
        }

        Log.i(TAG, "Caminho " + directory);

        return new File(directory, mFileName);
    }

    private File getAlbumStorageDir(String albumName) {
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), albumName);
        if (!file.mkdirs()) {
            Log.e(TAG, "Diretório não criado");
        }
        return file;
    }

    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    public static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state);
    }

    public Bitmap load() {
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(createFile());
            return BitmapFactory.decodeStream(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private class SaveImageTask extends AsyncTask<Bitmap, Void, Void> {

        @Override
        protected Void doInBackground(Bitmap... params) {
            FileOutputStream fileOutputStream = null;
            try {
                fileOutputStream = new FileOutputStream(createFile());
                params[0].compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (fileOutputStream != null) {
                        fileOutputStream.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(Void... values) {}
    }

}
