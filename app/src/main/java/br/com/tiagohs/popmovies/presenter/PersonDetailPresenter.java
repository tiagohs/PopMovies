package br.com.tiagohs.popmovies.presenter;

import br.com.tiagohs.popmovies.view.PersonDetailView;

/**
 * Created by Tiago Henrique on 03/09/2016.
 */
public interface PersonDetailPresenter extends BasePresenter<PersonDetailView> {

    void getPersonDetails(int personID, String tag);
}
