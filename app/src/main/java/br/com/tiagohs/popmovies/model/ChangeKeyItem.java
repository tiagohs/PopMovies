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

import java.util.ArrayList;
import java.util.List;

public class ChangeKeyItem implements Parcelable {

    @JsonProperty("key")
    private String key;
    @JsonProperty("items")
    private List<ChangedItem> changedItems = new ArrayList<>();

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<ChangedItem> getChangedItems() {
        return changedItems;
    }

    public void setChangedItems(List<ChangedItem> changes) {
        this.changedItems = changes;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.key);
        dest.writeTypedList(this.changedItems);
    }

    public ChangeKeyItem() {
    }

    protected ChangeKeyItem(Parcel in) {
        this.key = in.readString();
        this.changedItems = in.createTypedArrayList(ChangedItem.CREATOR);
    }

    public static final Parcelable.Creator<ChangeKeyItem> CREATOR = new Parcelable.Creator<ChangeKeyItem>() {
        @Override
        public ChangeKeyItem createFromParcel(Parcel source) {
            return new ChangeKeyItem(source);
        }

        @Override
        public ChangeKeyItem[] newArray(int size) {
            return new ChangeKeyItem[size];
        }
    };
}
