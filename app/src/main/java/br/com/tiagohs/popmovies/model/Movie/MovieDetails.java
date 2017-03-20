package br.com.tiagohs.popmovies.model.movie;


import android.os.Parcel;
import android.util.Log;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import br.com.tiagohs.popmovies.model.ChangeKeyItem;
import br.com.tiagohs.popmovies.model.Language;
import br.com.tiagohs.popmovies.model.atwork.Artwork;
import br.com.tiagohs.popmovies.model.credits.MediaCreditCast;
import br.com.tiagohs.popmovies.model.credits.MediaCreditCrew;
import br.com.tiagohs.popmovies.model.keyword.Keyword;
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

public class MovieDetails extends Movie  {

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
    private List<ProductionCompany> productionCompanies = new ArrayList<>();
    @JsonProperty("production_countries")
    private List<ProductionCountry> productionCountries = new ArrayList<>();
    @JsonProperty("runtime")
    private int runtime;
    @JsonProperty("spoken_languages")
    private List<Language> spokenLanguages = new ArrayList<>();
    @JsonProperty("tagline")
    private String tagline;
    @JsonProperty("status")
    private String status;

    // AppendToResponse
    private Set<MovieMethod> methods = EnumSet.noneOf(MovieMethod.class);
    // AppendToResponse Properties
    private List<AlternativeTitle> alternativeTitles = new ArrayList<>();
    private MediaCreditList credits = new MediaCreditList();
    private List<Artwork> images = new ArrayList<>();
    private List<Keyword> keywords = new ArrayList<>();
    private List<ReleaseInfo> releases = new ArrayList<>();
    private List<Video> videos = new ArrayList<>();
    private List<Translation> translations = new ArrayList<>();
    private List<MovieDetails> similarMovies = new ArrayList<>();
    private List<Review> reviews = new ArrayList<>();
    private List<ChangeKeyItem> changes = new ArrayList<>();

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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeParcelable(this.belongsToCollection, flags);
        dest.writeLong(this.budget);
        dest.writeTypedList(this.genres);
        dest.writeString(this.homepage);
        dest.writeString(this.imdbID);
        dest.writeLong(this.revenue);
        dest.writeList(this.productionCompanies);
        dest.writeList(this.productionCountries);
        dest.writeInt(this.runtime);
        dest.writeList(this.spokenLanguages);
        dest.writeString(this.tagline);
        dest.writeString(this.status);
        dest.writeValue(this.methods);
        dest.writeTypedList(this.alternativeTitles);
        dest.writeParcelable(this.credits, flags);
        dest.writeTypedList(this.images);
        dest.writeTypedList(this.keywords);
        dest.writeList(this.releases);
        dest.writeTypedList(this.videos);
        dest.writeTypedList(this.translations);
        dest.writeTypedList(this.similarMovies);
        dest.writeList(this.reviews);
        dest.writeList(this.changes);
    }

    public MovieDetails() {
    }

    protected MovieDetails(Parcel in) {
        super(in);
        this.belongsToCollection = in.readParcelable(BelongsToCollection.class.getClassLoader());
        this.budget = in.readLong();
        this.genres = in.createTypedArrayList(Genre.CREATOR);
        this.homepage = in.readString();
        this.imdbID = in.readString();
        this.revenue = in.readLong();
        this.productionCompanies = new ArrayList<ProductionCompany>();
        in.readList(this.productionCompanies, ProductionCompany.class.getClassLoader());
        this.productionCountries = new ArrayList<ProductionCountry>();
        in.readList(this.productionCountries, ProductionCountry.class.getClassLoader());
        this.runtime = in.readInt();
        this.spokenLanguages = new ArrayList<Language>();
        in.readList(this.spokenLanguages, Language.class.getClassLoader());
        this.tagline = in.readString();
        this.status = in.readString();
        this.methods = in.readParcelable(getClass().getClassLoader());
        this.alternativeTitles = in.createTypedArrayList(AlternativeTitle.CREATOR);
        this.credits = in.readParcelable(MediaCreditList.class.getClassLoader());
        this.images = in.createTypedArrayList(Artwork.CREATOR);
        this.keywords = in.createTypedArrayList(Keyword.CREATOR);
        this.releases = new ArrayList<ReleaseInfo>();
        in.readList(this.releases, ReleaseInfo.class.getClassLoader());
        this.videos = in.createTypedArrayList(Video.CREATOR);
        this.translations = in.createTypedArrayList(Translation.CREATOR);
        this.similarMovies = in.createTypedArrayList(MovieDetails.CREATOR);
        this.reviews = new ArrayList<Review>();
        in.readList(this.reviews, Review.class.getClassLoader());
        this.changes = new ArrayList<ChangeKeyItem>();
        in.readList(this.changes, ChangeKeyItem.class.getClassLoader());
    }

    public static final Creator<MovieDetails> CREATOR = new Creator<MovieDetails>() {
        @Override
        public MovieDetails createFromParcel(Parcel source) {
            return new MovieDetails(source);
        }

        @Override
        public MovieDetails[] newArray(int size) {
            return new MovieDetails[size];
        }
    };
}
