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

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.List;

import br.com.tiagohs.popmovies.model.credits.MediaBasic;
import br.com.tiagohs.popmovies.model.movie.Movie;
import br.com.tiagohs.popmovies.model.tv.TVBasic;
import br.com.tiagohs.popmovies.model.tv.TVEpisodeBasic;

/**
 * @author stuart.boston
 */
public class PersonFind extends PersonBasic {

    @JsonProperty("adult")
    private Boolean adult;
    @JsonProperty("popularity")
    private Float popularity;
    private List<? extends MediaBasic> knownFor;

    public Boolean getAdult() {
        return adult;
    }

    public void setAdult(Boolean adult) {
        this.adult = adult;
    }

    public Float getPopularity() {
        return popularity;
    }

    public void setPopularity(Float popularity) {
        this.popularity = popularity;
    }

    public List<MediaBasic> getKnownFor() {
        return (List<MediaBasic>) knownFor;
    }

    @JsonTypeInfo(
            use = JsonTypeInfo.Id.NAME,
            include = JsonTypeInfo.As.PROPERTY,
            property = "media_type",
            defaultImpl = MediaBasic.class
    )
    @JsonSubTypes({
        @JsonSubTypes.Type(value = Movie.class, name = "movie"),
        @JsonSubTypes.Type(value = TVBasic.class, name = "tv"),
        @JsonSubTypes.Type(value = TVEpisodeBasic.class, name = "episode")
    })
    @JsonSetter("known_for")
    public void setKnownFor(List<? extends MediaBasic> knownFor) {
        this.knownFor = knownFor;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeValue(this.adult);
        dest.writeValue(this.popularity);
        dest.writeTypedList(this.knownFor);
    }

    public PersonFind() {
    }

    protected PersonFind(Parcel in) {
        super(in);
        this.adult = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.popularity = (Float) in.readValue(Float.class.getClassLoader());
        this.knownFor = in.createTypedArrayList(MediaBasic.CREATOR);
    }

    public static final Creator<PersonFind> CREATOR = new Creator<PersonFind>() {
        @Override
        public PersonFind createFromParcel(Parcel source) {
            return new PersonFind(source);
        }

        @Override
        public PersonFind[] newArray(int size) {
            return new PersonFind[size];
        }
    };
}
