package br.com.tiagohs.popmovies.model.dto;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Tiago on 28/02/2017.
 */

public class ImageSizeDTO implements Parcelable {

    private String sizeName;
    private String size;

    public ImageSizeDTO(String sizeName, String size) {
        this.sizeName = sizeName;
        this.size = size;
    }

    public String getSizeName() {
        return sizeName;
    }

    public void setSizeName(String sizeName) {
        this.sizeName = sizeName;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return getSizeName();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.sizeName);
        dest.writeString(this.size);
    }

    protected ImageSizeDTO(Parcel in) {
        this.sizeName = in.readString();
        this.size = in.readString();
    }

    public static final Parcelable.Creator<ImageSizeDTO> CREATOR = new Parcelable.Creator<ImageSizeDTO>() {
        @Override
        public ImageSizeDTO createFromParcel(Parcel source) {
            return new ImageSizeDTO(source);
        }

        @Override
        public ImageSizeDTO[] newArray(int size) {
            return new ImageSizeDTO[size];
        }
    };
}
