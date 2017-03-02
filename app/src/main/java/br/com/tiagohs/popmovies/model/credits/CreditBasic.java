
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
package br.com.tiagohs.popmovies.model.credits;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

import br.com.tiagohs.popmovies.util.enumerations.CreditType;
import br.com.tiagohs.popmovies.util.enumerations.MediaType;

/**
 * @author stuart.boston
 */
public class CreditBasic implements Parcelable {

    private static final long serialVersionUID = 100L;

    private CreditType creditType;
    private MediaType mediaType;

    @JsonProperty("credit_id")
    private String creditId;
    @JsonProperty("id")
    private int id;
    @JsonProperty("poster_path")
    private String artworkPath;

    //cast
    @JsonProperty("character")
    private String character;
    //crew
    @JsonProperty("department")
    private String department;
    @JsonProperty("job")
    private String job;

    public CreditType getCreditType() {
        return creditType;
    }

    public void setCreditType(CreditType creditType) {
        this.creditType = creditType;
    }

    public MediaType getMediaType() {
        return mediaType;
    }

    @JsonSetter("media_type")
    public void setMediaType(String mediaType) {
        this.mediaType = MediaType.fromString(mediaType);
    }

    public void setMediaType(MediaType mediaType) {
        this.mediaType = mediaType;
    }

    public String getCreditId() {
        return creditId;
    }

    public void setCreditId(String creditId) {
        this.creditId = creditId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getArtworkPath() {
        return artworkPath;
    }

    public void setArtworkPath(String artworkPath) {
        this.artworkPath = artworkPath;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
        setCreditType(CreditType.CAST);
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
        setCreditType(CreditType.CREW);
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
        setCreditType(CreditType.CREW);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.creditType == null ? -1 : this.creditType.ordinal());
        dest.writeInt(this.mediaType == null ? -1 : this.mediaType.ordinal());
        dest.writeString(this.creditId);
        dest.writeInt(this.id);
        dest.writeString(this.artworkPath);
        dest.writeString(this.character);
        dest.writeString(this.department);
        dest.writeString(this.job);
    }

    public CreditBasic() {
    }

    protected CreditBasic(Parcel in) {
        int tmpCreditType = in.readInt();
        this.creditType = tmpCreditType == -1 ? null : CreditType.values()[tmpCreditType];
        int tmpMediaType = in.readInt();
        this.mediaType = tmpMediaType == -1 ? null : MediaType.values()[tmpMediaType];
        this.creditId = in.readString();
        this.id = in.readInt();
        this.artworkPath = in.readString();
        this.character = in.readString();
        this.department = in.readString();
        this.job = in.readString();
    }

    public static final Parcelable.Creator<CreditBasic> CREATOR = new Parcelable.Creator<CreditBasic>() {
        @Override
        public CreditBasic createFromParcel(Parcel source) {
            return new CreditBasic(source);
        }

        @Override
        public CreditBasic[] newArray(int size) {
            return new CreditBasic[size];
        }
    };
}
