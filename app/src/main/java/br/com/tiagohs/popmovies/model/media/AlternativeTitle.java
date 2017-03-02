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
package br.com.tiagohs.popmovies.model.media;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * @author Stuart
 */
public class AlternativeTitle implements Parcelable {

    @JsonProperty("iso_3166_1")
    private String country;
    @JsonProperty("title")
    private String title;

    public String getCountry() {
        return country;
    }

    public String getTitle() {
        return title;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof AlternativeTitle) {
            final AlternativeTitle other = (AlternativeTitle) obj;
            return new EqualsBuilder()
                    .append(country, other.country)
                    .append(title, other.title)
                    .isEquals();
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(country)
                .append(title)
                .toHashCode();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.country);
        dest.writeString(this.title);
    }

    public AlternativeTitle() {
    }

    protected AlternativeTitle(Parcel in) {
        this.country = in.readString();
        this.title = in.readString();
    }

    public static final Parcelable.Creator<AlternativeTitle> CREATOR = new Parcelable.Creator<AlternativeTitle>() {
        @Override
        public AlternativeTitle createFromParcel(Parcel source) {
            return new AlternativeTitle(source);
        }

        @Override
        public AlternativeTitle[] newArray(int size) {
            return new AlternativeTitle[size];
        }
    };
}
