package br.com.tiagohs.popmovies.model.movie;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class CollectionDetails implements Parcelable {

    @JsonProperty("id")
    private int id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("overview")
    private String overview;
    @JsonProperty("poster_path")
    private String posterPath;
    @JsonProperty("backdrop_path")
    private String backdropPath;
    @JsonProperty("parts")
    private List<Movie> movies = new ArrayList<>();

    public String getBackdropPath() {
        return backdropPath;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getOverview() {
        return overview;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.overview);
        dest.writeString(this.posterPath);
        dest.writeString(this.backdropPath);
        dest.writeTypedList(this.movies);
    }

    public CollectionDetails() {
    }

    protected CollectionDetails(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.overview = in.readString();
        this.posterPath = in.readString();
        this.backdropPath = in.readString();
        this.movies = (ArrayList) in.createTypedArrayList(Movie.CREATOR);
    }

    public static final Parcelable.Creator<CollectionDetails> CREATOR = new Parcelable.Creator<CollectionDetails>() {
        @Override
        public CollectionDetails createFromParcel(Parcel source) {
            return new CollectionDetails(source);
        }

        @Override
        public CollectionDetails[] newArray(int size) {
            return new CollectionDetails[size];
        }
    };
}
