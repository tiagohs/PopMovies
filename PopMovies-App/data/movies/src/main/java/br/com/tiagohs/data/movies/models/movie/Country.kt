package br.com.tiagohs.data.movies.models.movie


import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class Country (

	@SerializedName("certification") val certification : String? = null,
	@SerializedName("iso_3166_1") val iso3166_1 : String? = null,
	@SerializedName("type") val type : Int? = null,
	@SerializedName("note") val note : String? = null,
	@SerializedName("release_date") val releaseDate : String? = null
): Serializable