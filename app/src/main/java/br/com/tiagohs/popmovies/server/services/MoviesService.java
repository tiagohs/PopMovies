package br.com.tiagohs.popmovies.server.services;

import br.com.tiagohs.popmovies.model.movie.CollectionDetails;
import br.com.tiagohs.popmovies.model.movie.Movie;
import br.com.tiagohs.popmovies.model.movie.MovieDetails;
import br.com.tiagohs.popmovies.model.response.GenericListResponse;
import br.com.tiagohs.popmovies.model.response.GenresResponse;
import br.com.tiagohs.popmovies.model.response.ImageResponse;
import br.com.tiagohs.popmovies.model.response.MovieResponse;
import br.com.tiagohs.popmovies.model.response.RankingResponse;
import br.com.tiagohs.popmovies.model.response.VideosResponse;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface MoviesService {

    @GET("movie/{movie_id}?")
    Observable<MovieDetails> getMovieDetails(@Path("movie_id") String movieID,
                                             @Query("append_to_response") String appendToResponse,
                                             @Query("language") String language);

    @GET
    Observable<RankingResponse> getMovieRankings(@Url String urlRankings);

    @GET("movie/{movie_id}/similar?")
    Observable<GenericListResponse<Movie>> getMovieSimilars(@Path("movie_id") String movieID,
                                                            @Query("page") String currentPage,
                                                            @Query("language") String language);

    @GET("movie/{movie_id}/images?")
    Observable<ImageResponse> getMovieImagens(@Path("movie_id") String movieID);

    @GET("movie/{movie_id}/videos?")
    Observable<VideosResponse> getMovieVideos(@Path("movie_id") String movieID,
                                              @Query("language") String language);

    @GET("genre/{genre_id}/movies?")
    Observable<GenericListResponse<Movie>> getMoviesByGenres(@Path("genre_id") String genreID,
                                                             @Query("page") String currentPage,
                                                             @Query("language") String language);

    @GET("keyword/{keyword_id}/movies?")
    Observable<GenericListResponse<Movie>> getMoviesByKeywords(@Path("keyword_id") String keywordID,
                                                               @Query("page") String currentPage,
                                                               @Query("language") String language);

    @GET("discover/movie?")
    Observable<MovieResponse> getMoviesDiscover(@Query("page") String currentPage,
                                                @Query("region") String region,
                                                @Query("release_date.gte") String releaseDateGte,
                                                @Query("release_date.lte") String releaseDateLte,
                                                @Query("primary_release_date.gte") String primaryReleaseDateGte,
                                                @Query("sort_by") String sortBy,
                                                @Query("with_release_type") String withReleaseType,
                                                @Query("append_to_response") String appendToResponse,
                                                @Query("primary_release_year") String releaseYear,
                                                @Query("with_genres") String withGenres,
                                                @Query("include_adult") String includeAdult,
                                                @Query("with_keywords") String withKeywords,
                                                @Query("vote_average.gte") String voteAvaregeGte,
                                                @Query("language") String language);

    @GET("genre/movie/list?")
    Observable<GenresResponse> getGenres(@Query("language") String language);

    @GET("collection/{collection_id}?")
    Observable<CollectionDetails> getMovieCollections(@Path("collection_id") String collectionID,
                                                      @Query("language") String language);

    @GET("search/movie?")
    Observable<GenericListResponse<Movie>> searchMovies(@Query("query") String query,
                                                        @Query("include_adult") String includeAdult,
                                                        @Query("year") String searchYear,
                                                        @Query("primary_release_year") String primaryReleaseYear,
                                                        @Query("page") String currentPage,
                                                        @Query("language") String language);

}
