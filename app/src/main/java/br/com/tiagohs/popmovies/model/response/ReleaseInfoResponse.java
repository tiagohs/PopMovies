package br.com.tiagohs.popmovies.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import br.com.tiagohs.popmovies.model.movie.ReleaseInfo;

/**
 * Created by Tiago Henrique on 24/08/2016.
 */
public class ReleaseInfoResponse {

    @JsonProperty("id")
    private int id;

    @JsonProperty("results")
    private List<ReleaseInfo> countries;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<ReleaseInfo> getCountries() {
        return countries;
    }

    public void setCountries(List<ReleaseInfo> countries) {
        this.countries = countries;
    }
}
