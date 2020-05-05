package com.alpha.movieapp.platform.network

import com.alpha.movieapp.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val BASE_URL = "http://api.themoviedb.org/"

object NetworkApiClient {
    private var retrofit: Retrofit? = null
    val apiClient: Retrofit
        get() = retrofit ?: makeRetrofit()

    private fun makeRetrofit(): Retrofit {
        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(makeHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    private fun makeHttpClient() = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor())
            .followRedirects(true)
            .followSslRedirects(true)
            .retryOnConnectionFailure(true)
            .connectTimeout(25, TimeUnit.SECONDS)
            .readTimeout(25, TimeUnit.SECONDS)
            .writeTimeout(25, TimeUnit.SECONDS)
            .cache(null)
            .build()

    private fun loggingInterceptor() = HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
    }
}