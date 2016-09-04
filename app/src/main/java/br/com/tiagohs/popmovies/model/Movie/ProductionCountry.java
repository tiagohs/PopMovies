package br.com.tiagohs.popmovies.model.movie;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.io.Serializable;

/**
 * Created by Tiago Henrique on 24/08/2016.
 */
@JsonRootName("production_country")
public class ProductionCountry implements Serializable {

    @JsonProperty("iso_3166_1")
    private String country;
    @JsonProperty("name")
    private String name;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
