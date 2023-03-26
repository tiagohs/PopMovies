package br.com.tiagohs.data.movies.models.person

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PersonExtraInfo (

	@SerializedName("id") val id : Int,
	@SerializedName("name") val name : String? = null,
	@SerializedName("custom_name") val customName : String? = null,
	@SerializedName("highlight_image") val highlight_image : String? = null,
	@SerializedName("quote") val quote : String? = null,
	@SerializedName("profile") val profile : List<PersonProfile>? = null,
	@SerializedName("awards") val awards : String? = null,
	@SerializedName("videos") val videos : List<PersonVideo>? = null
): Serializable