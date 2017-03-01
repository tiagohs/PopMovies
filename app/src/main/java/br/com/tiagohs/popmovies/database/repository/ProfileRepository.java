package br.com.tiagohs.popmovies.database.repository;

import java.util.List;

import br.com.tiagohs.popmovies.model.db.ProfileDB;
import br.com.tiagohs.popmovies.model.dto.GenrerMoviesDTO;
import io.reactivex.Observable;

public interface ProfileRepository {

    Observable<Long> saveProfile(ProfileDB profile);

    Observable<Integer> deleteProfileByID(long id);
    Observable<Integer> deleteProfileByUsername(String username);

    Observable<ProfileDB> findProfileByUserUsername(String username);

    Observable<Boolean> hasMoviesWatched(long profileID);
    Observable<Boolean> hasMoviesWantSee(long profileID);
    Observable<Boolean> hasMoviesDontWantSee(long profileID);
    Observable<Boolean> hasMoviesFavorite(long profileID);

    Observable<Long> getTotalHoursWatched(long profileID);
    Observable<Long> getTotalMovies(long profileID);
    Observable<Long> getTotalMoviesWatched(long profileID);
    Observable<Long> getTotalMoviesWantSee(long profileID);
    Observable<Long> getTotalMoviesDontWantSee(long profileID);
    Observable<Long> getTotalFavorites(long profileID);
    Observable<Long> getTotalMoviesByGenrer(int genreID, long profileID);

    Observable<List<GenrerMoviesDTO>> getAllGenrersSaved(long profileID);
}
