package br.com.tiagohs.popmovies.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tiago Henrique on 24/08/2016.
 */
public class GenericListResponse<T> implements Parcelable {

    @JsonProperty("adult")
    private boolean adult;

    @JsonProperty("id")
    private int id;

    @JsonProperty("page")
    private int page;

    @JsonProperty("total_pages")
    private int totalPage;

    @JsonProperty("total_results")
    private int totalResults;

    @JsonProperty("results")
    private List<T> results;

    public List<T> getResults() {
        return results;
    }

    @JsonCreator
    public void setResults(List<T> results) {
        this.results = results;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.adult ? (byte) 1 : (byte) 0);
        dest.writeInt(this.id);
        dest.writeInt(this.page);
        dest.writeInt(this.totalPage);
        dest.writeInt(this.totalResults);
        dest.writeList(this.results);
    }

    public GenericListResponse() {
    }

    protected GenericListResponse(Parcel in) {
        this.adult = in.readByte() != 0;
        this.id = in.readInt();
        this.page = in.readInt();
        this.totalPage = in.readInt();
        this.totalResults = in.readInt();
        this.results = new ArrayList<T>();
    }

    public static final Parcelable.Creator<GenericListResponse> CREATOR = new Parcelable.Creator<GenericListResponse>() {
        @Override
        public GenericListResponse createFromParcel(Parcel source) {
            return new GenericListResponse(source);
        }

        @Override
        public GenericListResponse[] newArray(int size) {
            return new GenericListResponse[size];
        }
    };
}
