package br.com.tiagohs.popmovies.model.dto;

import java.io.Serializable;

import br.com.tiagohs.popmovies.util.enumerations.Sort;

/**
 * Created by Tiago Henrique on 10/09/2016.
 */
public class ListActivityDTO implements Serializable {
    private int id;
    private String nameActivity;
    private Sort typeList;
    private int layoutID;

    public ListActivityDTO(int id, String nameActivity, Sort typeList, int layoutID) {
        this.id = id;
        this.nameActivity = nameActivity;
        this.typeList = typeList;
        this.layoutID = layoutID;
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

    public Sort getTypeList() {
        return typeList;
    }

    public void setTypeList(Sort typeList) {
        this.typeList = typeList;
    }

    public int getLayoutID() {
        return layoutID;
    }

    public void setLayoutID(int layoutID) {
        this.layoutID = layoutID;
    }
}
