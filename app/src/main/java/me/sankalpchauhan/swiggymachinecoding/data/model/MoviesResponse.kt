package me.sankalpchauhan.swiggymachinecoding.data.model


import android.os.Parcelable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Serializable
@Parcelize
data class MoviesResponse(
    @SerialName("Response")
    val response: String? = null,
    @SerialName("Search")
    val movies: List<Movies>? = null,
    @SerialName("totalResults")
    val totalResults: String? = null
): Parcelable {
    @Keep
    @Serializable
    @Parcelize
    data class Movies(
        @SerialName("imdbID")
        val imdbID: String,
        @SerialName("Poster")
        val poster: String? = null,
        @SerialName("Title")
        val title: String? = null,
        @SerialName("Type")
        val type: String? = null,
        @SerialName("Year")
        val year: String? = null
    ): Parcelable
}