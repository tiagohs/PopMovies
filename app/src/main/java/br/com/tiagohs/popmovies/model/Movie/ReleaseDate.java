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
import com.fasterxml.jackson.annotation.JsonSetter;

import br.com.tiagohs.popmovies.util.enumerations.ReleaseType;

public class ReleaseDate implements Parcelable {

    @JsonProperty("certification")
    private String certification;
    @JsonProperty("release_date")
    private String releaseDate;
    @JsonProperty("note")
    private String note;
    @JsonProperty("iso_639_1")
    private String language;
    private ReleaseType releaseType;

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public ReleaseType getType() {
        return releaseType;
    }


    public String getCertification() {
        return certification;
    }

    public void setCertification(String certification) {
        this.certification = certification;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    @JsonSetter("type")
    public void setType(int type) {
        this.releaseType = ReleaseType.fromInteger(type);
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.certification);
        dest.writeString(this.releaseDate);
        dest.writeString(this.note);
        dest.writeString(this.language);
        dest.writeInt(this.releaseType == null ? -1 : this.releaseType.ordinal());
    }

    public ReleaseDate() {
    }

    protected ReleaseDate(Parcel in) {
        this.certification = in.readString();
        this.releaseDate = in.readString();
        this.note = in.readString();
        this.language = in.readString();
        int tmpReleaseType = in.readInt();
        this.releaseType = tmpReleaseType == -1 ? null : ReleaseType.values()[tmpReleaseType];
    }

    public static final Parcelable.Creator<ReleaseDate> CREATOR = new Parcelable.Creator<ReleaseDate>() {
        @Override
        public ReleaseDate createFromParcel(Parcel source) {
            return new ReleaseDate(source);
        }

        @Override
        public ReleaseDate[] newArray(int size) {
            return new ReleaseDate[size];
        }
    };
}
