package com.tryden.moovi.data

import androidx.paging.*
import com.tryden.moovi.ui.nowplaying.NowPlayingPagingSource
import com.tryden.moovi.util.Constants.PAGE_SIZE
import com.tryden.moovi.util.Constants.PREFETCH_DISTANCE
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesRepository @Inject constructor() {

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
}