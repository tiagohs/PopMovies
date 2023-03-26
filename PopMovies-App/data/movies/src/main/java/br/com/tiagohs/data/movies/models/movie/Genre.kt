package br.com.tiagohs.data.movies.models.movie

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Genre (
	@SerializedName("id") val id : Int? = null,
	@SerializedName("name") val name : String? = null
): Serializable