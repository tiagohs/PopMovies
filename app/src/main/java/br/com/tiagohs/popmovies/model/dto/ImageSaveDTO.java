package br.com.tiagohs.popmovies.model.dto;

import android.graphics.Bitmap;

/**
 * Created by Tiago on 22/01/2017.
 */

public class ImageSaveDTO {
    private Bitmap mBitmap;
    private String mPath;

    public ImageSaveDTO(Bitmap bitmap, String path) {
        mBitmap = bitmap;
        mPath = path;
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        mBitmap = bitmap;
    }

    public String getPath() {
        return mPath;
    }

    public void setPath(String path) {
        mPath = path;
    }
}
