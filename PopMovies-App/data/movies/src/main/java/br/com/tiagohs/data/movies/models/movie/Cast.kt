package br.com.tiagohs.data.movies.models.movie


import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class Cast (
	@SerializedName("adult") val adult : Boolean? = null,
	@SerializedName("cast_id") val castId : Int? = null,
	@SerializedName("character") val character : String? = null,
	@SerializedName("credit_id") val creditId : String? = null,
	@SerializedName("gender") val gender : Int? = null,
	@SerializedName("id") val id : Int? = null,
	@SerializedName("name") val name : String? = null,
	@SerializedName("order") val order : Int? = null,
	@SerializedName("profile_path") val profilePath : String? = null,
	@SerializedName("known_for_department") val knownForDepartment : String? = null,
	@SerializedName("original_name") val originalName : String? = null,
	@SerializedName("popularity") val popularity : Int? = null,

): Serializable