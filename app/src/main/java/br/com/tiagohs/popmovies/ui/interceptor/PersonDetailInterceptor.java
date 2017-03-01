package br.com.tiagohs.popmovies.ui.interceptor;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.com.tiagohs.popmovies.ui.contracts.PersonDetailContract;
import br.com.tiagohs.popmovies.model.credits.CreditMovieBasic;
import br.com.tiagohs.popmovies.model.dto.MovieListDTO;
import br.com.tiagohs.popmovies.model.person.PersonInfo;
import br.com.tiagohs.popmovies.server.methods.PersonsMethod;
import io.reactivex.Observable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class PersonDetailInterceptor implements PersonDetailContract.PersonDetailInterceptor {

    private PersonsMethod mPersonsMethod;

    @Inject
    public PersonDetailInterceptor(PersonsMethod personsMethod) {
        mPersonsMethod = personsMethod;
    }

    @Override
    public Observable<PersonInfo> getPersonDetails(int personID, String[] appendToResponse) {
        return mPersonsMethod.getPersonDetails(personID, appendToResponse)
                             .subscribeOn(Schedulers.io())
                             .map(new Function<PersonInfo, PersonInfo>() {
                                 @Override
                                 public PersonInfo apply(PersonInfo personInfo) throws Exception {
                                     List<CreditMovieBasic> cast = personInfo.getMovieCredits().getCast();
                                     List<CreditMovieBasic> crew = personInfo.getMovieCredits().getCrew();

                                     cast.addAll(crew);
                                     List<MovieListDTO> moviesMovieListDTO = new ArrayList<>();

                                     for (CreditMovieBasic c : cast) {
                                         MovieListDTO movie = new MovieListDTO(c.getId(), c.getTitle(), c.getArtworkPath(), null);

                                         if (moviesMovieListDTO.contains(movie))
                                             continue;
                                         else {
                                             moviesMovieListDTO.add(movie);
                                         }

                                     }

                                     personInfo.setMoviesCarrer(moviesMovieListDTO);

                                     return personInfo;
                                 }
                             });
    }

    @Override
    public Observable<PersonInfo> getPersonDetails(int personID, String[] appendToResponse, String language) {
        return mPersonsMethod.getPersonDetails(personID, appendToResponse, language)
                             .subscribeOn(Schedulers.io());
    }
}
