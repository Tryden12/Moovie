package com.tryden.moovi.data

import androidx.paging.*
import com.tryden.moovi.data.database.dao.FavoriteDao
import com.tryden.moovi.data.database.entity.FavoriteEntity
import com.tryden.moovi.ui.home.NowPlayingPagingSource
import com.tryden.moovi.util.Constants.PAGE_SIZE
import com.tryden.moovi.util.Constants.PREFETCH_DISTANCE
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesRepository @Inject constructor(private val favoriteDao: FavoriteDao) {

    fun getNowPlayingMovies() =
        Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                //Value at which we want to start dropping items
                prefetchDistance = PREFETCH_DISTANCE,
                //Disabling placeholders for objects that haven't been loaded yet
                enablePlaceholders = false
            ),
            pagingSourceFactory = { NowPlayingPagingSource() }
            //Turn this pager into a stream of paging data to get live updates
        ).flow

    fun getAllFavoriteMovies(): Flow<List<FavoriteEntity>> {
        return favoriteDao.getAllFavorites()
    }

    suspend fun insertFavoriteMovie(favoriteEntity: FavoriteEntity) {
        favoriteDao.insert(favoriteEntity)
    }

    suspend fun deleteFavoriteMovie(favoriteEntity: FavoriteEntity) {
        favoriteDao.delete(favoriteEntity)
    }
}