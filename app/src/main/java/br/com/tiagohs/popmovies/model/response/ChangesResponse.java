package br.com.tiagohs.popmovies.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

import br.com.tiagohs.popmovies.model.ChangeKeyItem;

public class ChangesResponse implements Parcelable {

    @JsonProperty("changes")
    private List<ChangeKeyItem> changedItems = new ArrayList<>();

    public List<ChangeKeyItem> getChangedItems() {
        return changedItems;
    }

    public void setChangedItems(List<ChangeKeyItem> changes) {
        this.changedItems = changes;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.changedItems);
    }

    public ChangesResponse() {
    }

    protected ChangesResponse(Parcel in) {
        this.changedItems = new ArrayList<ChangeKeyItem>();
        in.readList(this.changedItems, ChangeKeyItem.class.getClassLoader());
    }

    public static final Parcelable.Creator<ChangesResponse> CREATOR = new Parcelable.Creator<ChangesResponse>() {
        @Override
        public ChangesResponse createFromParcel(Parcel source) {
            return new ChangesResponse(source);
        }

        @Override
        public ChangesResponse[] newArray(int size) {
            return new ChangesResponse[size];
        }
    };
}
