package com.alpha.movieapp.platform.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alpha.movieapp.platform.network.MoviesRepo

@Suppress("UNCHECKED_CAST")
class MoviesViewModelFactory(private val moviesRepo: MoviesRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MoviesViewModel(moviesRepo) as T
    }
}