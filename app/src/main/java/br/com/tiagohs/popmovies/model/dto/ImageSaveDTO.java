package br.com.tiagohs.popmovies.model.dto;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Tiago on 22/01/2017.
 */

public class ImageSaveDTO implements Parcelable {
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.mBitmap, flags);
        dest.writeString(this.mPath);
    }

    protected ImageSaveDTO(Parcel in) {
        this.mBitmap = in.readParcelable(Bitmap.class.getClassLoader());
        this.mPath = in.readString();
    }

    public static final Parcelable.Creator<ImageSaveDTO> CREATOR = new Parcelable.Creator<ImageSaveDTO>() {
        @Override
        public ImageSaveDTO createFromParcel(Parcel source) {
            return new ImageSaveDTO(source);
        }

        @Override
        public ImageSaveDTO[] newArray(int size) {
            return new ImageSaveDTO[size];
        }
    };
}
