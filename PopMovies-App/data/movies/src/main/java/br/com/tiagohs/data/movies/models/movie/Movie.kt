package br.com.tiagohs.data.movies.models.movie

import com.google.gson.annotations.SerializedName
import br.com.tiagohs.data.movies.models.ExternalIds
import br.com.tiagohs.data.movies.models.Image
import br.com.tiagohs.data.movies.models.TranslationMovieData
import br.com.tiagohs.data.movies.models.TranslationsResult
import java.io.Serializable


data class Movie(

    @SerializedName("id") val id: Int? = null,
    @SerializedName("adult") val adult: Boolean? = null,
    @SerializedName("backdrop_path") val backdropPath: String? = null,
    @SerializedName("budget") val budget: Long? = null,
    @SerializedName("belongs_to_collection") val belongsToCollection: Collection? = null,
    @SerializedName("genres") val genres: List<Genre>? = null,
    @SerializedName("homepage") val homepage: String? = null,
    @SerializedName("imdb_id") val imdbId: String? = null,
    @SerializedName("original_language") val originalLanguage: String? = null,
    @SerializedName("original_title") val originalTitle: String? = null,
    @SerializedName("overview") val overview: String? = null,
    @SerializedName("popularity") val popularity: Double? = null,
    @SerializedName("poster_path") val posterPath: String? = null,
    @SerializedName("production_companies") val productionCompanies: List<ProductionCompany>? = null,
    @SerializedName("production_countries") val productionCountries: List<ProductionCountry>? = null,
    @SerializedName("release_date") val releaseDate: String? = null,
    @SerializedName("revenue") val revenue: Long? = null,
    @SerializedName("runtime") val runtime: Int? = null,
    @SerializedName("spoken_languages") val spokenLanguages: List<SpokenLanguages>? = null,
    @SerializedName("status") val status: MovieStatus? = null,
    @SerializedName("tagline") val tagline: String? = null,
    @SerializedName("title") val title: String? = null,
    @SerializedName("video") val video: Boolean? = null,
    @SerializedName("vote_average") val voteAverage: Double? = null,
    @SerializedName("vote_count") val voteCount: Int? = null,
    @SerializedName("videos") val videos: MovieVideos? = null,
    @SerializedName("images") var images: MovieImages? = null,
    @SerializedName("keywords") val keywords: KeywordList? = null,
    @SerializedName("releases") val releases: Releases? = null,
    @SerializedName("similar_movies") val similarMovies: SimilarMovies? = null,
    @SerializedName("credits") val credits: Credits? = null,
    @SerializedName("genre_ids") val genreIds: List<Int>? = null,
    @SerializedName("translations") val translations: TranslationsResult<TranslationMovieData>? = null,
    @SerializedName("external_ids") val externalIds: ExternalIds? = null
) : Serializable {

    val trailerUrlKey: String?
        get() = videos?.videoList?.find { it.type == "Trailer" }?.key
            ?: videos?.videoList?.firstOrNull()?.key

    var movieCollection: Collection? = null

    var allImages: List<Image>? = null

    fun setupImages() {
        val allImages = ArrayList<Image>(images?.backdrops ?: emptyList())
        allImages.addAll(images?.posters ?: emptyList())

        this.allImages = allImages
    }

    fun getMovieTitleFromAppLanguage(appLanguage: String): String {
        val translations = translations?.translations ?: emptyList()
        val originalLanguage = originalLanguage

        val portugueseTitle =
            translations.find { it.iso_639_1 == "pt" && it.iso_3166_1 == "BR" }?.data?.title
        if (!portugueseTitle.isNullOrBlank() && appLanguage == "Português Brasil") {
            return portugueseTitle
        }

        val englishTitle =
            translations.find { it.iso_639_1 == "en" && it.iso_3166_1 == "US" }?.data?.title
        if (!englishTitle.isNullOrBlank() && appLanguage == "Inglês") {
            return englishTitle
        }

        val originalTitle = translations.find { it.iso_639_1 == originalLanguage }?.data?.title
        if (!originalTitle.isNullOrBlank()) {
            return originalTitle
        }

        return title ?: originalTitle ?: ""
    }

    fun getMovieSummaryFromAppLanguage(defaultSummary: String, appLanguage: String): String {
        val translations = translations?.translations ?: emptyList()
        val originalLanguage = originalLanguage

        val portugueseOverview =
            translations.find { it.iso_639_1 == "pt" && it.iso_3166_1 == "BR" }?.data?.overview
        if (!portugueseOverview.isNullOrBlank() && appLanguage == "Português Brasil") {
            return portugueseOverview
        }

        val englishOverview =
            translations.find { it.iso_639_1 == "en" && it.iso_3166_1 == "US" }?.data?.overview
        if (!englishOverview.isNullOrBlank() && appLanguage == "Inglês") {
            return englishOverview
        }

        val originalOverview =
            translations.find { it.iso_639_1 == originalLanguage }?.data?.overview
        if (!originalOverview.isNullOrBlank()) {
            return originalOverview
        }

        return originalLanguage ?: defaultSummary
    }
}