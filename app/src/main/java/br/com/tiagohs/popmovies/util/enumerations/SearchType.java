package br.com.tiagohs.popmovies.util.enumerations;

/**
 * Created by Tiago Henrique on 01/09/2016.
 */
public enum SearchType {
    PHRASE,
    NGRAM;

    public String getPropertyString() {
        return this.name().toLowerCase();
    }

    @Override
    public String toString() {
        return getPropertyString();
    }
}
