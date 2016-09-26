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

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

import br.com.tiagohs.popmovies.util.enumerations.MediaType;

/**
 * @author stuart.boston
 */
public class CreditMovieBasic extends CreditBasic implements Serializable {

    private static final long serialVersionUID = 100L;

    @JsonProperty("adult")
    private boolean adult;
    @JsonProperty("original_title")
    private String originalTitle;
    @JsonProperty("release_date")
    private String releaseDate;
    @JsonProperty("title")
    private String title;

    @JsonProperty("episode_count")
    private int episodeCount;
    @JsonProperty("first_air_date")
    private String firstAirDate;
    @JsonProperty("air_date")
    private String airDate;
    @JsonProperty("name")
    private String name;
    @JsonProperty("original_name")
    private String originalName;

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

    public String getAirDate() {
        return airDate;
    }

    public void setAirDate(String airDate) {
        this.airDate = airDate;
    }

    public CreditMovieBasic() {
        setMediaType(MediaType.MOVIE);
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object obj) {

        if (obj instanceof CreditMovieBasic && obj != null) {
            CreditMovieBasic c = (CreditMovieBasic) obj;
            return c.getId() == this.getId();
        }
        return false;

    }
}
