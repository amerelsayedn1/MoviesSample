package com.alpha.movieapp.platform.network

import com.alpha.movieapp.core.data.network.ErrorResponse
import okhttp3.ResponseBody
import java.io.IOException

object ErrorResponseConverter {
    fun parseError(errorBody: ResponseBody): ErrorResponse = try {
        NetworkApiClient.apiClient.responseBodyConverter<ErrorResponse>(ErrorResponse::class.java, arrayOfNulls(0)).convert(errorBody)
                ?: ErrorResponse()

    } catch (e: IOException) {
        ErrorResponse()
    }
}
