package com.alpha.movieapp.platform.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alpha.movieapp.platform.network.MoviesRepo
import com.alpha.movieapp.core.data.MoviesResponse
import com.alpha.movieapp.core.data.network.ErrorResponse
import kotlinx.coroutines.launch

class MoviesViewModel(private val movieRepo: MoviesRepo) : ViewModel() {

    var movieSuccessResponse = MutableLiveData<MoviesResponse>()
    var movieErrorResponse = MutableLiveData<ErrorResponse>()

    fun getMovies(query: String, page: Int = 1) = viewModelScope.launch {
        val response = movieRepo.getMovies(query, page)
        if (response.isRequestSuccess) {
            movieSuccessResponse.value = response.responseBody
        } else {
            movieErrorResponse.value = response.errorResponse
        }
    }


}