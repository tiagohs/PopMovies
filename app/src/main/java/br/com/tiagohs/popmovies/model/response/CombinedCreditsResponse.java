package br.com.tiagohs.popmovies.model.response;


import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

import br.com.tiagohs.popmovies.model.credits.CreditMovieBasic;

public class CombinedCreditsResponse implements Parcelable {

    @JsonProperty("id")
    private int id;

    @JsonProperty("cast")
    private List<CreditMovieBasic> cast;

    @JsonProperty("crew")
    private List<CreditMovieBasic> crew;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<CreditMovieBasic> getCast() {
        return cast;
    }

    public List<CreditMovieBasic> getCrew() {
        return crew;
    }

    public void setCast(List<CreditMovieBasic> cast) {
        this.cast = cast;
    }

    public void setCrew(List<CreditMovieBasic> crew) {
        this.crew = crew;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeTypedList(this.cast);
        dest.writeTypedList(this.crew);
    }

    public CombinedCreditsResponse() {
    }

    protected CombinedCreditsResponse(Parcel in) {
        this.id = in.readInt();
        this.cast = (ArrayList) in.createTypedArrayList(CreditMovieBasic.CREATOR);
        this.crew = (ArrayList) in.createTypedArrayList(CreditMovieBasic.CREATOR);
    }

    public static final Parcelable.Creator<CombinedCreditsResponse> CREATOR = new Parcelable.Creator<CombinedCreditsResponse>() {
        @Override
        public CombinedCreditsResponse createFromParcel(Parcel source) {
            return new CombinedCreditsResponse(source);
        }

        @Override
        public CombinedCreditsResponse[] newArray(int size) {
            return new CombinedCreditsResponse[size];
        }
    };
}
