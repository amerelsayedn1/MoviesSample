package com.alpha.movieapp.platform.network

import com.alpha.movieapp.core.data.MoviesResponse
import com.alpha.movieapp.core.data.network.NetworkOutcome
import com.alpha.movieapp.core.data.network.ResponseErrorException

/**
 * Amer Elsayed
 * dev.amer.elsayed@gmail.com
 * 05/05/2020
 */
class MoviesRepo {

    private val moviesEndpoint: MoviesEndPoints by lazy {
        NetworkApiClient.apiClient.create(MoviesEndPoints::class.java)
    }

    suspend fun getMovies(query: String, page: Int): NetworkOutcome<MoviesResponse> = try {
        moviesEndpoint.movies(
                apiKey = "2696829a81b1b5827d515ff121700838",
                query = query,
                page = page
        ).getNetworkResponse()
    } catch (exception: ResponseErrorException) {
        NetworkOutcome(isRequestSuccess = false, responseBody = null, errorResponse = exception.errorModel)
    }

    suspend fun getPopularMovies(page: Int): NetworkOutcome<MoviesResponse> = try {
        moviesEndpoint.popularMovies(
                apiKey = "2696829a81b1b5827d515ff121700838",
                language = "en-US",
                page = page
        ).getNetworkResponse()
    } catch (exception: ResponseErrorException) {
        NetworkOutcome(isRequestSuccess = false, responseBody = null, errorResponse = exception.errorModel)
    }

}