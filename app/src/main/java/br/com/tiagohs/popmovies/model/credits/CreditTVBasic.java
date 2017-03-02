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

import br.com.tiagohs.popmovies.util.enumerations.MediaType;

/**
 * @author stuart.boston
 */
public class CreditTVBasic extends CreditBasic {

    private static final long serialVersionUID = 100L;

    @JsonProperty("episode_count")
    private int episodeCount;
    @JsonProperty("first_air_date")
    private String firstAirDate;
    @JsonProperty("name")
    private String name;
    @JsonProperty("original_name")
    private String originalName;

    public CreditTVBasic() {
        setMediaType(MediaType.TV);
    }

    public int getEpisodeCount() {
        return episodeCount;
    }

    public void setEpisodeCount(int episodeCount) {
        this.episodeCount = episodeCount;
    }

    public String getFirstAirDate() {
        return firstAirDate;
    }

    public void setFirstAirDate(String firstAirDate) {
        this.firstAirDate = firstAirDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(this.episodeCount);
        dest.writeString(this.firstAirDate);
        dest.writeString(this.name);
        dest.writeString(this.originalName);
    }

    protected CreditTVBasic(Parcel in) {
        super(in);
        this.episodeCount = in.readInt();
        this.firstAirDate = in.readString();
        this.name = in.readString();
        this.originalName = in.readString();
    }

    public static final Creator<CreditTVBasic> CREATOR = new Creator<CreditTVBasic>() {
        @Override
        public CreditTVBasic createFromParcel(Parcel source) {
            return new CreditTVBasic(source);
        }

        @Override
        public CreditTVBasic[] newArray(int size) {
            return new CreditTVBasic[size];
        }
    };
}
