package br.com.tiagohs.popmovies.model.dto;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class FilterValuesDTO {
    private String sortBy;
    private boolean includeAdult;
    private String releaseYear;
    private String primaryRelaseDateGte;
    private String primaryRelaseDateLte;
    private String voteAverageGte;
    private String voteAverageLte;

    private SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");

    public FilterValuesDTO() {
        sortBy = "popularity.desc";
        includeAdult = false;
        releaseYear = null;
        primaryRelaseDateGte = null;
        primaryRelaseDateLte = null;
        voteAverageGte = null;
        voteAverageLte = null;
    }

    public FilterValuesDTO(String sortBy, boolean includeAdult, String releaseYear, String primaryRelaseDateGte, String primaryRelaseDateLte, String voteAverageGte, String voteAverageLte) {
        this.sortBy = sortBy;
        this.includeAdult = includeAdult;
        this.releaseYear = releaseYear;
        this.primaryRelaseDateGte = primaryRelaseDateGte;
        this.primaryRelaseDateLte = primaryRelaseDateLte;
        this.voteAverageGte = voteAverageGte;
        this.voteAverageLte = voteAverageLte;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public boolean isIncludeAdult() {
        return includeAdult;
    }

    public void setIncludeAdult(boolean includeAdult) {
        this.includeAdult = includeAdult;
    }

    public String getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(String releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getPrimaryRelaseDateGte() {
        return primaryRelaseDateGte;
    }

    public void setPrimaryRelaseDateGte(String primaryRelaseDateGte) {
        this.primaryRelaseDateGte = primaryRelaseDateGte;
    }

    public void setPrimaryRelaseDateGteByDate(Calendar primaryRelaseDateGte) {
        this.primaryRelaseDateGte = formater.format(primaryRelaseDateGte.getTime());
    }

    public String getPrimaryRelaseDateLte() {
        return primaryRelaseDateLte;
    }

    public void setPrimaryRelaseDateLte(String primaryRelaseDateLte) {
        this.primaryRelaseDateLte = primaryRelaseDateLte;
    }

    public void setPrimaryRelaseDateLteByDate(Calendar primaryRelaseDateLte) {
        this.primaryRelaseDateLte = formater.format(primaryRelaseDateLte.getTime());
    }

    public String getVoteAverageGte() {
        return voteAverageGte;
    }

    public void setVoteAverageGte(String voteAverageGte) {
        this.voteAverageGte = voteAverageGte;
    }

    public String getVoteAverageLte() {
        return voteAverageLte;
    }

    public void setVoteAverageLte(String voteAverageLte) {
        this.voteAverageLte = voteAverageLte;
    }
}
