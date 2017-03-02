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
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import br.com.tiagohs.popmovies.model.credits.MediaCreditCast;
import br.com.tiagohs.popmovies.model.credits.MediaCreditCrew;

/**
 * @author stuart.boston
 */
public class MediaCreditList implements Serializable, Parcelable {

    private static final long serialVersionUID = 100L;

    @JsonProperty("id")
    private int id = 0;
    @JsonProperty("cast")
    private List<MediaCreditCast> cast = Collections.emptyList();
    @JsonProperty("guest_stars")
    private List<MediaCreditCast> guestStars = Collections.emptyList();
    @JsonProperty("crew")
    private List<MediaCreditCrew> crew = Collections.emptyList();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<MediaCreditCast> getCast() {
        return cast;
    }

    public void setCast(List<MediaCreditCast> cast) {
        this.cast = cast;
    }

    public List<MediaCreditCrew> getCrew() {
        return crew;
    }

    public void setCrew(List<MediaCreditCrew> crew) {
        this.crew = crew;
    }

    public List<MediaCreditCast> getGuestStars() {
        return guestStars;
    }

    public void setGuestStars(List<MediaCreditCast> guestStars) {
        this.guestStars = guestStars;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeTypedList(this.cast);
        dest.writeTypedList(this.guestStars);
        dest.writeTypedList(this.crew);
    }

    public MediaCreditList() {
    }

    protected MediaCreditList(Parcel in) {
        this.id = in.readInt();
        this.cast = in.createTypedArrayList(MediaCreditCast.CREATOR);
        this.guestStars = in.createTypedArrayList(MediaCreditCast.CREATOR);
        this.crew = in.createTypedArrayList(MediaCreditCrew.CREATOR);
    }

    public static final Parcelable.Creator<MediaCreditList> CREATOR = new Parcelable.Creator<MediaCreditList>() {
        @Override
        public MediaCreditList createFromParcel(Parcel source) {
            return new MediaCreditList(source);
        }

        @Override
        public MediaCreditList[] newArray(int size) {
            return new MediaCreditList[size];
        }
    };
}
