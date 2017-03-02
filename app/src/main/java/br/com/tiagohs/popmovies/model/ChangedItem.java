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
package br.com.tiagohs.popmovies.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ChangedItem implements Parcelable {

    @JsonProperty("id")
    private String id;
    @JsonProperty("action")
    private String action;
    @JsonProperty("time")
    private String time;
    @JsonProperty("iso_639_1")
    private String language;
    @JsonProperty("value")
    private Object value;
    @JsonProperty("original_value")
    private Object originalValue;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Object getOriginalValue() {
        return originalValue;
    }

    public void setOriginalValue(Object originalValue) {
        this.originalValue = originalValue;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.action);
        dest.writeString(this.time);
        dest.writeString(this.language);
        dest.writeValue(this.value);
        dest.writeValue(this.originalValue);
    }

    public ChangedItem() {
    }

    protected ChangedItem(Parcel in) {
        this.id = in.readString();
        this.action = in.readString();
        this.time = in.readString();
        this.language = in.readString();
        this.value = in.readParcelable(Object.class.getClassLoader());
        this.originalValue = in.readParcelable(Object.class.getClassLoader());
    }

    public static final Parcelable.Creator<ChangedItem> CREATOR = new Parcelable.Creator<ChangedItem>() {
        @Override
        public ChangedItem createFromParcel(Parcel source) {
            return new ChangedItem(source);
        }

        @Override
        public ChangedItem[] newArray(int size) {
            return new ChangedItem[size];
        }
    };
}
