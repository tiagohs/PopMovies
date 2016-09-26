package br.com.tiagohs.popmovies.view;

import br.com.tiagohs.popmovies.model.person.PersonInfo;

/**
 * Created by Tiago Henrique on 03/09/2016.
 */
public interface PersonDetailView extends BaseView {

    void atualizarView(PersonInfo person);

    void showProgressBar();
    void hideProgressBar();
}
