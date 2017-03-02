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

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import br.com.tiagohs.popmovies.model.credits.MediaBasic;
import br.com.tiagohs.popmovies.model.movie.Movie;
import br.com.tiagohs.popmovies.model.tv.TVBasic;
import br.com.tiagohs.popmovies.model.tv.TVEpisodeBasic;
import br.com.tiagohs.popmovies.util.enumerations.MediaType;

/**
 *
 * @author Stuart
 */
public class ArtworkMedia extends Artwork {

    private MediaType mediaType;
    @JsonTypeInfo(
            use = JsonTypeInfo.Id.NAME,
            include = JsonTypeInfo.As.EXTERNAL_PROPERTY,
            property = "media_type",
            defaultImpl = MediaBasic.class
    )
    @JsonSubTypes({
        @JsonSubTypes.Type(value = Movie.class, name = "movie"),
        @JsonSubTypes.Type(value = TVBasic.class, name = "tv"),
        @JsonSubTypes.Type(value = TVEpisodeBasic.class, name = "episode")
    })
    @JsonProperty("media")
    private MediaBasic media;

    public MediaType getMediaType() {
        return mediaType;
    }

    public void setMediaType(MediaType mediaType) {
        this.mediaType = mediaType;
    }

    @JsonSetter("media_type")
    public void setMediaType(String mediaType) {
        this.mediaType = MediaType.fromString(mediaType);
    }

    public MediaBasic getMedia() {
        return media;
    }

    public void setMedia(MediaBasic media) {
        this.media = media;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(mediaType)
                .toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ArtworkMedia) {
            final ArtworkMedia other = (ArtworkMedia) obj;
            return new EqualsBuilder()
                    .appendSuper(super.equals(obj))
                    .append(mediaType, other.mediaType)
                    .isEquals();
        } else {
            return false;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(this.mediaType == null ? -1 : this.mediaType.ordinal());
        dest.writeParcelable(this.media, flags);
    }

    public ArtworkMedia() {
    }

    protected ArtworkMedia(Parcel in) {
        super(in);
        int tmpMediaType = in.readInt();
        this.mediaType = tmpMediaType == -1 ? null : MediaType.values()[tmpMediaType];
        this.media = in.readParcelable(MediaBasic.class.getClassLoader());
    }

    public static final Creator<ArtworkMedia> CREATOR = new Creator<ArtworkMedia>() {
        @Override
        public ArtworkMedia createFromParcel(Parcel source) {
            return new ArtworkMedia(source);
        }

        @Override
        public ArtworkMedia[] newArray(int size) {
            return new ArtworkMedia[size];
        }
    };
}
