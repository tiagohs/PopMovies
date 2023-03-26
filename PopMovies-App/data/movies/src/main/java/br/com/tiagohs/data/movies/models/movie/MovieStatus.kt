package br.com.tiagohs.data.movies.models.movie

import com.google.gson.annotations.SerializedName
import java.io.Serializable

enum class MovieStatus: Serializable {
    @SerializedName("Rumored")
    RUMORED,
    @SerializedName("Planned")
    PLANNED,
    @SerializedName("In Production")
    IN_PRODUCTION,
    @SerializedName("Post Production")
    POST_PRODUCTION,
    @SerializedName("Released")
    RELEASED,
    @SerializedName("Canceled")
    CANCELED;
}