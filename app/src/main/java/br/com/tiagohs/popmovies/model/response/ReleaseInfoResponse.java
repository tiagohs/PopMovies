package br.com.tiagohs.popmovies.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import br.com.tiagohs.popmovies.model.movie.ReleaseInfo;

/**
 * Created by Tiago Henrique on 24/08/2016.
 */
public class ReleaseInfoResponse implements Parcelable {

    @JsonProperty("id")
    private int id;

    @JsonProperty("results")
    private List<ReleaseInfo> countries;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<ReleaseInfo> getCountries() {
        return countries;
    }

    public void setCountries(List<ReleaseInfo> countries) {
        this.countries = countries;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeTypedList(this.countries);
    }

    public ReleaseInfoResponse() {
    }

    protected ReleaseInfoResponse(Parcel in) {
        this.id = in.readInt();
        this.countries = in.createTypedArrayList(ReleaseInfo.CREATOR);
    }

    public static final Parcelable.Creator<ReleaseInfoResponse> CREATOR = new Parcelable.Creator<ReleaseInfoResponse>() {
        @Override
        public ReleaseInfoResponse createFromParcel(Parcel source) {
            return new ReleaseInfoResponse(source);
        }

        @Override
        public ReleaseInfoResponse[] newArray(int size) {
            return new ReleaseInfoResponse[size];
        }
    };
}
