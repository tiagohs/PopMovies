package br.com.tiagohs.popmovies.model.dto;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.Fragment;

import br.com.tiagohs.popmovies.util.enumerations.Sort;

public class HomeDTO implements Parcelable {
    private String mTitle;
    private String mSubtitle;
    private Sort mTypeList;
    private Fragment mFragment;

    public HomeDTO(String title, String subtitle, Sort typeList, Fragment fragment) {
        mTitle = title;
        mSubtitle = subtitle;
        mTypeList = typeList;
        mFragment = fragment;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getSubtitle() {
        return mSubtitle;
    }

    public void setSubtitle(String subtitle) {
        mSubtitle = subtitle;
    }

    public Sort getTypeList() {
        return mTypeList;
    }

    public void setTypeList(Sort typeList) {
        mTypeList = typeList;
    }

    public Fragment getFragment() {
        return mFragment;
    }

    public void setFragment(Fragment fragment) {
        mFragment = fragment;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mTitle);
        dest.writeString(this.mSubtitle);
        dest.writeInt(this.mTypeList == null ? -1 : this.mTypeList.ordinal());
        dest.writeValue(this.mFragment);
    }

    protected HomeDTO(Parcel in) {
        this.mTitle = in.readString();
        this.mSubtitle = in.readString();
        int tmpMTypeList = in.readInt();
        this.mTypeList = tmpMTypeList == -1 ? null : Sort.values()[tmpMTypeList];
        this.mFragment = in.readParcelable(Fragment.class.getClassLoader());
    }

    public static final Parcelable.Creator<HomeDTO> CREATOR = new Parcelable.Creator<HomeDTO>() {
        @Override
        public HomeDTO createFromParcel(Parcel source) {
            return new HomeDTO(source);
        }

        @Override
        public HomeDTO[] newArray(int size) {
            return new HomeDTO[size];
        }
    };
}
