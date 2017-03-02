package br.com.tiagohs.popmovies.model.movie;

import android.os.Parcel;
import android.os.Parcelable;

import br.com.tiagohs.popmovies.model.IDNameAbstract;

public class Genre extends IDNameAbstract implements Parcelable {

    private int imgPath;

    public Genre() {

    }

    public Genre(int id, String name) {
        super(id, name);
    }


    public int getImgPath() {
        return imgPath;
    }

    public void setImgPath(int imgPath) {
        this.imgPath = imgPath;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.imgPath);
    }

    protected Genre(Parcel in) {
        this.imgPath = in.readInt();
    }

    public static final Parcelable.Creator<Genre> CREATOR = new Parcelable.Creator<Genre>() {
        @Override
        public Genre createFromParcel(Parcel source) {
            return new Genre(source);
        }

        @Override
        public Genre[] newArray(int size) {
            return new Genre[size];
        }
    };
}
