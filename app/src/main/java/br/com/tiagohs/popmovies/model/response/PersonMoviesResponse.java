package br.com.tiagohs.popmovies.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.com.tiagohs.popmovies.model.credits.CreditMovieBasic;

public class PersonMoviesResponse implements Serializable, Parcelable {

    @JsonProperty("adult")
    private boolean adult;

    @JsonProperty("id")
    private int id;

    @JsonProperty("cast")
    private List<CreditMovieBasic> moviesByCast;

    @JsonProperty("crew")
    private List<CreditMovieBasic> moviesByCrew;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<CreditMovieBasic> getMoviesByCast() {
        return moviesByCast;
    }

    public void setMoviesByCast(List<CreditMovieBasic> moviesByCast) {
        this.moviesByCast = moviesByCast;
    }

    public List<CreditMovieBasic> getMoviesByCrew() {
        return moviesByCrew;
    }

    public void setMoviesByCrew(List<CreditMovieBasic> moviesByCrew) {
        this.moviesByCrew = moviesByCrew;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.adult ? (byte) 1 : (byte) 0);
        dest.writeInt(this.id);
        dest.writeTypedList(this.moviesByCast);
        dest.writeTypedList(this.moviesByCrew);
    }

    public PersonMoviesResponse() {
    }

    protected PersonMoviesResponse(Parcel in) {
        this.adult = in.readByte() != 0;
        this.id = in.readInt();
        this.moviesByCast = (ArrayList) in.createTypedArrayList(CreditMovieBasic.CREATOR);
        this.moviesByCrew = (ArrayList) in.createTypedArrayList(CreditMovieBasic.CREATOR);
    }

    public static final Parcelable.Creator<PersonMoviesResponse> CREATOR = new Parcelable.Creator<PersonMoviesResponse>() {
        @Override
        public PersonMoviesResponse createFromParcel(Parcel source) {
            return new PersonMoviesResponse(source);
        }

        @Override
        public PersonMoviesResponse[] newArray(int size) {
            return new PersonMoviesResponse[size];
        }
    };
}
