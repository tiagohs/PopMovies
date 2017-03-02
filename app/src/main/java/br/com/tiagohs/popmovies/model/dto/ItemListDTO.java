package br.com.tiagohs.popmovies.model.dto;

import android.os.Parcel;
import android.os.Parcelable;

import br.com.tiagohs.popmovies.util.enumerations.Sort;

public class ItemListDTO implements Parcelable {
    private int itemID;
    private String nameItem;
    private Sort typeItem;
    private DiscoverDTO mDiscoverDTO;

    public ItemListDTO(int itemID) {
        this.itemID = itemID;
    }

    public ItemListDTO(int itemID, String nameItem) {
        this.itemID = itemID;
        this.nameItem = nameItem;
    }

    public ItemListDTO(int itemID, String nameItem, Sort typeItem, DiscoverDTO discoverDTO) {
        this.itemID = itemID;
        this.nameItem = nameItem;
        this.typeItem = typeItem;
        this.mDiscoverDTO = discoverDTO;
    }

    public ItemListDTO(String nameItem, Sort typeItem, DiscoverDTO discoverDTO) {
        this.nameItem = nameItem;
        this.typeItem = typeItem;
        this.mDiscoverDTO = discoverDTO;
    }

    public ItemListDTO(String nameItem, Sort typeItem) {
        this.nameItem = nameItem;
        this.typeItem = typeItem;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public ItemListDTO() {
    }

    public String getNameItem() {
        return nameItem;
    }

    public void setNameItem(String nameItem) {
        this.nameItem = nameItem;
    }

    public Sort getTypeItem() {
        return typeItem;
    }

    public void setTypeItem(Sort typeItem) {
        this.typeItem = typeItem;
    }

    public DiscoverDTO getDiscoverDTO() {
        return mDiscoverDTO;
    }

    public void setDiscoverDTO(DiscoverDTO discoverDTO) {
        mDiscoverDTO = discoverDTO;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ItemListDTO) {
            ItemListDTO item = (ItemListDTO) obj;
            return getItemID() == item.getItemID();
        }

        return false;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.itemID);
        dest.writeString(this.nameItem);
        dest.writeInt(this.typeItem == null ? -1 : this.typeItem.ordinal());
        dest.writeParcelable(this.mDiscoverDTO, flags);
    }

    protected ItemListDTO(Parcel in) {
        this.itemID = in.readInt();
        this.nameItem = in.readString();
        int tmpTypeItem = in.readInt();
        this.typeItem = tmpTypeItem == -1 ? null : Sort.values()[tmpTypeItem];
        this.mDiscoverDTO = in.readParcelable(DiscoverDTO.class.getClassLoader());
    }

    public static final Parcelable.Creator<ItemListDTO> CREATOR = new Parcelable.Creator<ItemListDTO>() {
        @Override
        public ItemListDTO createFromParcel(Parcel source) {
            return new ItemListDTO(source);
        }

        @Override
        public ItemListDTO[] newArray(int size) {
            return new ItemListDTO[size];
        }
    };
}
