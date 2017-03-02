package br.com.tiagohs.popmovies.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import br.com.tiagohs.popmovies.model.movie.Genre;

/**
 * Created by Tiago Henrique on 03/09/2016.
 */
public class GenresResponse implements Parcelable {
    @JsonProperty("genres")
    List<Genre> genres;

    public GenresResponse() {
    }

    public GenresResponse(List<Genre> genres) {
        this.genres = genres;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.genres);
    }

    protected GenresResponse(Parcel in) {
        this.genres = in.createTypedArrayList(Genre.CREATOR);
    }

    public static final Parcelable.Creator<GenresResponse> CREATOR = new Parcelable.Creator<GenresResponse>() {
        @Override
        public GenresResponse createFromParcel(Parcel source) {
            return new GenresResponse(source);
        }

        @Override
        public GenresResponse[] newArray(int size) {
            return new GenresResponse[size];
        }
    };
}
