package br.com.tiagohs.popmovies.model.dto;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class FilterValuesDTO implements Parcelable {
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.sortBy);
        dest.writeByte(this.includeAdult ? (byte) 1 : (byte) 0);
        dest.writeString(this.releaseYear);
        dest.writeString(this.primaryRelaseDateGte);
        dest.writeString(this.primaryRelaseDateLte);
        dest.writeString(this.voteAverageGte);
        dest.writeString(this.voteAverageLte);
        dest.writeSerializable(this.formater);
    }

    protected FilterValuesDTO(Parcel in) {
        this.sortBy = in.readString();
        this.includeAdult = in.readByte() != 0;
        this.releaseYear = in.readString();
        this.primaryRelaseDateGte = in.readString();
        this.primaryRelaseDateLte = in.readString();
        this.voteAverageGte = in.readString();
        this.voteAverageLte = in.readString();
        this.formater = (SimpleDateFormat) in.readSerializable();
    }

    public static final Parcelable.Creator<FilterValuesDTO> CREATOR = new Parcelable.Creator<FilterValuesDTO>() {
        @Override
        public FilterValuesDTO createFromParcel(Parcel source) {
            return new FilterValuesDTO(source);
        }

        @Override
        public FilterValuesDTO[] newArray(int size) {
            return new FilterValuesDTO[size];
        }
    };
}
