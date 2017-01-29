package br.com.tiagohs.popmovies.data.repository;

import java.util.List;

import br.com.tiagohs.popmovies.model.db.GenreDB;

public interface GenreRepository {

    void saveGenres(List<GenreDB> genres, long movieID);
    long saveGenre(GenreDB genre, long movieID);
    GenreDB findGenreByGenreID(int genreID, long movieID);
    List<GenreDB> findAllGenreDB(long movieID);
    List<Integer> findAllGenreID(long movieID);
}
