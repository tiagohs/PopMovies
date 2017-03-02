package br.com.tiagohs.popmovies.model.dto;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

import br.com.tiagohs.popmovies.R;

/**
 * Created by Tiago Henrique on 09/10/2016.
 */

public class SortByItemDTO implements Parcelable {
    private String name;
    private String value;

    public SortByItemDTO(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return getName();
    }

    public static List<SortByItemDTO> getItemsDefault(Context context) {
        List<SortByItemDTO> list = new ArrayList<>();

        list.add(new SortByItemDTO(context.getString(R.string.popularidade_maior), "popularity.desc"));
        list.add(new SortByItemDTO(context.getString(R.string.popularidade_menor), "popularity.asc"));
        list.add(new SortByItemDTO(context.getString(R.string.release_date_maior), "release_date.desc"));
        list.add(new SortByItemDTO(context.getString(R.string.release_date_menor), "release_date.asc"));
        list.add(new SortByItemDTO(context.getString(R.string.revenue_maior), "revenue.desc"));
        list.add(new SortByItemDTO(context.getString(R.string.revenue_menor), "revenue.asc"));
        list.add(new SortByItemDTO(context.getString(R.string.primary_release_date_maior), "primary_release_date.desc"));
        list.add(new SortByItemDTO(context.getString(R.string.primary_release_date_menor), "primary_release_date.asc"));
        list.add(new SortByItemDTO(context.getString(R.string.original_title_maior), "original_title.desc"));
        list.add(new SortByItemDTO(context.getString(R.string.original_title_menor), "original_title.asc"));
        list.add(new SortByItemDTO(context.getString(R.string.vote_average_maior), "vote_average.desc"));
        list.add(new SortByItemDTO(context.getString(R.string.vote_average_menor), "vote_average.asc"));
        list.add(new SortByItemDTO(context.getString(R.string.vote_count_maior), "vote_count.desc"));
        list.add(new SortByItemDTO(context.getString(R.string.vote_count_desc), "vote_count.asc"));

        return list;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.value);
    }

    protected SortByItemDTO(Parcel in) {
        this.name = in.readString();
        this.value = in.readString();
    }

    public static final Parcelable.Creator<SortByItemDTO> CREATOR = new Parcelable.Creator<SortByItemDTO>() {
        @Override
        public SortByItemDTO createFromParcel(Parcel source) {
            return new SortByItemDTO(source);
        }

        @Override
        public SortByItemDTO[] newArray(int size) {
            return new SortByItemDTO[size];
        }
    };
}
