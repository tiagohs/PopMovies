package br.com.tiagohs.popmovies.model.movie;

import android.os.Parcel;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

import br.com.tiagohs.popmovies.model.credits.MediaBasic;
import br.com.tiagohs.popmovies.util.DateUtils;


public class Movie extends MediaBasic {

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

    @JsonProperty("video")
    private boolean video;

    @JsonProperty("favorite")
    private boolean favorite;

    private boolean isJaAssistido;
    private int statusDB;

    public Movie() {
    }

    public Movie(boolean adult, String nama, int popularity, String profilePath, List<Movie> knowFor, String posterPath, String overview, String releaseDate, String originalTitle, List<Integer> genreIDs, String originalLanguage, String title, String backdropPath, boolean video, boolean favorite) {
        super(adult, nama, popularity, profilePath, knowFor);
        this.posterPath = posterPath;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.originalTitle = originalTitle;
        this.genreIDs = genreIDs;
        this.originalLanguage = originalLanguage;
        this.title = title;
        this.backdropPath = backdropPath;
        this.video = video;
        this.favorite = favorite;
    }

    public boolean isJaAssistido() {
        return isJaAssistido;
    }

    public int getStatusDB() {
        return statusDB;
    }

    public void setStatusDB(int statusDB) {
        this.statusDB = statusDB;
    }

    public void setJaAssistido(boolean jaAssistido) {
        isJaAssistido = jaAssistido;
    }

    @Override
    public int getId() {
        return super.getId();
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

    public boolean isVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
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

    public int getYearRelease() {
        return DateUtils.getYearByDate(releaseDate);
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.posterPath);
        dest.writeString(this.overview);
        dest.writeString(this.releaseDate);
        dest.writeString(this.originalTitle);
        dest.writeList(this.genreIDs);
        dest.writeString(this.originalLanguage);
        dest.writeString(this.title);
        dest.writeString(this.backdropPath);
        dest.writeByte(this.video ? (byte) 1 : (byte) 0);
        dest.writeByte(this.favorite ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isJaAssistido ? (byte) 1 : (byte) 0);
        dest.writeInt(this.statusDB);
    }

    protected Movie(Parcel in) {
        super(in);
        this.posterPath = in.readString();
        this.overview = in.readString();
        this.releaseDate = in.readString();
        this.originalTitle = in.readString();
        this.genreIDs = new ArrayList<Integer>();
        in.readList(this.genreIDs, Integer.class.getClassLoader());
        this.originalLanguage = in.readString();
        this.title = in.readString();
        this.backdropPath = in.readString();
        this.video = in.readByte() != 0;
        this.favorite = in.readByte() != 0;
        this.isJaAssistido = in.readByte() != 0;
        this.statusDB = in.readInt();
    }

}
