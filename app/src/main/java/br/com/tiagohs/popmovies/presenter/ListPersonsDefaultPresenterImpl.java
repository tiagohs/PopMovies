package br.com.tiagohs.popmovies.presenter;

import br.com.tiagohs.popmovies.util.enumerations.Sort;
import br.com.tiagohs.popmovies.view.ListPersonsDefaultView;

public class ListPersonsDefaultPresenterImpl implements ListPersonsDefaultPresenter {

    private ListPersonsDefaultView mView;

    @Override
    public void setView(ListPersonsDefaultView view) {
        mView = view;
    }

    public void getPersons(Sort personSort) {

    }


}
