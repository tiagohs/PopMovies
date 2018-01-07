package br.com.tiagohs.popmovies.server.methods;

import javax.inject.Inject;

import br.com.tiagohs.popmovies.BuildConfig;
import br.com.tiagohs.popmovies.model.dto.DiscoverDTO;
import br.com.tiagohs.popmovies.model.movie.CollectionDetails;
import br.com.tiagohs.popmovies.model.movie.Movie;
import br.com.tiagohs.popmovies.model.movie.MovieDetails;
import br.com.tiagohs.popmovies.model.response.GenericListResponse;
import br.com.tiagohs.popmovies.model.response.GenresResponse;
import br.com.tiagohs.popmovies.model.response.ImageResponse;
import br.com.tiagohs.popmovies.model.response.MovieResponse;
import br.com.tiagohs.popmovies.model.response.RankingResponse;
import br.com.tiagohs.popmovies.model.response.VideosResponse;
import br.com.tiagohs.popmovies.server.services.MoviesService;
import br.com.tiagohs.popmovies.util.UrlUtils;
import br.com.tiagohs.popmovies.ui.tools.SharedPreferenceManager;
import io.reactivex.Observable;

public class MoviesMethod {
    protected static final String BASE_URL_OMDB_MOVIES = "http://www.omdbapi.com/";

    private MoviesService mMoviesService;
    private SharedPreferenceManager mSharedPreferenceManager;

    @Inject
    public MoviesMethod(MoviesService moviesService, SharedPreferenceManager sharedPreferenceManager) {
        this.mMoviesService = moviesService;
        this.mSharedPreferenceManager = sharedPreferenceManager;
    }

    public Observable<MovieDetails> getMovieDetails(int movieID,
                                String[] appendToResponse) {
        return mMoviesService.getMovieDetails(String.valueOf(movieID), UrlUtils.formatAppendToResponse(appendToResponse), mSharedPreferenceManager.getDefaultLanguage());
    }

    public Observable<MovieDetails> getMovieDetails(int movieID, String[] appendToResponse, String language) {
        return mMoviesService.getMovieDetails(String.valueOf(movieID), UrlUtils.formatAppendToResponse(appendToResponse), language);
    }

    public Observable<RankingResponse> getMovieRankings(String imdbID) {
        return mMoviesService.getMovieRankings(BASE_URL_OMDB_MOVIES + "?i=" + imdbID + "&tomatoes=true" + "&apikey=" + BuildConfig.OMDB_KEY);
    }

    public Observable<GenericListResponse<Movie>> getMovieSimilars(int movieID, int currentPage) {
        return mMoviesService.getMovieSimilars(String.valueOf(movieID), String.valueOf(currentPage), mSharedPreferenceManager.getDefaultLanguage());
    }

    public Observable<ImageResponse> getMovieImagens(int movieID) {
        return mMoviesService.getMovieImagens(String.valueOf(movieID));
    }

    public Observable<VideosResponse> getMovieVideos(int movieID) {
        return mMoviesService.getMovieVideos(String.valueOf(movieID), mSharedPreferenceManager.getDefaultLanguage());
    }

    public Observable<VideosResponse> getMovieVideos(int movieID, String language) {
        return mMoviesService.getMovieVideos(String.valueOf(movieID), language);
    }

    public Observable<GenericListResponse<Movie>> getMoviesByGenres(int genreID, int currentPage) {
        return mMoviesService.getMoviesByGenres(String.valueOf(genreID), String.valueOf(currentPage), mSharedPreferenceManager.getDefaultLanguage());
    }

    public Observable<GenericListResponse<Movie>> getMoviesByKeywords(int keywordID, int currentPage) {
        return mMoviesService.getMoviesByKeywords(String.valueOf(keywordID), String.valueOf(currentPage), mSharedPreferenceManager.getDefaultLanguage());
    }

    public Observable<MovieResponse> getMoviesDiscover(int currentPage, DiscoverDTO discoverDTO) {
        return mMoviesService.getMoviesDiscover(String.valueOf(currentPage), discoverDTO.getRegion(), discoverDTO.getReleaseDateGte(),
                                                discoverDTO.getReleaseDateLte(), discoverDTO.getPrimaryReleaseDateGte(),
                                                discoverDTO.getSortBy(), discoverDTO.getWithReleaseType(), UrlUtils.formatAppendToResponse(discoverDTO.getAppendToResponse()), discoverDTO.getReleaseYear(),
                                                discoverDTO.getWithGenres() != null ? String.valueOf(discoverDTO.getWithGenres()) : null,
                                                discoverDTO.isIncludeAdult() != null ? String.valueOf(discoverDTO.isIncludeAdult()) : null,
                                                discoverDTO.getWithKeywords() != null ? String.valueOf(discoverDTO.getWithKeywords()) : null,
                                                discoverDTO.getVoteAvaregeGte(), mSharedPreferenceManager.getDefaultLanguage());
    }

    public Observable<GenresResponse> getGenres() {
        return mMoviesService.getGenres(mSharedPreferenceManager.getDefaultLanguage());
    }

    public Observable<CollectionDetails> getMovieCollections(int collectionID) {
        return mMoviesService.getMovieCollections(String.valueOf(collectionID), mSharedPreferenceManager.getDefaultLanguage());
    }

    public Observable<GenericListResponse<Movie>> searchMovies(String query,
                             Boolean includeAdult,
                             Integer searchYear,
                             Integer primaryReleaseYear,
                             Integer currentPage) {
        return mMoviesService.searchMovies(query, includeAdult != null ? String.valueOf(includeAdult) : null,
                                            searchYear != null ? String.valueOf(searchYear) : null,
                                            primaryReleaseYear != null ? String.valueOf(primaryReleaseYear) : null,
                                            currentPage != null ? String.valueOf(currentPage) : null, mSharedPreferenceManager.getDefaultLanguage());
    }
}
