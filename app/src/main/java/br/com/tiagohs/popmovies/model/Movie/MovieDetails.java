package br.com.tiagohs.popmovies.model.movie;


import android.util.Log;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.io.Serializable;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import br.com.tiagohs.popmovies.model.ChangeKeyItem;
import br.com.tiagohs.popmovies.model.Language;
import br.com.tiagohs.popmovies.model.atwork.Artwork;
import br.com.tiagohs.popmovies.model.credits.MediaCreditCast;
import br.com.tiagohs.popmovies.model.credits.MediaCreditCrew;
import br.com.tiagohs.popmovies.model.keyword.Keyword;
import br.com.tiagohs.popmovies.model.list.UserList;
import br.com.tiagohs.popmovies.model.media.AlternativeTitle;
import br.com.tiagohs.popmovies.model.media.MediaCreditList;
import br.com.tiagohs.popmovies.model.media.Translation;
import br.com.tiagohs.popmovies.model.media.Video;
import br.com.tiagohs.popmovies.model.response.AlternativeTitlesResponse;
import br.com.tiagohs.popmovies.model.response.ChangesResponse;
import br.com.tiagohs.popmovies.model.response.GenericListResponse;
import br.com.tiagohs.popmovies.model.response.ImageResponse;
import br.com.tiagohs.popmovies.model.response.MovieKeywordsResponse;
import br.com.tiagohs.popmovies.model.response.ReleaseInfoResponse;
import br.com.tiagohs.popmovies.model.response.TranslationsResponse;
import br.com.tiagohs.popmovies.model.response.VideosResponse;
import br.com.tiagohs.popmovies.model.review.Review;
import br.com.tiagohs.popmovies.util.enumerations.MovieMethod;

public class MovieDetails extends Movie implements Serializable  {

    @JsonProperty("belongs_to_collection")
    private BelongsToCollection belongsToCollection;
    @JsonProperty("budget")
    private long budget;
    @JsonProperty("genres")
    private List<Genre> genres;
    @JsonProperty("homepage")
    private String homepage;
    @JsonProperty("imdb_id")
    private String imdbID;
    @JsonProperty("revenue")
    private long revenue;
    @JsonProperty("production_companies")
    private List<ProductionCompany> productionCompanies = Collections.emptyList();
    @JsonProperty("production_countries")
    private List<ProductionCountry> productionCountries = Collections.emptyList();
    @JsonProperty("runtime")
    private int runtime;
    @JsonProperty("spoken_languages")
    private List<Language> spokenLanguages = Collections.emptyList();
    @JsonProperty("tagline")
    private String tagline;
    @JsonProperty("status")
    private String status;

    // AppendToResponse
    private final Set<MovieMethod> methods = EnumSet.noneOf(MovieMethod.class);
    // AppendToResponse Properties
    private List<AlternativeTitle> alternativeTitles = Collections.emptyList();
    private MediaCreditList credits = new MediaCreditList();
    private List<Artwork> images = Collections.emptyList();
    private List<Keyword> keywords = Collections.emptyList();
    private List<ReleaseInfo> releases = Collections.emptyList();
    private List<Video> videos = Collections.emptyList();
    private List<Translation> translations = Collections.emptyList();
    private List<MovieDetails> similarMovies = Collections.emptyList();
    private List<Review> reviews = Collections.emptyList();
    private List<UserList> lists = Collections.emptyList();
    private List<ChangeKeyItem> changes = Collections.emptyList();

    public void addMoreVideos(List<Video> videos) {

        for (Video v : videos) {
            if (!this.videos.contains(v))
                this.videos.add(v);
        }

    }

    public void addMoreImages(List<Artwork> images) {

        for (Artwork v : images) {
            if (!this.images.contains(v))
                this.images.add(v);
        }

    }

    // <editor-fold defaultstate="collapsed" desc="Getter methods">
    public BelongsToCollection getBelongsToCollection() {
        return belongsToCollection;
    }

    public long getBudget() {
        return budget;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public String getHomepage() {
        return homepage;
    }

    public String getImdbID() {
        return imdbID;
    }

    public List<ProductionCompany> getProductionCompanies() {
        return productionCompanies;
    }

    public List<ProductionCountry> getProductionCountries() {
        return productionCountries;
    }

    public int getRuntime() {
        return runtime;
    }

    public List<Language> getSpokenLanguages() {
        return spokenLanguages;
    }

    public String getTagline() {
        return tagline;
    }

    public String getStatus() {
        return status;
    }

    public long getRevenue() {
        return revenue;
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Setter methods">
    public void setBelongsToCollection(BelongsToCollection belongsToCollection) {
        this.belongsToCollection = belongsToCollection;
    }

    public void setRevenue(long revenue) {
        this.revenue = revenue;
    }

    public void setBudget(long budget) {
        this.budget = budget;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }

    public void setProductionCompanies(List<ProductionCompany> productionCompanies) {
        this.productionCompanies = productionCompanies;
    }

    public void setProductionCountries(List<ProductionCountry> productionCountries) {
        this.productionCountries = productionCountries;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public void setSpokenLanguages(List<Language> spokenLanguages) {
        this.spokenLanguages = spokenLanguages;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    // </editor-fold>

    //<editor-fold defaultstate="collapsed" desc="AppendToResponse Getters">
    public List<AlternativeTitle> getAlternativeTitles() {
        return alternativeTitles;
    }

    public List<MediaCreditCast> getCast() {
        return credits.getCast();
    }

    public List<MediaCreditCrew> getCrew() {
        return credits.getCrew();
    }

    public List<Artwork> getImages() {
        return images;
    }

    public List<Keyword> getKeywords() {
        return keywords;
    }

    public List<ReleaseInfo> getReleases() {
        return releases;
    }

    public List<Video> getVideos() {
        return videos;
    }

    public List<Translation> getTranslations() {
        return translations;
    }

    public List<MovieDetails> getSimilarMovies() {
        return similarMovies;
    }

    public List<UserList> getLists() {
        return lists;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public List<ChangeKeyItem> getChanges() {
        return changes;
    }
    // </editor-fold>

    //<editor-fold defaultstate="collapsed" desc="AppendToResponse Setters">
    @JsonSetter("alternative_titles")
    public void setAlternativeTitles(AlternativeTitlesResponse alternativeTitles) {
        this.alternativeTitles = alternativeTitles.getAlternativeTitles();
        addMethod(MovieMethod.ALTERNATIVE_TITLES);
    }

    @JsonSetter("credits")
    public void setCredits(MediaCreditList credits) {
        this.credits = credits;
        addMethod(MovieMethod.CREDITS);
    }

    @JsonSetter("images")
    public void setImages(ImageResponse images) {
        this.images = images.getAll();
        addMethod(MovieMethod.IMAGES);
    }

    @JsonSetter("keywords")
    public void setKeywords(MovieKeywordsResponse keywords) {
        this.keywords = keywords.getKeywords();
        addMethod(MovieMethod.KEYWORDS);
    }

    @JsonSetter("release_dates")
    public void setReleases(ReleaseInfoResponse releases) {
        this.releases = releases.getCountries();
        addMethod(MovieMethod.RELEASES);
    }


    @JsonSetter("videos")
    public void setVideos(VideosResponse trailers) {
        Log.i("MOvie-", "Numero de Videos: " + trailers.getVideos().size());
        this.videos = trailers.getVideos();
        addMethod(MovieMethod.VIDEOS);
    }

    @JsonSetter("translations")
    public void setTranslations(TranslationsResponse translations) {
        this.translations = translations.getTranslations();
        addMethod(MovieMethod.TRANSLATIONS);
    }

    @JsonSetter("similar")
    public void setSimilarMovies(GenericListResponse<MovieDetails> similarMovies) {
        this.similarMovies = similarMovies.getResults();
        addMethod(MovieMethod.SIMILAR);
    }

    @JsonSetter("lists")
    public void setLists(GenericListResponse<UserList> lists) {
        this.lists = lists.getResults();
        addMethod(MovieMethod.LISTS);
    }

    @JsonSetter("reviews")
    public void setReviews(GenericListResponse<Review> reviews) {
        this.reviews = reviews.getResults();
        addMethod(MovieMethod.REVIEWS);
    }

    @JsonSetter("changes")
    public void setChanges(ChangesResponse changes) {
        this.changes = changes.getChangedItems();
    }
    // </editor-fold>

    private void addMethod(MovieMethod method) {
        methods.add(method);
    }

}
