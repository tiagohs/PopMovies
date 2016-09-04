package br.com.tiagohs.popmovies.model.response;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import br.com.tiagohs.popmovies.model.credits.CreditMovieBasic;

public class CombinedCreditsResponse {

    @JsonProperty("id")
    private int id;

    @JsonProperty("cast")
    private List<CreditMovieBasic> cast;

    @JsonProperty("crew")
    private List<CreditMovieBasic> crew;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<CreditMovieBasic> getCast() {
        return cast;
    }

    public List<CreditMovieBasic> getCrew() {
        return crew;
    }

    public void setCast(List<CreditMovieBasic> cast) {
        this.cast = cast;
    }

    public void setCrew(List<CreditMovieBasic> crew) {
        this.crew = crew;
    }
}
