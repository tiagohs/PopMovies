package br.com.tiagohs.popmovies.model.dto;

import android.os.Parcel;
import android.os.Parcelable;

public class DiscoverDTO implements Parcelable {
    private String mRegion;
    private String mReleaseDateGte;
    private String mReleaseDateLte;
    private String mPrimaryReleaseDateGte;
    private String mPrimaryRelaseDateLte;
    private String mReleaseYear;
    private String mSortBy;
    private String mWithReleaseType;
    private String[] mAppendToResponse;
    private String mVoteAvaregeGte;
    private Integer mWithGenres;
    private Integer mWithKeywords;
    private boolean mIncludeAdult;

    public String getPrimaryReleaseDateGte() {
        return mPrimaryReleaseDateGte;
    }

    public void setPrimaryReleaseDateGte(String primaryReleaseDateGte) {
        mPrimaryReleaseDateGte = primaryReleaseDateGte;
    }

    public String getPrimaryRelaseDateLte() {
        return mPrimaryRelaseDateLte;
    }

    public void setPrimaryRelaseDateLte(String primaryRelaseDateLte) {
        mPrimaryRelaseDateLte = primaryRelaseDateLte;
    }

    public Integer getWithGenres() {
        return mWithGenres;
    }

    public void setWithGenres(Integer withGenres) {
        mWithGenres = withGenres;
    }

    public Integer getWithKeywords() {
        return mWithKeywords;
    }

    public void setWithKeywords(Integer withKeywords) {
        mWithKeywords = withKeywords;
    }

    public String getVoteAvaregeGte() {
        return mVoteAvaregeGte;
    }

    public void setVoteAvaregeGte(String voteAvaregeGte) {
        mVoteAvaregeGte = voteAvaregeGte;
    }

    public String getReleaseYear() {
        return mReleaseYear;
    }

    public void setReleaseYear(String releaseYear) {
        mReleaseYear = releaseYear;
    }

    public Boolean isIncludeAdult() {
        return mIncludeAdult;
    }

    public void setIncludeAdult(Boolean includeAdult) {
        mIncludeAdult = includeAdult;
    }

    public String getRegion() {
        return mRegion;
    }

    public void setRegion(String region) {
        mRegion = region;
    }

    public String getReleaseDateGte() {
        return mReleaseDateGte;
    }

    public void setReleaseDateGte(String releaseDateGte) {
        mReleaseDateGte = releaseDateGte;
    }

    public String getReleaseDateLte() {
        return mReleaseDateLte;
    }

    public void setReleaseDateLte(String releaseDateLte) {
        mReleaseDateLte = releaseDateLte;
    }

    public String getSortBy() {
        return mSortBy;
    }

    public void setSortBy(String sortBy) {
        mSortBy = sortBy;
    }

    public String getWithReleaseType() {
        return mWithReleaseType;
    }

    public void setWithReleaseType(String withReleaseType) {
        mWithReleaseType = withReleaseType;
    }

    public String[] getAppendToResponse() {
        return mAppendToResponse;
    }

    public void setAppendToResponse(String[] appendToResponse) {
        mAppendToResponse = appendToResponse;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mRegion);
        dest.writeString(this.mReleaseDateGte);
        dest.writeString(this.mReleaseDateLte);
        dest.writeString(this.mPrimaryReleaseDateGte);
        dest.writeString(this.mPrimaryRelaseDateLte);
        dest.writeString(this.mReleaseYear);
        dest.writeString(this.mSortBy);
        dest.writeString(this.mWithReleaseType);
        dest.writeStringArray(this.mAppendToResponse);
        dest.writeString(this.mVoteAvaregeGte);
        dest.writeValue(this.mWithGenres);
        dest.writeValue(this.mWithKeywords);
        dest.writeByte(this.mIncludeAdult ? (byte) 1 : (byte) 0);
    }

    public DiscoverDTO() {
    }

    protected DiscoverDTO(Parcel in) {
        this.mRegion = in.readString();
        this.mReleaseDateGte = in.readString();
        this.mReleaseDateLte = in.readString();
        this.mPrimaryReleaseDateGte = in.readString();
        this.mPrimaryRelaseDateLte = in.readString();
        this.mReleaseYear = in.readString();
        this.mSortBy = in.readString();
        this.mWithReleaseType = in.readString();
        this.mAppendToResponse = in.createStringArray();
        this.mVoteAvaregeGte = in.readString();
        this.mWithGenres = (Integer) in.readValue(Integer.class.getClassLoader());
        this.mWithKeywords = (Integer) in.readValue(Integer.class.getClassLoader());
        this.mIncludeAdult = in.readByte() != 0;
    }

    public static final Parcelable.Creator<DiscoverDTO> CREATOR = new Parcelable.Creator<DiscoverDTO>() {
        @Override
        public DiscoverDTO createFromParcel(Parcel source) {
            return new DiscoverDTO(source);
        }

        @Override
        public DiscoverDTO[] newArray(int size) {
            return new DiscoverDTO[size];
        }
    };
}
