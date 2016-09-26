package br.com.tiagohs.popmovies.model.dto;

import android.support.v7.widget.RecyclerView;

import java.io.Serializable;

import br.com.tiagohs.popmovies.util.enumerations.Sort;

public class ListSpecificsDTO implements Serializable {

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
}
