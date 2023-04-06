package com.tryden.moovi.ui.nowplaying

import com.tryden.moovi.database.dao.FavoriteDao
import com.tryden.moovi.database.entity.FavoriteEntity
import com.tryden.moovi.network.NetworkLayer
import com.tryden.moovi.network.response.NowPlayingPageResponse
import kotlinx.coroutines.flow.Flow

class NowPlayingRepository(private val favoriteDao: FavoriteDao) {

    suspend fun getNowPlayingMoviesPage(pageIndex: Int): NowPlayingPageResponse? {
        val request = NetworkLayer.apiClient.getNowPlayingMoviesPage(pageIndex)

        if (request.failed || !request.isSuccessful) {
            return null
        }

        return request.body
    }

    // region FavoriteEntity
    fun getAllFavorites(): Flow<List<FavoriteEntity>> {
        return favoriteDao.getAllFavorites()
    }
    // endregion FavoriteEntity
}