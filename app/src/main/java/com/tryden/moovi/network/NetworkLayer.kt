package com.tryden.moovi.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.tryden.moovi.network.service.MovieService
import com.tryden.moovi.util.Constants.BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object NetworkLayer {

    // Converter factory
    val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()

    // Retrofit
    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(getHttpClient())
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    // Service
    val movieService: MovieService = retrofit.create(MovieService::class.java)

    // Api Client
    val apiClient = ApiClient(movieService)

    // OkHttpClient for adding api key
    private fun getHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                val originalHttpUrl = chain.request().url()
                val url = originalHttpUrl.newBuilder().addQueryParameter(
                    "api_key",
                    "7bfe007798875393b05c5aa1ba26323e")
                    .build()
                request.url(url)
                val response = chain.proceed(request.build())
                return@addInterceptor response
            }

        return builder.build()
    }
}