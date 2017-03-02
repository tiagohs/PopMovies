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

import java.util.ArrayList;
import java.util.List;

import br.com.tiagohs.popmovies.model.IDNameAbstract;

/**
 * TV Favorite information
 *
 * @author stuart.boston
 */
public class TVCredit extends IDNameAbstract implements Parcelable {

    private static final long serialVersionUID = 100L;

    @JsonProperty("original_name")
    private String originalName;
    @JsonProperty("character")
    private String character;
    @JsonProperty("seasons")
    private List<TVSeasonBasic> seasons;
    @JsonProperty("episodes")
    private List<TVEpisodeBasic> episodes;

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public List<TVSeasonBasic> getSeasons() {
        return seasons;
    }

    public void setSeasons(List<TVSeasonBasic> seasons) {
        this.seasons = seasons;
    }

    public List<TVEpisodeBasic> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(List<TVEpisodeBasic> episodes) {
        this.episodes = episodes;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.originalName);
        dest.writeString(this.character);
        dest.writeList(this.seasons);
        dest.writeTypedList(this.episodes);
    }

    public TVCredit() {
    }

    protected TVCredit(Parcel in) {
        this.originalName = in.readString();
        this.character = in.readString();
        this.seasons = new ArrayList<TVSeasonBasic>();
        in.readList(this.seasons, TVSeasonBasic.class.getClassLoader());
        this.episodes = in.createTypedArrayList(TVEpisodeBasic.CREATOR);
    }

    public static final Parcelable.Creator<TVCredit> CREATOR = new Parcelable.Creator<TVCredit>() {
        @Override
        public TVCredit createFromParcel(Parcel source) {
            return new TVCredit(source);
        }

        @Override
        public TVCredit[] newArray(int size) {
            return new TVCredit[size];
        }
    };
}
