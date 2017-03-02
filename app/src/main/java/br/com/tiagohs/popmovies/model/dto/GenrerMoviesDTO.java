package br.com.tiagohs.popmovies.model.dto;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Tiago on 20/01/2017.
 */

public class GenrerMoviesDTO implements Parcelable {
    private int mGenrerID;
    private int mGenrerName;
    private Long mTotalMovies;

    public GenrerMoviesDTO() {
    }

    public GenrerMoviesDTO(int genrerID, int genrerName, Long totalMovies) {
        mGenrerID = genrerID;
        mGenrerName = genrerName;
        mTotalMovies = totalMovies;
    }

    public int getGenrerID() {
        return mGenrerID;
    }

    public void setGenrerID(int genrerID) {
        mGenrerID = genrerID;
    }

    public int getGenrerName() {
        return mGenrerName;
    }

    public void setGenrerName(int genrerName) {
        mGenrerName = genrerName;
    }

    public Long getTotalMovies() {
        return mTotalMovies;
    }

    public void setTotalMovies(Long totalMovies) {
        mTotalMovies = totalMovies;
    }

    @Override
    public boolean equals(Object obj) {

        if (obj instanceof GenrerMoviesDTO) {
            GenrerMoviesDTO g = (GenrerMoviesDTO) obj;
            return g.getGenrerID() == getGenrerID();
        }

        return false;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mGenrerID);
        dest.writeInt(this.mGenrerName);
        dest.writeValue(this.mTotalMovies);
    }

    protected GenrerMoviesDTO(Parcel in) {
        this.mGenrerID = in.readInt();
        this.mGenrerName = in.readInt();
        this.mTotalMovies = (Long) in.readValue(Long.class.getClassLoader());
    }

    public static final Parcelable.Creator<GenrerMoviesDTO> CREATOR = new Parcelable.Creator<GenrerMoviesDTO>() {
        @Override
        public GenrerMoviesDTO createFromParcel(Parcel source) {
            return new GenrerMoviesDTO(source);
        }

        @Override
        public GenrerMoviesDTO[] newArray(int size) {
            return new GenrerMoviesDTO[size];
        }
    };
}
