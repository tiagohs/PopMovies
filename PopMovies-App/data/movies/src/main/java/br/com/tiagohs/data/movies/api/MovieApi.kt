package br.com.tiagohs.data.movies.api

import br.com.tiagohs.core.network.tmdb.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import androidx.annotation.IntRange
import br.com.tiagohs.data.movies.models.movie.Credits
import br.com.tiagohs.data.movies.models.movie.Movie
import br.com.tiagohs.data.movies.models.movie.MovieImages
import br.com.tiagohs.data.movies.models.movie.Video
import br.com.tiagohs.data.movies.models.Result

/**
 * Movies API calls with Retrofit Setup. <a href="https://developers.themoviedb.org/3/getting-started/introduction">See The Movie Databse API</a>
 * @author Tiago Silva (tiago.silva.93@hotmail.com)
 */
interface MovieApi {
    /**
     * Get the primary information about a movie.
     *
     * @param movieId the movie identification
     * @param language Pass a ISO 639-1 value to display translated data for the fields that support it.
     * <b>minLength</b>: 2
     * <b>pattern</b>: ([a-z]{2})-([A-Z]{2})
     * <b>default</b>: pt-BR
     * @param appendToResponse Append requests within the same namespace to the response.
     * <b>pattern</b>: ([\w]+)
     */
    @GET("3/movie/${MOVIE_ID}")
    suspend fun movieDetails(
        @Path(MOVIE_ID) movieId: Int,
        @Query(QUERY_LANGUAGE) language: String = DEFAULT_LANGUAGE,
        @Query(QUERY_APPEND_TO_RESPONSE) appendToResponse: String?
    ): Movie

    /**
     * Get a list of similar movies. This is not the same as the "Recommendation" system you see on the website.
     * These items are assembled by looking at keywords and genres.
     *
     * @param movieId the movie identification
     * @param language Pass a ISO 639-1 value to display translated data for the fields that support it.
     * <b>minLength</b>: 2
     * <b>pattern</b>: ([a-z]{2})-([A-Z]{2})
     * <b>default</b>: en-US
     * @param page Specify which page to query.
     * <b>minimum<b>: 1
     * <b>maximum<b>: 1000
     * <b>default<b>: 1
     */
    @GET("3/movie/${MOVIE_ID}/similar")
    suspend fun similarMovies(
        @Path(MOVIE_ID) movieId: Int,
        @Query(QUERY_LANGUAGE) language: String = DEFAULT_LANGUAGE,
        @IntRange(from = 1, to = 1000) @Query(QUERY_PAGE) page: Int = 1,
    ): Result<Movie>

    /**
     * Get the cast and crew for a movie.
     *
     * @param movieId the movie identification
     * @param language Pass a ISO 639-1 value to display translated data for the fields that support it.
     * <b>minLength</b>: 2
     * <b>pattern</b>: ([a-z]{2})-([A-Z]{2})
     * <b>default</b>: en-US
     */
    @GET("3/movie/${MOVIE_ID}/credits")
    suspend fun credits(
        @Path(MOVIE_ID) movieId: Int,
        @Query(QUERY_LANGUAGE) language: String = DEFAULT_LANGUAGE
    ): Credits

    /**
     * Get the images that belong to a movie.
     * Querying images with a [language] parameter will filter the results. If you want to include a fallback language (especially useful for backdrops) you can use the [include_image_language] parameter. This should be a comma seperated value like so: <pre>include_image_language=en,null</pre>.
     *
     * @param movieId the movie identification
     * @param language Pass a ISO 639-1 value to display translated data for the fields that support it.
     * <b>minLength</b>: 2
     * <b>pattern</b>: ([a-z]{2})-([A-Z]{2})
     * <b>default</b>: en-US
     * @param includeImageLanguage Pass a ISO 639-1 list to display translated data for the fields that support it. Is accepted null as well.
     */
    @GET("3/movie/${MOVIE_ID}/images")
    suspend fun images(
        @Path(MOVIE_ID) movieId: Int,
        @Query(QUERY_LANGUAGE) language: String = DEFAULT_LANGUAGE,
        @Query(QUERY_INCLUDE_IMAGE_LANGUAGE) includeImageLanguage: String?
    ): MovieImages

    /**
     * Get the videos that have been added to a movie.
     *
     * @param movieId the movie identification
     * @param language Pass a ISO 639-1 value to display translated data for the fields that support it.
     * <b>minLength</b>: 2
     * <b>pattern</b>: ([a-z]{2})-([A-Z]{2})
     * <b>default</b>: en-US
     * @param includeVideoLanguage Pass a ISO 639-1 list to display translated data for the fields that support it. Is accepted null as well.
     */
    @GET("3/movie/${MOVIE_ID}/videos")
    suspend fun videos(
        @Path(MOVIE_ID) movieId: Int,
        @Query(QUERY_LANGUAGE) language: String = DEFAULT_LANGUAGE,
        @Query(QUERY_INCLUDE_VIDEO_LANGUAGE) includeVideoLanguage: String?
    ): Result<Video>

    /**
     * Discover movies by different types of data like average rating, number of votes, genres and certifications. You can get a valid list of certifications from the  method.
     *
     * Discover also supports a nice list of sort options. See below for all of the available options.
     *
     * Please note, when using [certification] \ [certificationLte] you must also specify [certificationCountry]. These two parameters work together in order to filter the results. You can only filter results with the countries we have added to our .
     *
     * If you specify the [region] parameter, the regional release date will be used instead of the primary release date. The date returned will be the first date based on your query (ie. if a [withReleaseType] is specified). It's important to note the order of the release types that are used. Specifying "2|3" would return the limited theatrical release date as opposed to "3|2" which would return the theatrical date.
     *
     * Also note that a number of filters support being comma (,) or pipe (|) separated. Comma's are treated like an <pre>AND</pre> and query while pipe's are an <pre>OR</pre>.
     *
     * @param language Pass a ISO 639-1 value to display translated data for the fields that support it.
     * <b>minLength</b>: 2
     * <b>pattern</b>: ([a-z]{2})-([A-Z]{2})
     * <b>default</b>: en-US
     * @param region Specify a ISO 3166-1 code to filter release dates. Must be uppercase.
     * <b>pattern</b>: ^[A-Z]{2}$
     * @param sortBy Choose from one of the many available sort options.
     * <b>Allowed Values</b>: , popularity.asc, popularity.desc, release_date.asc, release_date.desc, revenue.asc, revenue.desc, primary_release_date.asc, primary_release_date.desc, original_title.asc, original_title.desc, vote_average.asc, vote_average.desc, vote_count.asc, vote_count.desc
     * <b>default</b>: popularity.desc
     * @param certificationCountry Used in conjunction with the certification filter, use this to specify a country with a valid certification.
     * @param certification Filter results with a valid certification from the 'certification_country' field.
     * @param certificationLte Filter and only include movies that have a certification that is less than or equal to the specified value.
     * @param certificationGte Filter and only include movies that have a certification that is greater than or equal to the specified value.
     * @param includeAdult A filter and include or exclude adult movies.
     * @param includeVideo A filter to include or exclude videos.
     * @param page Specify the page of results to query.
     * <b>minimum<b>: 1
     * <b>maximum<b>: 1000
     * <b>default<b>: 1
     * @param primaryReleaseYear A filter to limit the results to a specific primary release year.
     * @param primaryReleaseDateGte Filter and only include movies that have a primary release date that is greater or equal to the specified value.
     * <b>format</b>: date yyyy-MM-dd
     * @param primaryReleaseDateLte Filter and only include movies that have a primary release date that is less than or equal to the specified value.
     * <b>format</b>: date yyyy-MM-dd
     * @param releaseDateGte Filter and only include movies that have a release date (looking at all release dates) that is greater or equal to the specified value.
     * <b>format</b>: date yyyy-MM-dd
     * @param releaseDateLte Filter and only include movies that have a release date (looking at all release dates) that is less than or equal to the specified value.
     * <b>format</b>: date yyyy-MM-dd
     * @param year A filter to limit the results to a specific year (looking at all release dates).
     * @param voteCountGte Filter and only include movies that have a vote count that is greater or equal to the specified value.
     * <b>minimum</b>: 0
     * @param voteCountLte Filter and only include movies that have a vote count that is less than or equal to the specified value.
     * <b>minimum</b>: 1
     * @param voteAverageGte Filter and only include movies that have a rating that is greater or equal to the specified value.
     * <b>minimum</b>: 0
     * @param voteAverageLte Filter and only include movies that have a rating that is less than or equal to the specified value.
     * <b>minimum</b>: 0
     * @param withCast A comma separated list of person ID's. Only include movies that have one of the ID's added as an actor.
     * @param withCrew A comma separated list of person ID's. Only include movies that have one of the ID's added as a crew member.
     * @param withPeople A comma separated list of person ID's. Only include movies that have one of the ID's added as a either a actor or a crew member.
     * @param withCompanies A comma separated list of production company ID's. Only include movies that have one of the ID's added as a production company.
     * @param withoutCompanies Filter the results to exclude the specific production companies you specify here. <pre>AND</pre> / <pre>OR</pre> filters are supported.
     * @param withGenres Comma separated value of genre ids that you want to include in the results.
     * @param withoutGenres Comma separated value of genre ids that you want to exclude from the results.
     * @param withKeywords A comma separated list of keyword ID's. Only includes movies that have one of the ID's added as a keyword.
     * @param withoutKeywords Exclude items with certain keywords. You can comma and pipe seperate these values to create an <pre>AND</pre> or <pre>OR</pre> logic.
     * @param withRuntimeGte Filter and only include movies that have a runtime that is greater or equal to a value.
     * @param withRuntimeLte Filter and only include movies that have a runtime that is less than or equal to a value.
     * @param withOriginalLanguage Specify an ISO 639-1 string to filter results by their original language value.
     * @param withWatchProviders A comma or pipe separated list of watch provider ID's. Combine this filter with [watchRegion] in order to filter your results by a specific watch provider in a specific region.
     * @param withWatchMonetizationTypes In combination with watch_region, you can filter by monetization type. Allowed Values: flatrate, free, ads, rent, buy
     * @param watchRegion An ISO 3166-1 code. Combine this filter with [withWatchProviders] in order to filter your results by a specific watch provider in a specific region.
     */
    @GET("3/discover/movie")
    suspend fun discover(
        @Query(QUERY_LANGUAGE) language: String = DEFAULT_LANGUAGE,
        @Query(QUERY_REGION) region: String?,
        @Query(QUERY_SORT_BY) sortBy: String = DEFAULT_SORT_BY,
        @Query(QUERY_CERTIFICATION_COUNTRY) certificationCountry: String?,
        @Query(QUERY_CERTIFICATION) certification: String?,
        @Query(QUERY_CERTIFICATION_GTE) certificationLte: String?,
        @Query(QUERY_CERTIFICATION_LTE) certificationGte: String?,
        @Query(QUERY_INCLUDE_ADULT) includeAdult: Boolean?,
        @Query(QUERY_INCLUDE_VIDEO) includeVideo: Boolean?,
        @IntRange(from = 1, to = 1000) @Query(QUERY_PAGE) page: Int = 1,
        @Query(QUERY_PRIMARY_RELEASE_YEAR) primaryReleaseYear: Int?,
        @Query(QUERY_PRIMARY_RELEASE_GTE) primaryReleaseDateGte: String?,
        @Query(QUERY_PRIMARY_RELEASE_LTE) primaryReleaseDateLte: String?,
        @Query(QUERY_RELEASE_DATE_GTE) releaseDateGte: String?,
        @Query(QUERY_RELEASE_DATE_LTE) releaseDateLte: String?,
        @Query(QUERY_YEAR) year: Int?,
        @Query(QUERY_VOTE_COUNT_GTE) voteCountGte: Long?,
        @IntRange(from = 1) @Query(QUERY_VOTE_COUNT_LTE) voteCountLte: Long?,
        @IntRange(from = 0) @Query(QUERY_VOTE_AVERAGE_GTE) voteAverageGte: Int?,
        @IntRange(from = 0) @Query(QUERY_VOTE_AVERAGE_LTE) voteAverageLte: Int?,
        @Query(QUERY_WITH_CAST) withCast: String?,
        @Query(QUERY_WITH_CREW) withCrew: String?,
        @Query(QUERY_WITH_PEOPLE) withPeople: String?,
        @Query(QUERY_WITH_COMPANIES) withCompanies: String?,
        @Query(QUERY_WITHOUT_COMPANIES) withoutCompanies: String?,
        @Query(QUERY_WITH_GENRES) withGenres: String?,
        @Query(QUERY_WITHOUT_GENRES) withoutGenres: String?,
        @Query(QUERY_WITH_KEYWORDS) withKeywords: String?,
        @Query(QUERY_WITHOUT_KEYWORDS) withoutKeywords: String?,
        @Query(QUERY_WITH_RUNTIME_GTE) withRuntimeGte: Int?,
        @Query(QUERY_WITH_RUNTIME_LTE) withRuntimeLte: Int?,
        @Query(QUERY_WITH_ORIGINAL_LANGUAGE) withOriginalLanguage: String?,
        @Query(QUERY_WITH_WATCH_PROVIDERS) withWatchProviders: String?,
        @Query(QUERY_WITH_WATCH_MONETIZATION_TYPES) withWatchMonetizationTypes: String?,
        @Query(QUERY_WATCH_REGION) watchRegion: String?
    ): Result<Movie>

    /**
     *
     *
     * @param language Pass a ISO 639-1 value to display translated data for the fields that support it.
     * <b>minLength</b>: 2
     * <b>pattern</b>: ([a-z]{2})-([A-Z]{2})
     * <b>default</b>: en-US
     */
    @GET("3/search/movie")
    suspend fun search(
        @Query(QUERY_LANGUAGE) language: String = DEFAULT_LANGUAGE,
        @Query(QUERY_QUERY) query: String?,
        @Query(QUERY_PAGE) page: Int?,
        @Query(QUERY_INCLUDE_ADULT) includeAdult: Boolean?,
        @Query(QUERY_REGION) region: String?,
        @Query(QUERY_YEAR) year: Int?,
        @Query(QUERY_PRIMARY_RELEASE_YEAR) primaryReleaseYear: Int?
    ): Result<Movie>

    /**
     *
     *
     * @param language Pass a ISO 639-1 value to display translated data for the fields that support it.
     * <b>minLength</b>: 2
     * <b>pattern</b>: ([a-z]{2})-([A-Z]{2})
     * <b>default</b>: en-US
     */
    @GET("3/movie/now_playing")
    suspend fun nowPlaying(
        @Query(QUERY_LANGUAGE) language: String = DEFAULT_LANGUAGE,
        @Query(QUERY_PAGE) page: Int?,
        @Query(QUERY_REGION) region: String?,
    ): Result<Movie>

    /**
     *
     *
     * @param language Pass a ISO 639-1 value to display translated data for the fields that support it.
     * <b>minLength</b>: 2
     * <b>pattern</b>: ([a-z]{2})-([A-Z]{2})
     * <b>default</b>: en-US
     */
    @GET("3/movie/popular")
    suspend fun popular(
        @Query(QUERY_LANGUAGE) language: String = DEFAULT_LANGUAGE,
        @Query(QUERY_PAGE) page: Int?,
        @Query(QUERY_REGION) region: String?,
    ): Result<Movie>

    /**
     *
     *
     * @param language Pass a ISO 639-1 value to display translated data for the fields that support it.
     * <b>minLength</b>: 2
     * <b>pattern</b>: ([a-z]{2})-([A-Z]{2})
     * <b>default</b>: en-US
     */
    @GET("3/movie/top_rated")
    suspend fun topRated(
        @Query(QUERY_LANGUAGE) language: String = DEFAULT_LANGUAGE,
        @Query(QUERY_PAGE) page: Int?,
        @Query(QUERY_REGION) region: String?,
    ): Result<Movie>

    /**
     *
     *
     * @param language Pass a ISO 639-1 value to display translated data for the fields that support it.
     * <b>minLength</b>: 2
     * <b>pattern</b>: ([a-z]{2})-([A-Z]{2})
     * <b>default</b>: en-US
     */
    @GET("3/movie/upcoming")
    suspend fun upcoming(
        @Query(QUERY_LANGUAGE) language: String = DEFAULT_LANGUAGE,
        @Query(QUERY_PAGE) page: Int?,
        @Query(QUERY_REGION) region: String?,
    ): Result<Movie>
}