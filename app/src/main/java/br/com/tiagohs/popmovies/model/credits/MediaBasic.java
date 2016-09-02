package br.com.tiagohs.popmovies.model.credits;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import br.com.tiagohs.popmovies.model.Movie.Movie;

/**
 * Created by Tiago Henrique on 01/09/2016.
 */
public class MediaBasic {
    @JsonProperty("adult")
    private boolean adult;

    @JsonProperty("name")
    private String nama;

    @JsonProperty("popularity")
    private int popularity;

    @JsonProperty("profile_path")
    private String profilePath;

    @JsonProperty("known_for")
    private List<Movie> knowFor;

    public MediaBasic(boolean adult, String nama, int popularity, String profilePath, List<Movie> knowFor) {
        this.adult = adult;
        this.nama = nama;
        this.popularity = popularity;
        this.profilePath = profilePath;
        this.knowFor = knowFor;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public String getProfilePath() {
        return profilePath;
    }

    public void setProfilePath(String profilePath) {
        this.profilePath = profilePath;
    }

    public List<Movie> getKnowFor() {
        return knowFor;
    }

    public void setKnowFor(List<Movie> knowFor) {
        this.knowFor = knowFor;
    }
}
