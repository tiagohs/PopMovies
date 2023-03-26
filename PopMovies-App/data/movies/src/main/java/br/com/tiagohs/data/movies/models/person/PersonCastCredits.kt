package br.com.tiagohs.data.movies.models.person

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PersonCastCredits (

	@SerializedName("poster_path") val poster_path : String? = null,
	@SerializedName("adult") val adult : Boolean? = null,
	@SerializedName("backdrop_path") val backdrop_path : String? = null,
	@SerializedName("vote_count") val vote_count : Int? = null,
	@SerializedName("video") val video : Boolean? = null,
	@SerializedName("id") val id : Int? = null,
	@SerializedName("popularity") val popularity : Double? = null,
	@SerializedName("genre_ids") val genre_ids : List<Int>? = null,
	@SerializedName("original_language") val originalLanguage : String? = null,
	@SerializedName("title") val title : String? = null,
	@SerializedName("original_title") val originalTitle : String? = null,
	@SerializedName("release_date") val releaseDate : String? = null,
	@SerializedName("character") val character : String? = null,
	@SerializedName("vote_average") val voteAverage : Double? = null,
	@SerializedName("overview") val overview : String? = null,
	@SerializedName("credit_id") val creditId : String? = null
): Serializable