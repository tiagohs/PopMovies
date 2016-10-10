package br.com.tiagohs.popmovies.presenter;

import br.com.tiagohs.popmovies.util.enumerations.Sort;
import br.com.tiagohs.popmovies.view.ListPersonsDefaultView;

/**
 * Created by Tiago Henrique on 09/10/2016.
 */

public interface ListPersonsDefaultPresenter extends BasePresenter<ListPersonsDefaultView> {

    void getPersons(Sort personSort);
}
