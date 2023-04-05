package com.tryden.moovi.network

import com.tryden.moovi.network.response.MovieNowPlayingResponse
import com.tryden.moovi.network.service.MovieService
import retrofit2.Response

class ApiClient(
    private val movieService: MovieService
) {

    suspend fun getMoviesNowPlaying(): SimpleResponse<MovieNowPlayingResponse> {
        return safeApiCall { movieService.getMoviesNowPlaying() }
    }

    private inline fun <T> safeApiCall(apiCall: () -> Response<T>): SimpleResponse<T> {
        return try {
            SimpleResponse.success(apiCall.invoke())
        } catch (e: Exception) {
            SimpleResponse.failure(e)
        }
    }
}