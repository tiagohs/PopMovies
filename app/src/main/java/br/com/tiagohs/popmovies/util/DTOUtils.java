package br.com.tiagohs.popmovies.util;

import java.util.ArrayList;
import java.util.List;

import br.com.tiagohs.popmovies.model.atwork.Artwork;
import br.com.tiagohs.popmovies.model.atwork.ArtworkMedia;
import br.com.tiagohs.popmovies.model.credits.MediaCreditCast;
import br.com.tiagohs.popmovies.model.credits.MediaCreditCrew;
import br.com.tiagohs.popmovies.model.dto.ImageDTO;
import br.com.tiagohs.popmovies.model.dto.ItemListDTO;
import br.com.tiagohs.popmovies.model.dto.MovieListDTO;
import br.com.tiagohs.popmovies.model.dto.PersonListDTO;
import br.com.tiagohs.popmovies.model.keyword.Keyword;
import br.com.tiagohs.popmovies.model.movie.Genre;
import br.com.tiagohs.popmovies.model.movie.Movie;
import br.com.tiagohs.popmovies.model.movie.MovieDetails;
import br.com.tiagohs.popmovies.model.person.PersonFind;
import br.com.tiagohs.popmovies.model.person.PersonInfo;

public class DTOUtils {
    private static final String DIRECTOR = "Directing";

    public static List<MovieListDTO> createMovieListDTO(List<Movie> movies) {
        List<MovieListDTO> moviesDTO = new ArrayList<>();

        MovieListDTO movieListDTO;
        for (Movie m : movies) {
            if (m.getPosterPath() != null) {
                movieListDTO = new MovieListDTO(m.getId(), m.getTitle(), m.getPosterPath(), m.getVoteAverage());
                moviesDTO.add(movieListDTO);
            }
        }

        return moviesDTO;
    }

    public static List<MovieListDTO> createMovieDetailsListDTO(List<MovieDetails> movies) {
        List<MovieListDTO> moviesDTO = new ArrayList<>();

        MovieListDTO movieListDTO;
        for (Movie movie : movies) {
            if (movie.getPosterPath() != null) {
                movieListDTO = new MovieListDTO(movie.getId(), movie.getTitle(), movie.getPosterPath(), movie.getVoteAverage());
                moviesDTO.add(movieListDTO);
            }

        }

        return moviesDTO;
    }


    public static List<ItemListDTO> createKeywordsItemsListDTO(List<Keyword> keywords) {
        List<ItemListDTO> list = new ArrayList<>();

        ItemListDTO itemListDTO;
        for (Keyword k : keywords) {
            itemListDTO = new ItemListDTO(k.getId(), k.getName());
            list.add(itemListDTO);
        }

        return list;
    }

    public static List<ItemListDTO> createDirectorsItemsListDTO(List<MediaCreditCrew> crews) {
        List<ItemListDTO> list = new ArrayList<>();

        ItemListDTO itemListDTO;
        for (MediaCreditCrew crew : crews) {
            itemListDTO = new ItemListDTO(crew.getId(), crew.getName());
            if (crew.getDepartment().equals(DIRECTOR) && !list.contains(itemListDTO))
                list.add(itemListDTO);
        }

        return list;
    }

    public static List<PersonListDTO> createCastPersonListDTO(List<MediaCreditCast> cast) {
        List<PersonListDTO> personListDTOs = new ArrayList<>();

        PersonListDTO personListDTO;
        for (MediaCreditCast c : cast) {
            personListDTO = new PersonListDTO(c.getId(), c.getArtworkPath(), c.getName(), c.getCharacter());
            personListDTOs.add(personListDTO);
        }

        return personListDTOs;
    }

    public static List<PersonListDTO> createCrewPersonListDTO(List<MediaCreditCrew> cast) {
        List<PersonListDTO> personListDTOs = new ArrayList<>();

        PersonListDTO personListDTO;
        for (MediaCreditCrew c : cast) {
            personListDTO = new PersonListDTO(c.getId(), c.getArtworkPath(), c.getName(), c.getDepartment());
            personListDTOs.add(personListDTO);
        }

        return personListDTOs;
    }

    public static List<PersonListDTO> createPersonListDTO(List<PersonFind> persons) {
        List<PersonListDTO> personListDTOs = new ArrayList<>();

        PersonListDTO personListDTO;
        for (PersonFind c : persons) {
            personListDTO = new PersonListDTO(c.getId(), c.getProfilePath(), c.getName(), "");
            personListDTOs.add(personListDTO);
        }

        return personListDTOs;
    }

    public static List<ItemListDTO> createGenresItemsListDTO(List<Genre> genres) {
        List<ItemListDTO> list = new ArrayList<>();

        ItemListDTO itemListDTO;
        for (Genre g : genres) {
            itemListDTO = new ItemListDTO(g.getId(), g.getName());
            list.add(itemListDTO);
        }

        return list;
    }

    public static List<ImageDTO> createPersonImagesDTO(PersonInfo person, int numTotalImages, List<Artwork> images) {
        numTotalImages = images.size() <= numTotalImages ? images.size() : numTotalImages;
        List<ImageDTO> imageDTOs = new ArrayList<>();

        ImageDTO imageDTO;
        Artwork image;
        for (int cont = 0; cont < numTotalImages; cont++) {
            image = images.get(cont);
            imageDTO = new ImageDTO(person.getId(), image.getId(), image.getFilePath());
            imageDTOs.add(imageDTO);
        }

        return imageDTOs;
    }

    public static List<ImageDTO> createPersonImagesBackgroundDTO(PersonInfo person, int numImages, List<ArtworkMedia> images) {
        List<ImageDTO> imageDTOs = new ArrayList<>();

        ImageDTO imageDTO;
        for (Artwork ar : images) {
            imageDTO = new ImageDTO(person.getId(), ar.getId(), ar.getFilePath());
            imageDTOs.add(imageDTO);
        }

        return imageDTOs;
    }

    public static List<MovieListDTO> createPersonKnowForMoviesDTO(List<MovieListDTO> movies, int maxSize) {
        int numMovies = movies.size() < maxSize ? movies.size() : maxSize;
        List<MovieListDTO> moviesMovieListDTO = new ArrayList<>();

        MovieListDTO c;
        for (int cont = 0; cont < numMovies; cont++) {
            c = movies.get(cont);

            if (moviesMovieListDTO.contains(c))
                continue;
            else {
                moviesMovieListDTO.add(c);
            }

        }

        return moviesMovieListDTO;
    }

}
