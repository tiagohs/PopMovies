package br.com.tiagohs.popmovies.database.repository;

import java.util.List;

import br.com.tiagohs.popmovies.model.db.GenreDB;
import io.reactivex.Observable;

public interface GenreRepository {

    Observable<Long> saveGenres(List<GenreDB> genres, long movieID);
    Observable<Long> saveGenre(GenreDB genre, long movieID);

    Observable<GenreDB> findGenreByGenreID(int genreID, long movieID);

    Observable<List<GenreDB>> findAllGenreDB(long movieID);
    Observable<List<Integer>> findAllGenreID(long movieID);

    List<GenreDB> findAllGenreDBDatabase(long movieID);
    List<Integer> findAllGenreIDDatabase(long movieID);
}
