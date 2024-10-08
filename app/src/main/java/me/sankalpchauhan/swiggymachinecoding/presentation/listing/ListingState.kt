package me.sankalpchauhan.swiggymachinecoding.presentation.listing

import me.sankalpchauhan.swiggymachinecoding.data.model.MoviesResponse

data class ListingState(
    val isLoading: Boolean = true,
    val movies: List<MoviesResponse.Movies>? = null,
    val error: String? = null,
    val currentPage: Int=1,
    val userQuery: String = ""
)