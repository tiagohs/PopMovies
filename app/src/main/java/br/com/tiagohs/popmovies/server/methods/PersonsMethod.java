package br.com.tiagohs.popmovies.server.methods;

import javax.inject.Inject;

import br.com.tiagohs.popmovies.model.person.PersonFind;
import br.com.tiagohs.popmovies.model.person.PersonInfo;
import br.com.tiagohs.popmovies.model.response.GenericListResponse;
import br.com.tiagohs.popmovies.model.response.PersonMoviesResponse;
import br.com.tiagohs.popmovies.server.services.PersonsService;
import br.com.tiagohs.popmovies.util.UrlUtils;
import br.com.tiagohs.popmovies.ui.tools.SharedPreferenceManager;
import io.reactivex.Observable;

public class PersonsMethod {

    private PersonsService mPersonsService;
    private SharedPreferenceManager mSharedPreferenceManager;

    @Inject
    public PersonsMethod(PersonsService personsService, SharedPreferenceManager sharedPreferenceManager) {
        this.mPersonsService = personsService;
        this.mSharedPreferenceManager = sharedPreferenceManager;
    }

    public Observable<GenericListResponse<PersonFind>> getPersons(int currentPage) {
        return mPersonsService.getPersons(String.valueOf(currentPage), mSharedPreferenceManager.getDefaultLanguage());
    }

    public Observable<PersonMoviesResponse> getPersonMoviesCredits(int personID, int currentPage) {
        return mPersonsService.getPersonMoviesCredits(String.valueOf(personID), String.valueOf(currentPage), mSharedPreferenceManager.getDefaultLanguage());
    }

    public Observable<PersonInfo> getPersonDetails(int personID, String[] appendToResponse) {
        return mPersonsService.getPersonDetails(String.valueOf(personID), UrlUtils.formatAppendToResponse(appendToResponse), mSharedPreferenceManager.getDefaultLanguage());
    }

    public Observable<PersonInfo> getPersonDetails(int personID, String[] appendToResponse, String language) {
        return mPersonsService.getPersonDetails(String.valueOf(personID), UrlUtils.formatAppendToResponse(appendToResponse), language);
    }


    public Observable<GenericListResponse<PersonFind>> searchPerson(String query,
                             Boolean includeAdult,
                             Integer currentPage) {
        return mPersonsService.searchPerson(query, includeAdult != null ? String.valueOf(includeAdult) : null,
                                            currentPage != null ? String.valueOf(currentPage) : null, mSharedPreferenceManager.getDefaultLanguage());
    }
}
