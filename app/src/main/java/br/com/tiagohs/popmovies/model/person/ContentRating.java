/*
 *      Copyright (c) 2004-2016 Stuart Boston
 *
 *      This file is part of TheMovieDB API.
 *
 *      TheMovieDB API is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      any later version.
 *
 *      TheMovieDB API is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU General Public License for more details.
 *
 *      You should have received a copy of the GNU General Public License
 *      along with TheMovieDB API.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package br.com.tiagohs.popmovies.model.person;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ContentRating implements Parcelable {

    @JsonProperty("iso_3166_1")
    private String country;
    @JsonProperty("rating")
    private String rating;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.country);
        dest.writeString(this.rating);
    }

    public ContentRating() {
    }

    protected ContentRating(Parcel in) {
        this.country = in.readString();
        this.rating = in.readString();
    }

    public static final Parcelable.Creator<ContentRating> CREATOR = new Parcelable.Creator<ContentRating>() {
        @Override
        public ContentRating createFromParcel(Parcel source) {
            return new ContentRating(source);
        }

        @Override
        public ContentRating[] newArray(int size) {
            return new ContentRating[size];
        }
    };
}
