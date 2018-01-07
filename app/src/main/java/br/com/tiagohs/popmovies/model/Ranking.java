package br.com.tiagohs.popmovies.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Tiago on 07/01/2018.
 */

public class Ranking {

    @JsonProperty("Source")
    private String source;

    @JsonProperty("Value")
    private String value;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
