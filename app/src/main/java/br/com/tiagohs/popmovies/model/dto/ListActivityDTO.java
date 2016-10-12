package br.com.tiagohs.popmovies.model.dto;

import java.io.Serializable;

import br.com.tiagohs.popmovies.util.enumerations.ListType;
import br.com.tiagohs.popmovies.util.enumerations.Sort;

public class ListActivityDTO implements Serializable {
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
}
