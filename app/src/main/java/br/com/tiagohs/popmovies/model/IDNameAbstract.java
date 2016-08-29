package br.com.tiagohs.popmovies.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by Tiago Henrique on 24/08/2016.
 */
public abstract class IDNameAbstract implements Serializable {
    @JsonProperty("id")
    private int id;

    @JsonProperty("name")
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
