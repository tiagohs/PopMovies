package br.com.tiagohs.popmovies.model.Movie;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;


public class Movie implements Serializable {
    @JsonProperty("id")
    private int id;

    @JsonProperty("adult")
    private boolean adult;

    @JsonProperty("poster_path")
    private String posterPath;

    @JsonProperty("overview")
    private String overview;

    @JsonProperty("release_date")
    private String releaseDate;

    @JsonProperty("original_title")
    private String originalTitle;

    @JsonProperty("genre_ids")
    private List<Integer> genreIDs;

    @JsonProperty("original_language")
    private String originalLanguage;

    @JsonProperty("title")
    private String title;

    @JsonProperty("backdrop_path")
    private String backdropPath;

    @JsonProperty("popularity")
    private String popularity;

    @JsonProperty("video")
    private boolean video;

    @JsonProperty("vote_average")
    private String voteAverage;

    @JsonProperty("vote_count")
    private int voteCount;

    @JsonProperty("favorite")
    private boolean favorite = false;

    public Movie() {
    }

    public Movie(int id, boolean adult, String posterPath, String overview, String releaseDate, String originalTitle, String originalLanguage, String title, String backdropPath, String popularity, boolean video, String voteAverage, int voteCount, boolean favorite) {
        this.id = id;
        this.adult = adult;
        this.posterPath = posterPath;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.originalTitle = originalTitle;
        this.originalLanguage = originalLanguage;
        this.title = title;
        this.backdropPath = backdropPath;
        this.popularity = popularity;
        this.video = video;
        this.voteAverage = voteAverage;
        this.voteCount = voteCount;
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

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
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

    public String getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(String voteAverage) {
        this.voteAverage = voteAverage;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public List<Integer> getGenreIDs() {
        return genreIDs;
    }

    public void setGenreIDs(List<Integer> genreIDs) {
        this.genreIDs = genreIDs;
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
