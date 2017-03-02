package br.com.tiagohs.popmovies.model.db;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Tiago on 06/01/2017.
 */

public class GenreDB implements Parcelable {
    private long id;
    private int genrerID;
    private String genrerName;

    public GenreDB(long id, int genrerID, String genrerName) {
        this.id = id;
        this.genrerID = genrerID;
        this.genrerName = genrerName;
    }

    public GenreDB(long id, int genrerID) {
        this.id = id;
        this.genrerID = genrerID;
    }

    public GenreDB(int genrerID) {
        this.genrerID = genrerID;
    }

    public GenreDB() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getGenrerID() {
        return genrerID;
    }

    public void setGenrerID(int genrerID) {
        this.genrerID = genrerID;
    }

    public String getGenrerName() {
        return genrerName;
    }

    public void setGenrerName(String genrerName) {
        this.genrerName = genrerName;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeInt(this.genrerID);
        dest.writeString(this.genrerName);
    }

    protected GenreDB(Parcel in) {
        this.id = in.readLong();
        this.genrerID = in.readInt();
        this.genrerName = in.readString();
    }

    public static final Parcelable.Creator<GenreDB> CREATOR = new Parcelable.Creator<GenreDB>() {
        @Override
        public GenreDB createFromParcel(Parcel source) {
            return new GenreDB(source);
        }

        @Override
        public GenreDB[] newArray(int size) {
            return new GenreDB[size];
        }
    };
}
