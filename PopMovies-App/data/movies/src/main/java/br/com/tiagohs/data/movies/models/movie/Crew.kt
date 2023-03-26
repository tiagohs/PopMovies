package br.com.tiagohs.data.movies.models.movie

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class Crew (

	@SerializedName("credit_id") val creditId : String? = null,
	@SerializedName("department") val department : String? = null,
	@SerializedName("gender") val gender : Int? = null,
	@SerializedName("id") val id : Int? = null,
	@SerializedName("job") val job : String? = null,
	@SerializedName("name") val name : String? = null,
	@SerializedName("profile_path") val profilePath : String? = null,
	@SerializedName("known_for_department") val knownForDepartment : String? = null,
	@SerializedName("original_name") val originalName : String? = null,
	@SerializedName("popularity") val popularity : Int? = null,
): Serializable