package br.com.tiagohs.popmovies.model.dto;

import android.os.Parcel;
import android.os.Parcelable;

import br.com.tiagohs.popmovies.util.enumerations.ListType;
import br.com.tiagohs.popmovies.util.enumerations.Sort;

public class ListActivityDTO implements Parcelable {
    private int id;
    private String nameActivity;
    private String subtitleActivity;
    private Sort mSortList;
    private int layoutID;
    private ListType listType;

    public ListActivityDTO(int id, String nameActivity, Sort sortList, int layoutID, ListType listType) {
        this(id, nameActivity, null, sortList, layoutID, listType);
    }

    public ListActivityDTO(int id, String nameActivity, String subtitleActivity, Sort sortList, int layoutID, ListType listType) {
        this.id = id;
        this.nameActivity = nameActivity;
        this.mSortList = sortList;
        this.layoutID = layoutID;
        this.subtitleActivity = subtitleActivity;
        this.listType = listType;
    }

    public ListActivityDTO(String nameActivity, String subtitleActivity, int layoutID, ListType listType) {
        this.nameActivity = nameActivity;
        this.layoutID = layoutID;
        this.subtitleActivity = subtitleActivity;
        this.listType = listType;
    }

    public ListActivityDTO(String nameActivity, int layoutID, Sort sortList, ListType listType) {
        this.nameActivity = nameActivity;
        this.layoutID = layoutID;
        this.mSortList = sortList;
        this.listType = listType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameActivity() {
        return nameActivity;
    }

    public void setNameActivity(String nameActivity) {
        this.nameActivity = nameActivity;
    }

    public Sort getSortList() {
        return mSortList;
    }

    public void setSortList(Sort sortList) {
        this.mSortList = sortList;
    }

    public int getLayoutID() {
        return layoutID;
    }

    public void setLayoutID(int layoutID) {
        this.layoutID = layoutID;
    }

    public String getSubtitleActivity() {
        return subtitleActivity;
    }

    public void setSubtitleActivity(String subtitleActivity) {
        this.subtitleActivity = subtitleActivity;
    }

    public ListType getListType() {
        return listType;
    }

    public void setListType(ListType listType) {
        this.listType = listType;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.nameActivity);
        dest.writeString(this.subtitleActivity);
        dest.writeInt(this.mSortList == null ? -1 : this.mSortList.ordinal());
        dest.writeInt(this.layoutID);
        dest.writeInt(this.listType == null ? -1 : this.listType.ordinal());
    }

    protected ListActivityDTO(Parcel in) {
        this.id = in.readInt();
        this.nameActivity = in.readString();
        this.subtitleActivity = in.readString();
        int tmpMSortList = in.readInt();
        this.mSortList = tmpMSortList == -1 ? null : Sort.values()[tmpMSortList];
        this.layoutID = in.readInt();
        int tmpListType = in.readInt();
        this.listType = tmpListType == -1 ? null : ListType.values()[tmpListType];
    }

    public static final Parcelable.Creator<ListActivityDTO> CREATOR = new Parcelable.Creator<ListActivityDTO>() {
        @Override
        public ListActivityDTO createFromParcel(Parcel source) {
            return new ListActivityDTO(source);
        }

        @Override
        public ListActivityDTO[] newArray(int size) {
            return new ListActivityDTO[size];
        }
    };
}
