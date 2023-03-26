package br.com.tiagohs.data.movies.models.movie

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class Credits (
    @SerializedName("id") val id : Int? = null,
    @SerializedName("cast") val cast : List<Cast>? = null,
    @SerializedName("crew") val crew : List<Crew>? = null
): Serializable