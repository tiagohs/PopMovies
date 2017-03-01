package br.com.tiagohs.popmovies.ui.interceptor;

import javax.inject.Inject;

import br.com.tiagohs.popmovies.ui.contracts.ListPersonsDefaultContract;
import br.com.tiagohs.popmovies.model.person.PersonFind;
import br.com.tiagohs.popmovies.model.response.GenericListResponse;
import br.com.tiagohs.popmovies.server.methods.PersonsMethod;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Tiago on 27/02/2017.
 */

public class ListPersonsDefaultInterceptor implements ListPersonsDefaultContract.ListPersonsDefaultInterceptor {

    private PersonsMethod mPersonsMethod;

    @Inject
    public ListPersonsDefaultInterceptor(PersonsMethod personsMethod) {
        mPersonsMethod = personsMethod;
    }

    @Override
    public Observable<GenericListResponse<PersonFind>> getPersons(int currentPage) {
        return mPersonsMethod.getPersons(currentPage)
                             .subscribeOn(Schedulers.io());
    }
}
