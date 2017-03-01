package br.com.tiagohs.popmovies.model.dto;

import java.io.Serializable;

public class DiscoverDTO implements Serializable {
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
}
