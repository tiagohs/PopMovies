package br.com.tiagohs.data.movies.models.movie

import com.google.gson.annotations.SerializedName
import br.com.tiagohs.data.movies.models.Image
import java.io.Serializable


data class MovieImages (
    @SerializedName("backdrops") val backdrops : List<Image>? = null,
    @SerializedName("posters") val posters : List<Image>? = null
): Serializable