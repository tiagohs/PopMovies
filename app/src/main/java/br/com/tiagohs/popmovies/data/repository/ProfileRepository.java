package br.com.tiagohs.popmovies.data.repository;

import android.content.Context;
import java.util.List;

import br.com.tiagohs.popmovies.model.db.ProfileDB;
import br.com.tiagohs.popmovies.model.dto.GenrerMoviesDTO;

public interface ProfileRepository {

    long saveProfile(ProfileDB profile);

    void deleteProfileByID(long id);
    void deleteProfileByUsername(String username);

    ProfileDB findProfileByUserUsername(String username);

    boolean hasMoviesWatched(long profileID);
    boolean hasMoviesWantSee(long profileID);
    boolean hasMoviesDontWantSee(long profileID);
    boolean hasMoviesFavorite(long profileID);

    long getTotalHoursWatched(long profileID);
    long getTotalMovies(long profileID);
    long getTotalMoviesWatched(long profileID);
    long getTotalMoviesWantSee(long profileID);
    long getTotalMoviesDontWantSee(long profileID);
    long getTotalFavorites(long profileID);
    long getTotalMoviesByGenrer(int genreID, long profileID);

    List<GenrerMoviesDTO> getAllGenrersSaved(long profileID);
}
