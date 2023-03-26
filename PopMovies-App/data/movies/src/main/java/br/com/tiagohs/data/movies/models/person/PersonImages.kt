package br.com.tiagohs.data.movies.models.person

import com.google.gson.annotations.SerializedName
import br.com.tiagohs.data.movies.models.Image
import java.io.Serializable

data class PersonImages (
	@SerializedName("profiles") val profiles : List<Image>? = null
): Serializable