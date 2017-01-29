package br.com.tiagohs.popmovies.presenter;

import br.com.tiagohs.popmovies.view.PersonDetailView;

public interface PersonDetailPresenter extends BasePresenter<PersonDetailView> {

    void getPersonDetails(int personID, String tag);
}
