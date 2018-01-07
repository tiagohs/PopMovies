package br.com.tiagohs.popmovies.model.dto;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Tiago Henrique on 04/09/2016.
 */
public class ImageDTO implements Parcelable {
    private int movieID;
    private String imageID;
    private String imagePath;

    public ImageDTO(int movieID, String imageID, String imagePath) {
        this.movieID = movieID;
        this.imageID = imageID;
        this.imagePath = imagePath;
    }

    public ImageDTO() {
    }

    public ImageDTO(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getMovieID() {
        return movieID;
    }

    public void setMovieID(int movieID) {
        this.movieID = movieID;
    }

    public String getImageID() {
        return imageID;
    }

    public void setImageID(String imageID) {
        this.imageID = imageID;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public boolean equals(Object obj) {

        if (obj instanceof ImageDTO) {
            ImageDTO im = (ImageDTO) obj;
            return getImagePath() != null && this.getImagePath().equals(im.getImagePath());
        }
        return false;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.movieID);
        dest.writeString(this.imageID);
        dest.writeString(this.imagePath);
    }

    protected ImageDTO(Parcel in) {
        this.movieID = in.readInt();
        this.imageID = in.readString();
        this.imagePath = in.readString();
    }

    public static final Parcelable.Creator<ImageDTO> CREATOR = new Parcelable.Creator<ImageDTO>() {
        @Override
        public ImageDTO createFromParcel(Parcel source) {
            return new ImageDTO(source);
        }

        @Override
        public ImageDTO[] newArray(int size) {
            return new ImageDTO[size];
        }
    };
}
