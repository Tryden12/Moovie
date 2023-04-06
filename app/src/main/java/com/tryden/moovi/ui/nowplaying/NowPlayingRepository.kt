package com.tryden.moovi.ui.nowplaying

import com.tryden.moovi.network.NetworkLayer
import com.tryden.moovi.network.response.NowPlayingPageResponse

class NowPlayingRepository {

    suspend fun getNowPlayingMoviesPage(pageIndex: Int): NowPlayingPageResponse? {
        val request = NetworkLayer.apiClient.getNowPlayingMoviesPage(pageIndex)

        if (request.failed || !request.isSuccessful) {
            return null
        }

        return request.body
    }
}