package br.com.tiagohs.popmovies.util;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import br.com.tiagohs.popmovies.data.PopMoviesDB;
import br.com.tiagohs.popmovies.data.repository.MovieRepository;
import br.com.tiagohs.popmovies.model.atwork.Artwork;
import br.com.tiagohs.popmovies.model.atwork.ArtworkMedia;
import br.com.tiagohs.popmovies.model.credits.CreditMovieBasic;
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

    public static List<MovieListDTO> createMovieListDTO(Context context, List<Movie> movies, MovieRepository movieRepository) {
        List<MovieListDTO> moviesDTO = new ArrayList<>();

        for (Movie movie : movies) {
            if (movie.getPosterPath() != null)
                moviesDTO.add(checkIsMovieWasSaved(context, movie.getId(), movie.getTitle(), movie.getPosterPath(), movie.getVoteAverage(), movieRepository));
        }

        return moviesDTO;
    }

    public static List<MovieListDTO> createMovieDetailsListDTO(Context context, List<MovieDetails> movies, MovieRepository movieRepository) {
        List<MovieListDTO> moviesDTO = new ArrayList<>();

        for (Movie movie : movies) {
            if (movie.getPosterPath() != null)
                moviesDTO.add(checkIsMovieWasSaved(context, movie.getId(), movie.getTitle(), movie.getPosterPath(), movie.getVoteAverage(), movieRepository));
        }

        return moviesDTO;
    }

    private static MovieListDTO checkIsMovieWasSaved(Context context, int id, String title, String posterPath, String votes, MovieRepository movieRepository) {
        boolean jaAssistido = false;
        boolean favorito = false;
        Movie movieAssistido = movieRepository.findMovieByServerID(id, PrefsUtils.getCurrentUser(context).getProfileID());

        if (movieAssistido != null) {
            jaAssistido = true;
            favorito = movieAssistido.isFavorite();
        }

        return new MovieListDTO(id, title, posterPath, votes, jaAssistido, favorito);

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

    public static List<PersonListDTO> createPersonListDTO(List<PersonFind> persons) {
        List<PersonListDTO> personListDTO = new ArrayList<>();

        for (PersonFind c : persons)
            personListDTO.add(new PersonListDTO(c.getId(), c.getProfilePath(), c.getName(), ""));

        return personListDTO;
    }

    public static List<ItemListDTO> createGenresItemsListDTO(List<Genre> genres) {
        List<ItemListDTO> list = new ArrayList<>();

        for (Genre g : genres)
            list.add(new ItemListDTO(g.getId(), g.getName()));

        return list;
    }

    public static List<ImageDTO> createPersonImagesDTO(PersonInfo person, int numTotalImages, List<Artwork> images) {
        numTotalImages = images.size() <= numTotalImages ? images.size() - 1: numTotalImages;
        List<ImageDTO> imageDTOs = new ArrayList<>();

        for (int cont = 0; cont < numTotalImages; cont++) {
            Artwork image = images.get(cont);
            imageDTOs.add(new ImageDTO(person.getId(), image.getId(), image.getFilePath()));
        }

        return imageDTOs;
    }

    public static List<ImageDTO> createPersonImagesBackgroundDTO(PersonInfo person, int numImages, List<ArtworkMedia> images) {
        List<ImageDTO> imageDTOs = new ArrayList<>();

        for (int cont = 0; cont < (numImages - 1); cont++) {
            Artwork image = images.get(cont);
            imageDTOs.add(new ImageDTO(person.getId(), image.getId(), image.getFilePath()));
        }

        return imageDTOs;
    }

    public static List<MovieListDTO> createPersonKnowForMoviesDTO(Context context, List<CreditMovieBasic> personsMovies, int maxSize, MovieRepository movieRepository) {

        int numMovies = personsMovies.size() < maxSize ? personsMovies.size() - 1 : maxSize;
        List<MovieListDTO> moviesMovieListDTO = new ArrayList<>();

        for (int cont = 0; cont < numMovies; cont++) {
            CreditMovieBasic person = personsMovies.get(cont);
            moviesMovieListDTO.add(checkIsMovieWasSaved(context, person.getId(), person.getTitle(), person.getArtworkPath(), null, movieRepository));
        }

        return moviesMovieListDTO;
    }

}
