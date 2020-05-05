package com.alpha.movieapp.core.data.network

import com.alpha.movieapp.core.data.network.ErrorResponse


class ResponseErrorException(val errorModel: ErrorResponse) : Exception()
