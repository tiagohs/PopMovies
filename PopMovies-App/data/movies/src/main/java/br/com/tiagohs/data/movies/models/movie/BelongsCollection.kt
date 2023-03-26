package br.com.tiagohs.data.movies.models.movie

import com.google.gson.annotations.SerializedName

class BelongsCollection(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("poster_path") val poster_path: String? = null,
    @SerializedName("backdrop_path") val backdrop_path: String? = null
)