package com.alpha.movieapp.platform.network

import com.alpha.movieapp.core.data.MoviesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesEndPoints {

    @GET("3/search/movie")
    fun movies(
            @Query("api_key") apiKey: String,
            @Query("query") query: String,
            @Query("page") page: Int
    ): Call<MoviesResponse>

    @GET("3/movie/top_rated")
    fun popularMovies(
            @Query("api_key") apiKey: String,
            @Query("language") language: String,
            @Query("page") page: Int
    ): Call<MoviesResponse>

}