package br.com.tiagohs.data.movies.models.movie

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class MovieVideos (
	@SerializedName("results") var videoList : List<Video>? = null
): Serializable