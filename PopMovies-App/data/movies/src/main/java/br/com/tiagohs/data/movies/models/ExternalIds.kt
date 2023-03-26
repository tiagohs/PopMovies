package br.com.tiagohs.data.movies.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ExternalIds (

	@SerializedName("id") val id : String? = null,
	@SerializedName("instagram_id") val instagramId : String? = null,
	@SerializedName("twitter_id") val twitterId : String? = null,
	@SerializedName("imdb_id") val imdbId : String? = null,
	@SerializedName("facebook_id") val facebookId : String? = null
): Serializable