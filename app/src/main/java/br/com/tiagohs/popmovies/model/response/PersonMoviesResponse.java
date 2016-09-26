package br.com.tiagohs.popmovies.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

import br.com.tiagohs.popmovies.model.credits.CreditMovieBasic;

public class PersonMoviesResponse implements Serializable {

    @JsonProperty("adult")
    private boolean adult;

    @JsonProperty("id")
    private int id;

    @JsonProperty("cast")
    private List<CreditMovieBasic> moviesByCast;

    @JsonProperty("crew")
    private List<CreditMovieBasic> moviesByCrew;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<CreditMovieBasic> getMoviesByCast() {
        return moviesByCast;
    }

    public void setMoviesByCast(List<CreditMovieBasic> moviesByCast) {
        this.moviesByCast = moviesByCast;
    }

    public List<CreditMovieBasic> getMoviesByCrew() {
        return moviesByCrew;
    }

    public void setMoviesByCrew(List<CreditMovieBasic> moviesByCrew) {
        this.moviesByCrew = moviesByCrew;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }
}
