package br.com.tiagohs.popmovies.model.dto;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;

import java.io.Serializable;

import br.com.tiagohs.popmovies.util.enumerations.Sort;

public class ListSpecificsDTO implements Serializable, Parcelable {

    private RecyclerView.LayoutManager mLayoutManager;
    private Sort mTypeList;

    public ListSpecificsDTO(RecyclerView.LayoutManager layoutManager, Sort typeList) {
        mLayoutManager = layoutManager;
        mTypeList = typeList;
    }

    public RecyclerView.LayoutManager getLayoutManager() {
        return mLayoutManager;
    }

    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        mLayoutManager = layoutManager;
    }

    public Sort getTypeList() {
        return mTypeList;
    }

    public void setTypeList(Sort typeList) {
        mTypeList = typeList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.mLayoutManager);
        dest.writeInt(this.mTypeList == null ? -1 : this.mTypeList.ordinal());
    }

    protected ListSpecificsDTO(Parcel in) {
        this.mLayoutManager = in.readParcelable(RecyclerView.LayoutManager.class.getClassLoader());
        int tmpMTypeList = in.readInt();
        this.mTypeList = tmpMTypeList == -1 ? null : Sort.values()[tmpMTypeList];
    }

    public static final Parcelable.Creator<ListSpecificsDTO> CREATOR = new Parcelable.Creator<ListSpecificsDTO>() {
        @Override
        public ListSpecificsDTO createFromParcel(Parcel source) {
            return new ListSpecificsDTO(source);
        }

        @Override
        public ListSpecificsDTO[] newArray(int size) {
            return new ListSpecificsDTO[size];
        }
    };
}
