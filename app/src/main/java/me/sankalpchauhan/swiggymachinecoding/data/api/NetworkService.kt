package me.sankalpchauhan.swiggymachinecoding.data.api

import me.sankalpchauhan.swiggymachinecoding.data.model.MoviesResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface NetworkService {
    //TODO: Move apiKey handling to okhttp interceptor
    @GET("/")
    suspend fun getMovies(@Query("page") page: String, @Query("s") query: String, @Query("apiKey") apiKey: String): MoviesResponse
}