package br.com.tiagohs.popmovies.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import br.com.tiagohs.popmovies.model.movie.Genre;

/**
 * Created by Tiago Henrique on 03/09/2016.
 */
public class GenresResponse {
    @JsonProperty("genres")
    List<Genre> genres;

    public GenresResponse() {
    }

    public GenresResponse(List<Genre> genres) {
        this.genres = genres;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }
}
