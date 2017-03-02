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

/**
 *
 * @author Stuart
 */
public class ExternalID implements Parcelable {

    @JsonProperty("id")
    private int id;
    @JsonProperty("imdb_id")
    private String imdbId;
    @JsonProperty("freebase_mid")
    private String freebaseMid;
    @JsonProperty("freebase_id")
    private String freebaseId;
    @JsonProperty("tvdb_id")
    private String tvdbId;
    @JsonProperty("tvrage_id")
    private String tvrageId;
    @JsonProperty("facebook_id")
    private String facebookId;
    @JsonProperty("twitter_id")
    private String twitterId;
    @JsonProperty("instagram_id")
    private String instagramID;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public String getFreebaseMid() {
        return freebaseMid;
    }

    public void setFreebaseMid(String freebaseMid) {
        this.freebaseMid = freebaseMid;
    }

    public String getFreebaseId() {
        return freebaseId;
    }

    public void setFreebaseId(String freebaseId) {
        this.freebaseId = freebaseId;
    }

    public String getTvrageId() {
        return tvrageId;
    }

    public void setTvrageId(String tvrageId) {
        this.tvrageId = tvrageId;
    }

    public String getTvdbId() {
        return tvdbId;
    }

    public void setTvdbId(String tvdbId) {
        this.tvdbId = tvdbId;
    }

    public String getFacebookId() {
        return facebookId;
    }

    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }

    public String getTwitterId() {
        return twitterId;
    }

    public void setTwitterId(String twitterId) {
        this.twitterId = twitterId;
    }

    public String getInstagramID() {
        return instagramID;
    }

    public void setInstagramID(String instagramID) {
        this.instagramID = instagramID;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.imdbId);
        dest.writeString(this.freebaseMid);
        dest.writeString(this.freebaseId);
        dest.writeString(this.tvdbId);
        dest.writeString(this.tvrageId);
        dest.writeString(this.facebookId);
        dest.writeString(this.twitterId);
        dest.writeString(this.instagramID);
    }

    public ExternalID() {
    }

    protected ExternalID(Parcel in) {
        this.id = in.readInt();
        this.imdbId = in.readString();
        this.freebaseMid = in.readString();
        this.freebaseId = in.readString();
        this.tvdbId = in.readString();
        this.tvrageId = in.readString();
        this.facebookId = in.readString();
        this.twitterId = in.readString();
        this.instagramID = in.readString();
    }

    public static final Parcelable.Creator<ExternalID> CREATOR = new Parcelable.Creator<ExternalID>() {
        @Override
        public ExternalID createFromParcel(Parcel source) {
            return new ExternalID(source);
        }

        @Override
        public ExternalID[] newArray(int size) {
            return new ExternalID[size];
        }
    };
}
