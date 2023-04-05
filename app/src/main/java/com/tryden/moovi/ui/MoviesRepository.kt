package com.tryden.moovi.ui

import com.tryden.moovi.network.NetworkLayer
import com.tryden.moovi.network.response.MovieNowPlayingResponse

class MoviesRepository {

    suspend fun getMoviesNowPlaying(): MovieNowPlayingResponse? {
        val request = NetworkLayer.apiClient.getMoviesNowPlaying()

        if (request.failed || !request.isSuccessful) { return null }

        return request.body
    }
}