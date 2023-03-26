package br.com.tiagohs.data.movies.models.movie

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class SpokenLanguages (
	@SerializedName("iso_639_1") val iso_639_1 : String? = null,
	@SerializedName("name") val name : String? = null
): Serializable