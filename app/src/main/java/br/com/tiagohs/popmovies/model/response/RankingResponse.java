package br.com.tiagohs.popmovies.model.response;

/*
    "

 */

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import br.com.tiagohs.popmovies.model.Ranking;

public class RankingResponse implements Parcelable {

    @JsonProperty("Title")
    private String title;

    @JsonProperty("Year")
    private String year;

    @JsonProperty("Rated")
    private String rated;

    @JsonProperty("Released")
    private String released;

    @JsonProperty("Runtime")
    private String runtime;

    @JsonProperty("Genre")
    private String genre;

    @JsonProperty("Director")
    private String director;

    @JsonProperty("Writer")
    private String writer;

    @JsonProperty("Country")
    private String country;

    @JsonProperty("Actors")
    private String actors;

    @JsonProperty("Plot")
    private String plot;

    @JsonProperty("Language")
    private String language;

    @JsonProperty("Awards")
    private String awards;

    @JsonProperty("Poster")
    private String posterPath;

    @JsonProperty("Metascore")
    private String metascoreRating;

    @JsonProperty("imdbRating")
    private String imdbRanting;

    @JsonProperty("imdbVotes")
    private String imdbVotes;

    @JsonProperty("imdbID")
    private String imdbID;

    @JsonProperty("Type")
    private String type;

    @JsonProperty("tomatoMeter")
    private String tomatoMeter;

    @JsonProperty("tomatoImage")
    private String tomatoImage;

    @JsonProperty("tomatoRating")
    private String tomatoRating;

    @JsonProperty("tomatoReviews")
    private String tomatoReviews;

    @JsonProperty("tomatoFresh")
    private String tomatoFresh;

    @JsonProperty("tomatoRotten")
    private String tomatoRotten;

    @JsonProperty("tomatoConsensus")
    private String tomatoConsensus;

    @JsonProperty("tomatoUserMeter")
    private String tomatoUserMeter;

    @JsonProperty("tomatoUserRating")
    private String tomatoUserRating;

    @JsonProperty("tomatoUserReviews")
    private String tomatoUserReviews;

    @JsonProperty("tomatoURL")
    private String tomatoURL;

    @JsonProperty("DVD")
    private String dvdRelease;

    @JsonProperty("BoxOffice")
    private String boxOffice;

    @JsonProperty("Production")
    private String production;

    @JsonProperty("Website")
    private String website;

    @JsonProperty("Response")
    private boolean response;

    @JsonProperty("Ratings")
    private List<Ranking> ratings;

    @Nullable
    @JsonProperty("Error")
    private String error;

    public List<Ranking> getRatings() {
        return ratings;
    }

    public void setRatings(List<Ranking> ratings) {
        this.ratings = ratings;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getRated() {
        return rated;
    }

    public void setRated(String rated) {
        this.rated = rated;
    }

    public String getReleased() {
        return released;
    }

    public void setReleased(String released) {
        this.released = released;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getAwards() {
        return awards;
    }

    public void setAwards(String awards) {
        this.awards = awards;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getMetascoreRating() {
        return metascoreRating;
    }

    public void setMetascoreRating(String metascoreRating) {
        this.metascoreRating = metascoreRating;
    }

    public String getImdbRanting() {
        return imdbRanting;
    }

    public void setImdbRanting(String imdbRanting) {
        this.imdbRanting = imdbRanting;
    }

    public String getImdbVotes() {
        return imdbVotes;
    }

    public void setImdbVotes(String imdbVotes) {
        this.imdbVotes = imdbVotes;
    }

    public String getImdbID() {
        return imdbID;
    }

    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTomatoMeter() {
        return tomatoMeter;
    }

    public void setTomatoMeter(String tomatoMeter) {
        this.tomatoMeter = tomatoMeter;
    }

    public String getTomatoImage() {
        return tomatoImage;
    }

    public void setTomatoImage(String tomatoImage) {
        this.tomatoImage = tomatoImage;
    }

    public String getTomatoRating() {
        return tomatoRating;
    }

    public void setTomatoRating(String tomatoRating) {
        this.tomatoRating = tomatoRating;
    }

    public String getTomatoReviews() {
        return tomatoReviews;
    }

    public void setTomatoReviews(String tomatoReviews) {
        this.tomatoReviews = tomatoReviews;
    }

    public String getTomatoFresh() {
        return tomatoFresh;
    }

    public void setTomatoFresh(String tomatoFresh) {
        this.tomatoFresh = tomatoFresh;
    }

    public String getTomatoRotten() {
        return tomatoRotten;
    }

    public void setTomatoRotten(String tomatoRotten) {
        this.tomatoRotten = tomatoRotten;
    }

    public String getTomatoConsensus() {
        return tomatoConsensus;
    }

    public void setTomatoConsensus(String tomatoConsensus) {
        this.tomatoConsensus = tomatoConsensus;
    }

    public String getTomatoUserMeter() {
        return tomatoUserMeter;
    }

    public void setTomatoUserMeter(String tomatoUserMeter) {
        this.tomatoUserMeter = tomatoUserMeter;
    }

    public String getTomatoUserRating() {
        return tomatoUserRating;
    }

    public void setTomatoUserRating(String tomatoUserRating) {
        this.tomatoUserRating = tomatoUserRating;
    }

    public String getTomatoUserReviews() {
        return tomatoUserReviews;
    }

    public void setTomatoUserReviews(String tomatoUserReviews) {
        this.tomatoUserReviews = tomatoUserReviews;
    }

    public String getTomatoURL() {
        return tomatoURL;
    }

    public void setTomatoURL(String tomatoURL) {
        this.tomatoURL = tomatoURL;
    }

    public String getDvdRelease() {
        return dvdRelease;
    }

    public void setDvdRelease(String dvdRelease) {
        this.dvdRelease = dvdRelease;
    }

    public String getBoxOffice() {
        return boxOffice;
    }

    public void setBoxOffice(String boxOffice) {
        this.boxOffice = boxOffice;
    }

    public String getProduction() {
        return production;
    }

    public void setProduction(String production) {
        this.production = production;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public boolean isResponse() {
        return response;
    }

    public void setResponse(boolean response) {
        this.response = response;
    }

    @Nullable
    public String getError() {
        return error;
    }

    public void setError(@Nullable String error) {
        this.error = error;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.year);
        dest.writeString(this.rated);
        dest.writeString(this.released);
        dest.writeString(this.runtime);
        dest.writeString(this.genre);
        dest.writeString(this.director);
        dest.writeString(this.writer);
        dest.writeString(this.country);
        dest.writeString(this.actors);
        dest.writeString(this.plot);
        dest.writeString(this.language);
        dest.writeString(this.awards);
        dest.writeString(this.posterPath);
        dest.writeString(this.metascoreRating);
        dest.writeString(this.imdbRanting);
        dest.writeString(this.imdbVotes);
        dest.writeString(this.imdbID);
        dest.writeString(this.type);
        dest.writeString(this.tomatoMeter);
        dest.writeString(this.tomatoImage);
        dest.writeString(this.tomatoRating);
        dest.writeString(this.tomatoReviews);
        dest.writeString(this.tomatoFresh);
        dest.writeString(this.tomatoRotten);
        dest.writeString(this.tomatoConsensus);
        dest.writeString(this.tomatoUserMeter);
        dest.writeString(this.tomatoUserRating);
        dest.writeString(this.tomatoUserReviews);
        dest.writeString(this.tomatoURL);
        dest.writeString(this.dvdRelease);
        dest.writeString(this.boxOffice);
        dest.writeString(this.production);
        dest.writeString(this.website);
        dest.writeByte(this.response ? (byte) 1 : (byte) 0);
        dest.writeString(this.error);
    }

    public RankingResponse() {
    }

    protected RankingResponse(Parcel in) {
        this.title = in.readString();
        this.year = in.readString();
        this.rated = in.readString();
        this.released = in.readString();
        this.runtime = in.readString();
        this.genre = in.readString();
        this.director = in.readString();
        this.writer = in.readString();
        this.country = in.readString();
        this.actors = in.readString();
        this.plot = in.readString();
        this.language = in.readString();
        this.awards = in.readString();
        this.posterPath = in.readString();
        this.metascoreRating = in.readString();
        this.imdbRanting = in.readString();
        this.imdbVotes = in.readString();
        this.imdbID = in.readString();
        this.type = in.readString();
        this.tomatoMeter = in.readString();
        this.tomatoImage = in.readString();
        this.tomatoRating = in.readString();
        this.tomatoReviews = in.readString();
        this.tomatoFresh = in.readString();
        this.tomatoRotten = in.readString();
        this.tomatoConsensus = in.readString();
        this.tomatoUserMeter = in.readString();
        this.tomatoUserRating = in.readString();
        this.tomatoUserReviews = in.readString();
        this.tomatoURL = in.readString();
        this.dvdRelease = in.readString();
        this.boxOffice = in.readString();
        this.production = in.readString();
        this.website = in.readString();
        this.response = in.readByte() != 0;
        this.error = in.readString();
    }

    public static final Parcelable.Creator<RankingResponse> CREATOR = new Parcelable.Creator<RankingResponse>() {
        @Override
        public RankingResponse createFromParcel(Parcel source) {
            return new RankingResponse(source);
        }

        @Override
        public RankingResponse[] newArray(int size) {
            return new RankingResponse[size];
        }
    };
}
