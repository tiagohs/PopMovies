package br.com.tiagohs.popmovies.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Item implements Parcelable {
    public String mItemTextMarcado;
    public String mItemTextDesmarcado;
    public int mItemIconMarcado;
    public int mItemIconDesmarcado;
    public int mColorID;

    public Item(String mItemTextMarcado, String mItemTextDesmarcado, int mItemIconMarcado, int mItemIconDesmarcado, int mColorID) {
        this.mItemTextMarcado = mItemTextMarcado;
        this.mItemTextDesmarcado = mItemTextDesmarcado;
        this.mItemIconMarcado = mItemIconMarcado;
        this.mItemIconDesmarcado = mItemIconDesmarcado;
        this.mColorID = mColorID;
    }

    public String getItemTextMarcado() {
        return mItemTextMarcado;
    }

    public void setItemTextMarcado(String mItemTextMarcado) {
        this.mItemTextMarcado = mItemTextMarcado;
    }

    public String getItemTextDesmarcado() {
        return mItemTextDesmarcado;
    }

    public void setItemTextDesmarcado(String mItemTextDesmarcado) {
        this.mItemTextDesmarcado = mItemTextDesmarcado;
    }

    public int getItemIconMarcado() {
        return mItemIconMarcado;
    }

    public void setItemIconMarcado(int mItemIconMarcado) {
        this.mItemIconMarcado = mItemIconMarcado;
    }

    public int getItemIconDesmarcado() {
        return mItemIconDesmarcado;
    }

    public void setItemIconDesmarcado(int mItemIconDesmarcado) {
        this.mItemIconDesmarcado = mItemIconDesmarcado;
    }

    public int getColorID() {
        return mColorID;
    }



    @Override
    public String toString() {
        return mItemTextMarcado;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mItemTextMarcado);
        dest.writeString(this.mItemTextDesmarcado);
        dest.writeInt(this.mItemIconMarcado);
        dest.writeInt(this.mItemIconDesmarcado);
        dest.writeInt(this.mColorID);
    }

    protected Item(Parcel in) {
        this.mItemTextMarcado = in.readString();
        this.mItemTextDesmarcado = in.readString();
        this.mItemIconMarcado = in.readInt();
        this.mItemIconDesmarcado = in.readInt();
        this.mColorID = in.readInt();
    }

    public static final Parcelable.Creator<Item> CREATOR = new Parcelable.Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel source) {
            return new Item(source);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };
}
