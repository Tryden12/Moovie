package com.tryden.moovi.network

import com.tryden.moovi.network.response.NowPlayingPageResponse
import com.tryden.moovi.network.service.MovieService
import retrofit2.Response

class ApiClient(
    private val movieService: MovieService
) {

    suspend fun getMoviesNowPlaying(): SimpleResponse<NowPlayingPageResponse> {
        return safeApiCall { movieService.getMoviesNowPlaying() }
    }

    // Paging
    suspend fun getNowPlayingMoviesPage(page: Int): SimpleResponse<NowPlayingPageResponse> {
        return safeApiCall { movieService.getNowPlayingMoviesPage(page)}
    }

    private inline fun <T> safeApiCall(apiCall: () -> Response<T>): SimpleResponse<T> {
        return try {
            SimpleResponse.success(apiCall.invoke())
        } catch (e: Exception) {
            SimpleResponse.failure(e)
        }
    }
}