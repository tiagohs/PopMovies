package br.com.tiagohs.popmovies.model.dto;

import android.support.v4.app.Fragment;

import java.util.Map;

import br.com.tiagohs.popmovies.util.enumerations.Sort;

public class HomeDTO {
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

}
