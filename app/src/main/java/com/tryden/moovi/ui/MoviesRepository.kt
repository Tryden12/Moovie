package com.tryden.moovi.ui

import com.tryden.moovi.network.NetworkLayer
import com.tryden.moovi.network.response.NowPlayingPageResponse

class MoviesRepository {

    suspend fun getMoviesNowPlaying(): NowPlayingPageResponse? {
        val request = NetworkLayer.apiClient.getMoviesNowPlaying()

        if (request.failed || !request.isSuccessful) { return null }

        return request.body
    }

    suspend fun getNowPlayingMoviesPage(pageIndex: Int): NowPlayingPageResponse? {
        val request = NetworkLayer.apiClient.getNowPlayingMoviesPage(pageIndex)

        if (request.failed || !request.isSuccessful) {
            return null
        }

        return request.body
    }
}