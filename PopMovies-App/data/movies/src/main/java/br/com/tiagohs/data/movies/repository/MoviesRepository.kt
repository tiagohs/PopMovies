package br.com.tiagohs.data.movies.repository

import androidx.annotation.IntRange
import br.com.tiagohs.core.network.tmdb.*
import br.com.tiagohs.data.movies.models.movie.Movie
import br.com.tiagohs.data.movies.models.movie.Video
import br.com.tiagohs.data.movies.models.Result
import br.com.tiagohs.data.movies.models.movie.Credits
import br.com.tiagohs.data.movies.models.movie.MovieImages
import retrofit2.http.Query

interface MoviesRepository {

    suspend fun movieDetails(
        movieId: Int,
        language: String = DEFAULT_LANGUAGE,
        appendToResponse: String? = null
    ): Movie

    suspend fun similarMovies(
        movieId: Int,
        language: String = DEFAULT_LANGUAGE,
        @IntRange(from = 1, to = 1000) @Query(QUERY_PAGE) page: Int = 1,
    ): Result<Movie>

    suspend fun credits(
        movieId: Int,
        language: String = DEFAULT_LANGUAGE
    ): Credits

    suspend fun images(
        movieId: Int,
        language: String = DEFAULT_LANGUAGE,
        includeImageLanguage: String? = null
    ): MovieImages

    suspend fun videos(
        movieId: Int,
        language: String = DEFAULT_LANGUAGE,
        includeVideoLanguage: String? = null
    ): Result<Video>

    suspend fun discover(
        language: String = DEFAULT_LANGUAGE,
        region: String? = null,
        sortBy: String = DEFAULT_SORT_BY,
        certificationCountry: String? = null,
        certification: String? = null,
        certificationLte: String? = null,
        certificationGte: String? = null,
        includeAdult: Boolean? = null,
        includeVideo: Boolean? = null,
        page: Int = 1,
        primaryReleaseYear: Int? = null,
        primaryReleaseDateGte: String? = null,
        primaryReleaseDateLte: String? = null,
        releaseDateGte: String? = null,
        releaseDateLte: String? = null,
        year: Int? = null,
        voteCountGte: Long? = null,
        @IntRange(from = 1) voteCountLte: Long? = null,
        @IntRange(from = 0) voteAverageGte: Int? = null,
        @IntRange(from = 0) voteAverageLte: Int? = null,
        withCast: String? = null,
        withCrew: String? = null,
        withPeople: String? = null,
        withCompanies: String? = null,
        withoutCompanies: String? = null,
        withGenres: String? = null,
        withoutGenres: String? = null,
        withKeywords: String? = null,
        withoutKeywords: String? = null,
        withRuntimeGte: Int? = null,
        withRuntimeLte: Int? = null,
        withOriginalLanguage: String? = null,
        withWatchProviders: String? = null,
        withWatchMonetizationTypes: String? = null,
        watchRegion: String? = null
    ): Result<Movie>

    suspend fun search(
        language: String = DEFAULT_LANGUAGE,
        query: String? = null,
        page: Int? = null,
        includeAdult: Boolean? = null,
        region: String? = null,
        year: Int? = null,
        primaryReleaseYear: Int? = null
    ): Result<Movie>

    suspend fun nowPlaying(
        language: String = DEFAULT_LANGUAGE,
        page: Int? = null,
        region: String? = null,
    ): Result<Movie>

    suspend fun popular(
        language: String = DEFAULT_LANGUAGE,
        page: Int? = null,
        region: String? = null,
    ): Result<Movie>

    suspend fun topRated(
        language: String = DEFAULT_LANGUAGE,
        page: Int? = null,
        region: String? = null,
    ): Result<Movie>

    suspend fun upcoming(
        language: String = DEFAULT_LANGUAGE,
        page: Int? = null,
        region: String? = null,
    ): Result<Movie>
}