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
package br.com.tiagohs.popmovies.model.movie;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.List;

/**
 * @author Stuart
 */
public class ReleaseInfo implements Parcelable {

    private static final long serialVersionUID = 100L;

    @JsonProperty("iso_3166_1")
    private String country;
    @JsonProperty("release_dates")
    private List<ReleaseDate> releaseDates;

    public ReleaseInfo() {
    }

    public ReleaseInfo(String country) {
        this.country = country;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setReleaseDates(List<ReleaseDate> releaseDates) {
        this.releaseDates = releaseDates;
    }

    public List<ReleaseDate> getReleaseDates() {
        return releaseDates;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ReleaseInfo) {
            final ReleaseInfo other = (ReleaseInfo) obj;
            return new EqualsBuilder()
                    .append(getCountry(), other.getCountry())
                    .isEquals();
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(getCountry())
                .toHashCode();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.country);
        dest.writeTypedList(this.releaseDates);
    }

    protected ReleaseInfo(Parcel in) {
        this.country = in.readString();
        this.releaseDates = in.createTypedArrayList(ReleaseDate.CREATOR);
    }

    public static final Parcelable.Creator<ReleaseInfo> CREATOR = new Parcelable.Creator<ReleaseInfo>() {
        @Override
        public ReleaseInfo createFromParcel(Parcel source) {
            return new ReleaseInfo(source);
        }

        @Override
        public ReleaseInfo[] newArray(int size) {
            return new ReleaseInfo[size];
        }
    };
}
