package br.com.tiagohs.data.movies.models.movie

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ProductionCountry (
	@SerializedName("iso_3166_1") val iso3166_1 : String? = null,
	@SerializedName("name") val name : String? = null
): Serializable