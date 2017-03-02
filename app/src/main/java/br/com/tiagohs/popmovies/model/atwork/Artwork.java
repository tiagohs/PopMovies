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
package br.com.tiagohs.popmovies.model.atwork;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import br.com.tiagohs.popmovies.util.enumerations.ArtworkType;

/**
 * The artwork type information
 *
 * @author Stuart
 */
public class Artwork implements Parcelable {

    @JsonProperty("id")
    private String id;
    @JsonProperty("aspect_ratio")
    private float aspectRatio;
    @JsonProperty("file_path")
    private String filePath;
    @JsonProperty("height")
    private int height;
    @JsonProperty("iso_639_1")
    private String language;
    @JsonProperty("width")
    private int width;
    @JsonProperty("vote_average")
    private float voteAverage;
    @JsonProperty("vote_count")
    private int voteCount;
    @JsonProperty("flag")
    private String flag;
    private ArtworkType artworkType = ArtworkType.POSTER;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public float getAspectRatio() {
        return aspectRatio;
    }

    public void setAspectRatio(float aspectRatio) {
        this.aspectRatio = aspectRatio;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public float getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(float voteAverage) {
        this.voteAverage = voteAverage;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public ArtworkType getArtworkType() {
        return artworkType;
    }

    public void setArtworkType(ArtworkType artworkType) {
        this.artworkType = artworkType;
    }

    @JsonSetter("image_type")
    public void setArtworkType(String artworkType) {
        this.artworkType = ArtworkType.fromString(artworkType);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Artwork) {
            final Artwork other = (Artwork) obj;
            return new EqualsBuilder()
                    .append(aspectRatio, other.aspectRatio)
                    .append(filePath, other.filePath)
                    .append(language, other.language)
                    .append(height, other.height)
                    .append(width, other.width)
                    .append(artworkType, other.artworkType)
                    .isEquals();
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(aspectRatio)
                .append(filePath)
                .append(height)
                .append(width)
                .append(language)
                .append(artworkType)
                .toHashCode();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeFloat(this.aspectRatio);
        dest.writeString(this.filePath);
        dest.writeInt(this.height);
        dest.writeString(this.language);
        dest.writeInt(this.width);
        dest.writeFloat(this.voteAverage);
        dest.writeInt(this.voteCount);
        dest.writeString(this.flag);
        dest.writeInt(this.artworkType == null ? -1 : this.artworkType.ordinal());
    }

    public Artwork() {
    }

    protected Artwork(Parcel in) {
        this.id = in.readString();
        this.aspectRatio = in.readFloat();
        this.filePath = in.readString();
        this.height = in.readInt();
        this.language = in.readString();
        this.width = in.readInt();
        this.voteAverage = in.readFloat();
        this.voteCount = in.readInt();
        this.flag = in.readString();
        int tmpArtworkType = in.readInt();
        this.artworkType = tmpArtworkType == -1 ? null : ArtworkType.values()[tmpArtworkType];
    }

    public static final Parcelable.Creator<Artwork> CREATOR = new Parcelable.Creator<Artwork>() {
        @Override
        public Artwork createFromParcel(Parcel source) {
            return new Artwork(source);
        }

        @Override
        public Artwork[] newArray(int size) {
            return new Artwork[size];
        }
    };
}
