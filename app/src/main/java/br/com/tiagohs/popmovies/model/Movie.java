package br.com.tiagohs.popmovies.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Tiago Henrique on 09/08/2016.
 */
public class Movie implements Serializable {
    private int id;
    private boolean adult;
    private String poster_path;
    private String overview;
    private String release_date;
    private List<Integer> genre_ids;
    private String original_title;
    private String original_language;
    private String title;
    private String backdrop_path;
    private String popularity;
    private boolean video;
    private String vote_average;
    private int vote_count;
    private boolean favorite = false;

    public Movie() {
    }

    public Movie(int id, boolean adult, String poster_path, String overview, String release_date, List<Integer> genre_ids, String original_title, String original_language, String title, String backdrop_path, String popularity, boolean video, String vote_average, int vote_count, boolean favorite) {
        this.id = id;
        this.adult = adult;
        this.poster_path = poster_path;
        this.overview = overview;
        this.release_date = release_date;
        this.genre_ids = genre_ids;
        this.original_title = original_title;
        this.original_language = original_language;
        this.title = title;
        this.backdrop_path = backdrop_path;
        this.popularity = popularity;
        this.video = video;
        this.vote_average = vote_average;
        this.vote_count = vote_count;
        this.favorite = favorite;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public List<Integer> getGenre_ids() {
        return genre_ids;
    }

    public void setGenre_ids(List<Integer> genre_ids) {
        this.genre_ids = genre_ids;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public boolean isVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public String getVote_average() {
        return vote_average;
    }

    public void setVote_average(String vote_average) {
        this.vote_average = vote_average;
    }

    public int getVote_count() {
        return vote_count;
    }

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    @Override
    public boolean equals(Object obj) {

        if (obj instanceof Movie) {
            Movie m = (Movie) obj;
            return this.getId() == m.getId();
        }

        return false;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
