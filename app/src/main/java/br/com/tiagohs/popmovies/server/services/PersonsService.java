package br.com.tiagohs.popmovies.server.services;

import br.com.tiagohs.popmovies.model.person.PersonFind;
import br.com.tiagohs.popmovies.model.person.PersonInfo;
import br.com.tiagohs.popmovies.model.response.GenericListResponse;
import br.com.tiagohs.popmovies.model.response.PersonMoviesResponse;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PersonsService {

    @GET("person/popular?")
    Observable<GenericListResponse<PersonFind>> getPersons(@Query("page") String currentPage,
                                                           @Query("language") String language);

    @GET("person/{person_id}/movie_credits?")
    Observable<PersonMoviesResponse> getPersonMoviesCredits(@Path("person_id") String personID,
                                                            @Query("page") String currentPage,
                                                            @Query("language") String language);

    @GET("person/{person_id}?")
    Observable<PersonInfo> getPersonDetails(@Path("person_id") String personID,
                                            @Query("append_to_response") String appendToResponse,
                                            @Query("language") String language);

    @GET("search/person?")
    Observable<GenericListResponse<PersonFind>> searchPerson(@Query("query") String query,
                                                             @Query("include_adult") String includeAdult,
                                                             @Query("page") String currentPage,
                                                             @Query("language") String language);
}
