package br.com.tiagohs.data.movies.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class TranslationPersonData(
    @SerializedName("biography") val overview : String? = null
): Serializable