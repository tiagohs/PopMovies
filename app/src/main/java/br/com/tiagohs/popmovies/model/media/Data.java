package br.com.tiagohs.popmovies.model.media;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Data implements Parcelable {

    @JsonProperty("homepage")
    private String homepage;

    @JsonProperty("title")
    private String title;

    @JsonProperty("overview")
    private String englishName;

    public Data() {
    }

    public Data(String homepage, String title, String englishName) {
        this.homepage = homepage;
        this.title = title;
        this.englishName = englishName;
    }

    protected Data(Parcel in) {
        homepage = in.readString();
        title = in.readString();
        englishName = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(homepage);
        dest.writeString(title);
        dest.writeString(englishName);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Data> CREATOR = new Creator<Data>() {
        @Override
        public Data createFromParcel(Parcel in) {
            return new Data(in);
        }

        @Override
        public Data[] newArray(int size) {
            return new Data[size];
        }
    };

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }
}
