package br.com.tiagohs.popmovies.model.credits;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.util.ArrayList;
import java.util.List;

import br.com.tiagohs.popmovies.model.movie.Movie;
import br.com.tiagohs.popmovies.util.enumerations.MediaType;

public class MediaBasic implements Parcelable {

    @JsonProperty("_id")
    private String _id;

    @JsonProperty("id")
    private int id;

    @JsonProperty("adult")
    private boolean adult;

    @JsonProperty("name")
    private String nama;

    @JsonProperty("popularity")
    private int popularity;

    @JsonProperty("profile_path")
    private String profilePath;

    @JsonProperty("known_for")
    private List<Movie> knowFor;
    @JsonProperty("vote_average")
    private String voteAverage;
    @JsonProperty("vote_count")
    private long voteCount;

    @JsonProperty("poster_path")
    private String posterPath;

    @JsonProperty("air_date")
    private String airDate;
    @JsonProperty("season_number")
    private int seasonNumber;
    @JsonProperty("episode_number")
    private int episodeNumber;
    @JsonProperty("overview")
    private String overview;
    @JsonProperty("still_path")
    private String stillPath;
    @JsonProperty("show_id")
    private String showId;

    private MediaType mediaType;

    public MediaBasic() {
    }

    public MediaBasic(boolean adult, String nama, int popularity, String profilePath, List<Movie> knowFor) {
        this.adult = adult;
        this.nama = nama;
        this.popularity = popularity;
        this.profilePath = profilePath;
        this.knowFor = knowFor;
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

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public String getProfilePath() {
        return profilePath;
    }

    public void setProfilePath(String profilePath) {
        this.profilePath = profilePath;
    }

    public List<Movie> getKnowFor() {
        return knowFor;
    }

    public void setKnowFor(List<Movie> knowFor) {
        this.knowFor = knowFor;
    }

    public MediaType getMediaType() {
        return mediaType;
    }

    @JsonSetter("media_type")
    public void setMediaType(String mediaType) {
        this.mediaType = MediaType.fromString(mediaType);
    }

    public void setMediaType(MediaType mediaType) {
        this.mediaType = mediaType;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(String voteAverage) {
        this.voteAverage = voteAverage;
    }

    public long getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(long voteCount) {
        this.voteCount = voteCount;
    }

    public String getAirDate() {
        return airDate;
    }

    public void setAirDate(String airDate) {
        this.airDate = airDate;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public int getSeasonNumber() {
        return seasonNumber;
    }

    public void setSeasonNumber(int seasonNumber) {
        this.seasonNumber = seasonNumber;
    }

    public int getEpisodeNumber() {
        return episodeNumber;
    }

    public void setEpisodeNumber(int episodeNumber) {
        this.episodeNumber = episodeNumber;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getStillPath() {
        return stillPath;
    }

    public void setStillPath(String stillPath) {
        this.stillPath = stillPath;
    }

    public String getShowId() {
        return showId;
    }

    public void setShowId(String showId) {
        this.showId = showId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this._id);
        dest.writeInt(this.id);
        dest.writeByte(this.adult ? (byte) 1 : (byte) 0);
        dest.writeString(this.nama);
        dest.writeInt(this.popularity);
        dest.writeString(this.profilePath);
        dest.writeTypedList(this.knowFor);
        dest.writeString(this.voteAverage);
        dest.writeLong(this.voteCount);
        dest.writeString(this.posterPath);
        dest.writeString(this.airDate);
        dest.writeInt(this.seasonNumber);
        dest.writeInt(this.episodeNumber);
        dest.writeString(this.overview);
        dest.writeString(this.stillPath);
        dest.writeString(this.showId);
        dest.writeInt(this.mediaType == null ? -1 : this.mediaType.ordinal());
    }

    protected MediaBasic(Parcel in) {
        this._id = in.readString();
        this.id = in.readInt();
        this.adult = in.readByte() != 0;
        this.nama = in.readString();
        this.popularity = in.readInt();
        this.profilePath = in.readString();
        this.knowFor = (ArrayList) in.createTypedArrayList(Movie.CREATOR);
        this.voteAverage = in.readString();
        this.voteCount = in.readLong();
        this.posterPath = in.readString();
        this.airDate = in.readString();
        this.seasonNumber = in.readInt();
        this.episodeNumber = in.readInt();
        this.overview = in.readString();
        this.stillPath = in.readString();
        this.showId = in.readString();
        int tmpMediaType = in.readInt();
        this.mediaType = tmpMediaType == -1 ? null : MediaType.values()[tmpMediaType];
    }

    public static final Creator<MediaBasic> CREATOR = new Creator<MediaBasic>() {
        @Override
        public MediaBasic createFromParcel(Parcel source) {
            return new MediaBasic(source);
        }

        @Override
        public MediaBasic[] newArray(int size) {
            return new MediaBasic[size];
        }
    };
}

