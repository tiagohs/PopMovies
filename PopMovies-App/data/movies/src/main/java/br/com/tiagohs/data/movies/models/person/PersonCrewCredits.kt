package br.com.tiagohs.data.movies.models.person

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PersonCrewCredits (

	@SerializedName("department") val department : String? = null,
	@SerializedName("job") val job : String? = null,
	@SerializedName("credit_id") val creditId : String? = null,

	@SerializedName("id") val id : Int? = null,
	@SerializedName("original_language") val originalLanguage : String? = null,
	@SerializedName("original_title") val originalTitle : String,
	@SerializedName("overview") val overview : String? = null,
	@SerializedName("genre_ids") val genreIds : List<Int>? = null,
	@SerializedName("video") val video : Boolean? = null,
	@SerializedName("release_date") val releaseDate : String? = null,
	@SerializedName("popularity") val popularity : Double? = null,
	@SerializedName("vote_average") val voteAverage : Double? = null,
	@SerializedName("vote_count") val voteCount : Int? = null,
	@SerializedName("title") val title : String? = null,
	@SerializedName("adult") val adult : Boolean? = null,
	@SerializedName("backdrop_path") val backdropPath : String? = null,
	@SerializedName("poster_path") val posterPath : String? = null
): Serializable