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
import com.fasterxml.jackson.annotation.JsonSetter;

import java.util.ArrayList;
import java.util.List;

import br.com.tiagohs.popmovies.model.credits.CreditBasic;

public class PersonCreditList<T extends CreditBasic> implements Parcelable {

    private static final long serialVersionUID = 101L;

    @JsonProperty("id")
    private int id;
    private List<T> cast;
    private List<T> crew;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<T> getCast() {
        return cast;
    }

    @JsonSetter("cast")
    public void setCast(List<T> cast) {
        this.cast = cast;
    }

    public List<T> getCrew() {
        return crew;
    }

    @JsonSetter("crew")
    public void setCrew(List<T> crew) {
        this.crew = crew;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeTypedList(this.cast);
        dest.writeTypedList(this.crew);
    }

    public PersonCreditList() {
    }

    protected PersonCreditList(Parcel in) {
        this.id = in.readInt();
        this.cast = (ArrayList) in.createTypedArrayList(T.CREATOR);
        this.crew = (ArrayList) in.createTypedArrayList(T.CREATOR);
    }

    public static final Parcelable.Creator<PersonCreditList> CREATOR = new Parcelable.Creator<PersonCreditList>() {
        @Override
        public PersonCreditList createFromParcel(Parcel source) {
            return new PersonCreditList(source);
        }

        @Override
        public PersonCreditList[] newArray(int size) {
            return new PersonCreditList[size];
        }
    };
}
