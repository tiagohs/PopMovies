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

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

import br.com.tiagohs.popmovies.model.credits.MediaBasic;
import br.com.tiagohs.popmovies.util.enumerations.MediaType;

/**
 * TV Favorite information
 *
 * @author stuart.boston
 */
public class TVEpisodeBasic extends MediaBasic {

    private static final long serialVersionUID = 100L;

    @JsonProperty("air_date")
    private String airDate;
    @JsonProperty("season_number")
    private int seasonNumber;
    @JsonProperty("episode_number")
    private int episodeNumber;
    @JsonProperty("name")
    private String name;
    @JsonProperty("overview")
    private String overview;
    @JsonProperty("still_path")
    private String stillPath;
    @JsonProperty("show_id")
    private String showId;

    public TVEpisodeBasic() {
        super.setMediaType(MediaType.EPISODE);
    }

    public String getAirDate() {
        return airDate;
    }

    public void setAirDate(String airDate) {
        this.airDate = airDate;
    }

    public int getSeasonNumber() {
        return seasonNumber;
    }

    public void setSeasonNumber(int seasonNumber) {
        this.seasonNumber = seasonNumber;
    }

    public int getEpisodeNumber() {
        return episodeNumber;
    }

    public void setEpisodeNumber(int episodeNumber) {
        this.episodeNumber = episodeNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getStillPath() {
        return stillPath;
    }

    public void setStillPath(String stillPath) {
        this.stillPath = stillPath;
    }

    public String getShowId() {
        return showId;
    }

    public void setShowId(String showId) {
        this.showId = showId;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.airDate);
        dest.writeInt(this.seasonNumber);
        dest.writeInt(this.episodeNumber);
        dest.writeString(this.name);
        dest.writeString(this.overview);
        dest.writeString(this.stillPath);
        dest.writeString(this.showId);
    }

    protected TVEpisodeBasic(Parcel in) {
        super(in);
        this.airDate = in.readString();
        this.seasonNumber = in.readInt();
        this.episodeNumber = in.readInt();
        this.name = in.readString();
        this.overview = in.readString();
        this.stillPath = in.readString();
        this.showId = in.readString();
    }

    public static final Creator<TVEpisodeBasic> CREATOR = new Creator<TVEpisodeBasic>() {
        @Override
        public TVEpisodeBasic createFromParcel(Parcel source) {
            return new TVEpisodeBasic(source);
        }

        @Override
        public TVEpisodeBasic[] newArray(int size) {
            return new TVEpisodeBasic[size];
        }
    };
}
