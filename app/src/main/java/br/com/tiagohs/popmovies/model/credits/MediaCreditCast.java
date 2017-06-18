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

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author Stuart.Boston
 */
public class MediaCreditCast extends MediaCredit {

    private static final long serialVersionUID = 100L;

    @JsonProperty("cast_id")
    private int castId = 0;
    @JsonProperty("character")
    private String character;
    @JsonProperty("order")
    private int order;
    @JsonProperty("gender")
    private String gender;

    public int getCastId() {
        return castId;
    }

    public void setCastId(int castId) {
        this.castId = castId;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(this.castId);
        dest.writeString(this.character);
        dest.writeInt(this.order);
    }

    public MediaCreditCast() {
    }

    protected MediaCreditCast(Parcel in) {
        super(in);
        this.castId = in.readInt();
        this.character = in.readString();
        this.order = in.readInt();
    }

    public static final Creator<MediaCreditCast> CREATOR = new Creator<MediaCreditCast>() {
        @Override
        public MediaCreditCast createFromParcel(Parcel source) {
            return new MediaCreditCast(source);
        }

        @Override
        public MediaCreditCast[] newArray(int size) {
            return new MediaCreditCast[size];
        }
    };
}
