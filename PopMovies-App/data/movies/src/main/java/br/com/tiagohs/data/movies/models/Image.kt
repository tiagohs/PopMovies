package br.com.tiagohs.data.movies.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class Image (
	@SerializedName("aspect_ratio") val aspectRatio : Double? = null,
	@SerializedName("file_path") val filePath : String? = null,
	@SerializedName("height") val height : Int? = null,
	@SerializedName("iso_639_1") val iso639_1 : String? = null,
	@SerializedName("vote_average") val voteAverage : Double? = null,
	@SerializedName("vote_count") val voteCount : Int? = null,
	@SerializedName("width") val width : Int? = null
): Serializable