package br.com.tiagohs.popmovies.util;

import java.util.ArrayList;
import java.util.List;

import br.com.tiagohs.popmovies.model.credits.MediaCreditCast;
import br.com.tiagohs.popmovies.model.credits.MediaCreditCrew;
import br.com.tiagohs.popmovies.model.dto.ItemListDTO;
import br.com.tiagohs.popmovies.model.dto.MovieListDTO;
import br.com.tiagohs.popmovies.model.dto.PersonListDTO;
import br.com.tiagohs.popmovies.model.keyword.Keyword;
import br.com.tiagohs.popmovies.model.movie.Genre;
import br.com.tiagohs.popmovies.model.movie.Movie;

public class DTOUtils {

    public static List<MovieListDTO> createMovieListDTO(List<Movie> movies) {
        List<MovieListDTO> moviesDTO = new ArrayList<>();

        for (Movie movie : movies) {
            if (movie.getPosterPath() != null)
                moviesDTO.add(new MovieListDTO(movie.getId(), movie.getTitle(), movie.getPosterPath(), movie.getVoteAverage()));
        }

        return moviesDTO;
    }

    public static List<ItemListDTO> createKeywordsItemsListDTO(List<Keyword> keywords) {
        List<ItemListDTO> list = new ArrayList<>();

        for (Keyword k : keywords)
            list.add(new ItemListDTO(k.getId(), k.getName()));

        return list;
    }

    public static List<PersonListDTO> createCastPersonListDTO(List<MediaCreditCast> cast) {
        List<PersonListDTO> personListDTO = new ArrayList<>();

        for (MediaCreditCast c : cast)
            personListDTO.add(new PersonListDTO(c.getId(), c.getArtworkPath(), c.getName(), c.getCharacter()));

        return personListDTO;
    }

    public static List<PersonListDTO> createCrewPersonListDTO(List<MediaCreditCrew> cast) {
        List<PersonListDTO> personListDTO = new ArrayList<>();

        for (MediaCreditCrew c : cast)
            personListDTO.add(new PersonListDTO(c.getId(), c.getArtworkPath(), c.getName(), c.getDepartment()));

        return personListDTO;
    }

    public static List<ItemListDTO> createGenresItemsListDTO(List<Genre> genres) {
        List<ItemListDTO> list = new ArrayList<>();

        for (Genre g : genres)
            list.add(new ItemListDTO(g.getId(), g.getName()));

        return list;
    }

}
