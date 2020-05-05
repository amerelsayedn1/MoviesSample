package com.alpha.movieapp.platform.network

import com.alpha.movieapp.core.data.network.ErrorResponse
import com.alpha.movieapp.core.data.network.ResponseErrorException
import java.net.ConnectException
import java.net.NoRouteToHostException
import java.net.UnknownHostException

class NetworkExceptionHandler(private val throwable: Throwable) {
    fun getFailureException(): ResponseErrorException =
        when (throwable) {
            is UnknownHostException, is NoRouteToHostException -> ResponseErrorException(
                    ErrorResponse()
            )
            is ConnectException -> ResponseErrorException(ErrorResponse())
            else -> ResponseErrorException(ErrorResponse())
        }
}