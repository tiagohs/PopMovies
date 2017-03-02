package br.com.tiagohs.popmovies.model.dto;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Locale;

/**
 * Created by Tiago on 22/01/2017.
 */

public class LocaleDTO implements Comparable<LocaleDTO>,Parcelable {
    private String displayCountryName;
    private String displayCountryLanguage;
    private String isoCountry;
    private String isoLanguage;
    private Locale locale;

    public LocaleDTO(String displayCountryName, String displayCountryLanguage, String isoCountry, String isoLanguage, Locale locale) {
        this.displayCountryName = displayCountryName;
        this.displayCountryLanguage = displayCountryLanguage;
        this.isoCountry = isoCountry;
        this.isoLanguage = isoLanguage;
        this.locale = locale;
    }

    public LocaleDTO(String displayCountryName) {
        this.displayCountryName = displayCountryName;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public String getDisplayCountryName() {
        return displayCountryName;
    }

    public void setDisplayCountryName(String displayCountryName) {
        this.displayCountryName = displayCountryName;
    }

    public String getDisplayCountryLanguage() {
        return displayCountryLanguage;
    }

    public void setDisplayCountryLanguage(String displayCountryLanguage) {
        this.displayCountryLanguage = displayCountryLanguage;
    }

    public String getIsoCountry() {
        return isoCountry;
    }

    public void setIsoCountry(String isoCountry) {
        this.isoCountry = isoCountry;
    }

    public String getIsoLanguage() {
        return isoLanguage;
    }

    public void setIsoLanguage(String isoLanguage) {
        this.isoLanguage = isoLanguage;
    }

    @Override
    public int compareTo(LocaleDTO o) {
        return getDisplayCountryName().compareTo(o.getDisplayCountryName());
    }

    @Override
    public String toString() {
        return getDisplayCountryName();
    }

    @Override
    public boolean equals(Object obj) {

        if (obj instanceof LocaleDTO) {
            LocaleDTO l = (LocaleDTO) obj;
            return l.getDisplayCountryName().equals(getDisplayCountryName());
        }
        return false;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.displayCountryName);
        dest.writeString(this.displayCountryLanguage);
        dest.writeString(this.isoCountry);
        dest.writeString(this.isoLanguage);
        dest.writeSerializable(this.locale);
    }

    protected LocaleDTO(Parcel in) {
        this.displayCountryName = in.readString();
        this.displayCountryLanguage = in.readString();
        this.isoCountry = in.readString();
        this.isoLanguage = in.readString();
        this.locale = (Locale) in.readSerializable();
    }

    public static final Parcelable.Creator<LocaleDTO> CREATOR = new Parcelable.Creator<LocaleDTO>() {
        @Override
        public LocaleDTO createFromParcel(Parcel source) {
            return new LocaleDTO(source);
        }

        @Override
        public LocaleDTO[] newArray(int size) {
            return new LocaleDTO[size];
        }
    };
}
