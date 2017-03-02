package br.com.tiagohs.popmovies.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class IDNameAbstract {
    @JsonProperty("id")
    private int id;

    @JsonProperty("name")
    private String name;

    public IDNameAbstract(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public IDNameAbstract() {
    }

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
