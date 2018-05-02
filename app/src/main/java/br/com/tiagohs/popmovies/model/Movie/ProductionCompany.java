package br.com.tiagohs.popmovies.model.movie;


import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.tiagohs.popmovies.model.IDNameAbstract;

public class ProductionCompany extends IDNameAbstract implements Parcelable {

    @JsonProperty("logo_path")
    private String logoPath;

    @JsonProperty("description")
    private String description;

    @JsonProperty("headquarters")
    private String headquarters;

    @JsonProperty("homepage")
    private String homepage;

    @JsonProperty("origin_country")
    private String originCountry;

    @JsonProperty("parent_company")
    private String parentCompany;

    public String getLogoPath() {
        return logoPath;
    }

    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHeadquarters() {
        return headquarters;
    }

    public void setHeadquarters(String headquarters) {
        this.headquarters = headquarters;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public String getOriginCountry() {
        return originCountry;
    }

    public void setOriginCountry(String originCountry) {
        this.originCountry = originCountry;
    }

    public String getParentCompany() {
        return parentCompany;
    }

    public void setParentCompany(String parentCompany) {
        this.parentCompany = parentCompany;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public ProductionCompany() {
    }

    protected ProductionCompany(Parcel in) {
    }

    public static final Parcelable.Creator<ProductionCompany> CREATOR = new Parcelable.Creator<ProductionCompany>() {
        @Override
        public ProductionCompany createFromParcel(Parcel source) {
            return new ProductionCompany(source);
        }

        @Override
        public ProductionCompany[] newArray(int size) {
            return new ProductionCompany[size];
        }
    };
}
