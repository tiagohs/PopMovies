package br.com.tiagohs.data.movies.repository

import br.com.tiagohs.data.movies.api.MovieApi

internal class MoviesRepositoryImpl(
    private val movieApi: MovieApi
): MoviesRepository {

    override suspend fun movieDetails(
        movieId: Int,
        language: String,
        appendToResponse: String?
    ) = movieApi.movieDetails(movieId, language, appendToResponse)

    override suspend fun similarMovies(
        movieId: Int,
        language: String,
        page: Int,
    ) = movieApi.similarMovies(movieId, language, page)

    override suspend fun credits(
        movieId: Int,
        language: String
    ) = movieApi.credits(movieId, language)

    override suspend fun images(
        movieId: Int,
        language: String,
        includeImageLanguage: String?
    ) = movieApi.images(movieId, language, includeImageLanguage)

    override suspend fun videos(
        movieId: Int,
        language: String,
        includeVideoLanguage: String?
    ) = movieApi.videos(movieId, language, includeVideoLanguage)

    override suspend fun discover(
        language: String,
        region: String?,
        sortBy: String,
        certificationCountry: String?,
        certification: String?,
        certificationLte: String?,
        certificationGte: String?,
        includeAdult: Boolean?,
        includeVideo: Boolean?,
        page: Int,
        primaryReleaseYear: Int?,
        primaryReleaseDateGte: String?,
        primaryReleaseDateLte: String?,
        releaseDateGte: String?,
        releaseDateLte: String?,
        year: Int?,
        voteCountGte: Long?,
        voteCountLte: Long?,
        voteAverageGte: Int?,
        voteAverageLte: Int?,
        withCast: String?,
        withCrew: String?,
        withPeople: String?,
        withCompanies: String?,
        withoutCompanies: String?,
        withGenres: String?,
        withoutGenres: String?,
        withKeywords: String?,
        withoutKeywords: String?,
        withRuntimeGte: Int?,
        withRuntimeLte: Int?,
        withOriginalLanguage: String?,
        withWatchProviders: String?,
        withWatchMonetizationTypes: String?,
        watchRegion: String?
    ) = movieApi.discover(language, region, sortBy, certificationCountry, certification, certificationLte, certificationGte, includeAdult, includeVideo, page, primaryReleaseYear, primaryReleaseDateGte, primaryReleaseDateLte, releaseDateGte, releaseDateLte, year, voteCountGte, voteCountLte, voteAverageGte, voteAverageLte, withCast, withCrew, withPeople, withCompanies, withoutCompanies, withGenres, withoutGenres, withKeywords, withoutKeywords, withRuntimeGte, withRuntimeLte, withOriginalLanguage, withWatchProviders, withWatchMonetizationTypes, watchRegion)

    override suspend fun search(
        language: String,
        query: String?,
        page: Int?,
        includeAdult: Boolean?,
        region: String?,
        year: Int?,
        primaryReleaseYear: Int?
    ) = movieApi.search(language, query, page, includeAdult, region, year, primaryReleaseYear)

    override suspend fun nowPlaying(
        language: String,
        page: Int?,
        region: String?,
    ) = movieApi.nowPlaying(language, page, region)

    override suspend fun popular(
        language: String,
        page: Int?,
        region: String?,
    ) = movieApi.popular(language, page, region)

    override suspend fun topRated(
        language: String,
        page: Int?,
        region: String?,
    ) = movieApi.topRated(language, page, region)

    override suspend fun upcoming(
        language: String,
        page: Int?,
        region: String?,
    ) = movieApi.upcoming(language, page, region)
}