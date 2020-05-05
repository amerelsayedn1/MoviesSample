package com.alpha.movieapp.platform.network

import com.alpha.movieapp.core.data.network.ErrorResponse
import com.alpha.movieapp.core.data.network.NetworkOutcome
import com.alpha.movieapp.core.data.network.ResponseErrorException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

suspend fun <T> Call<T>.getNetworkResponse(): NetworkOutcome<T> = suspendCoroutine {
    enqueue(object : Callback<T> {
        override fun onResponse(call: Call<T>?, response: Response<T>) {
            if (response.isSuccessful) {
                it.resume(NetworkOutcome(true, response.body(), ErrorResponse()))
            } else {
                var errorModel = ErrorResponse()
                response.errorBody()?.let { errorBody ->
                    errorModel = ErrorResponseConverter.parseError(errorBody)
                }
                when (response.code()) {
                    400, 404, 401, 409 -> it.resumeWithException(ResponseErrorException(errorModel))
                    else -> it.resumeWithException(ResponseErrorException(errorModel))
                }
            }
        }

        override fun onFailure(call: Call<T>, t: Throwable) {
            it.resumeWithException(NetworkExceptionHandler(t).getFailureException())
        }
    })
}
