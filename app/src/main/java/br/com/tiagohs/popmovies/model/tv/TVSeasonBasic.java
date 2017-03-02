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
package br.com.tiagohs.popmovies.model.tv;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * TV Favorite information
 *
 * @author stuart.boston
 */
public class TVSeasonBasic implements Parcelable {

    @JsonProperty("id")
    private int id = -1;
    @JsonProperty("air_date")
    private String airDate;
    @JsonProperty("poster_path")
    private String posterPath;
    @JsonProperty("season_number")
    private int seasonNumber;
    @JsonProperty("episode_count")
    private int episodeCount = -1;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAirDate() {
        return airDate;
    }

    public void setAirDate(String airDate) {
        this.airDate = airDate;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public int getSeasonNumber() {
        return seasonNumber;
    }

    public void setSeasonNumber(int seasonNumber) {
        this.seasonNumber = seasonNumber;
    }

    public int getEpisodeCount() {
        return episodeCount;
    }

    public void setEpisodeCount(int episodeCount) {
        this.episodeCount = episodeCount;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.airDate);
        dest.writeString(this.posterPath);
        dest.writeInt(this.seasonNumber);
        dest.writeInt(this.episodeCount);
    }

    public TVSeasonBasic() {
    }

    protected TVSeasonBasic(Parcel in) {
        this.id = in.readInt();
        this.airDate = in.readString();
        this.posterPath = in.readString();
        this.seasonNumber = in.readInt();
        this.episodeCount = in.readInt();
    }

    public static final Parcelable.Creator<TVSeasonBasic> CREATOR = new Parcelable.Creator<TVSeasonBasic>() {
        @Override
        public TVSeasonBasic createFromParcel(Parcel source) {
            return new TVSeasonBasic(source);
        }

        @Override
        public TVSeasonBasic[] newArray(int size) {
            return new TVSeasonBasic[size];
        }
    };
}
