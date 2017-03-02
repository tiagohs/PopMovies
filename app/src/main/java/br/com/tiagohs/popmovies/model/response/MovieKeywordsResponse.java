package br.com.tiagohs.popmovies.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import br.com.tiagohs.popmovies.model.keyword.Keyword;

/**
 * Created by Tiago Henrique on 24/08/2016.
 */
public class MovieKeywordsResponse implements Parcelable {

    @JsonProperty("id")
    private int id;

    @JsonProperty("keywords")
    private List<Keyword> keywords;

    public List<Keyword> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<Keyword> keywords) {
        this.keywords = keywords;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeTypedList(this.keywords);
    }

    public MovieKeywordsResponse() {
    }

    protected MovieKeywordsResponse(Parcel in) {
        this.id = in.readInt();
        this.keywords = in.createTypedArrayList(Keyword.CREATOR);
    }

    public static final Parcelable.Creator<MovieKeywordsResponse> CREATOR = new Parcelable.Creator<MovieKeywordsResponse>() {
        @Override
        public MovieKeywordsResponse createFromParcel(Parcel source) {
            return new MovieKeywordsResponse(source);
        }

        @Override
        public MovieKeywordsResponse[] newArray(int size) {
            return new MovieKeywordsResponse[size];
        }
    };
}
