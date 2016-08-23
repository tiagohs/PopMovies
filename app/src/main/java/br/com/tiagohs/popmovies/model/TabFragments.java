package br.com.tiagohs.popmovies.model;

import android.support.v4.app.Fragment;

/**
 * Created by Tiago Henrique on 21/08/2016.
 */
public class TabFragments {
    private String tabName;
    private Fragment fragment;

    public TabFragments(String tabName, Fragment fragment) {
        this.tabName = tabName;
        this.fragment = fragment;
    }

    public String getTabName() {
        return tabName;
    }

    public void setTabName(String tabName) {
        this.tabName = tabName;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }
}
