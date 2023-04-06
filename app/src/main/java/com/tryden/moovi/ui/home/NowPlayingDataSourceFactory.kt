package com.tryden.moovi.ui.home

import androidx.paging.DataSource
import com.tryden.moovi.domain.NowPlayingItem
import com.tryden.moovi.network.response.NowPlayingPageResponse
import com.tryden.moovi.ui.MoviesRepository
import kotlinx.coroutines.CoroutineScope

class NowPlayingDataSourceFactory(
    private val coroutineScope: CoroutineScope,
    private val repository: MoviesRepository
): DataSource.Factory<Int, NowPlayingItem>() {

    override fun create(): DataSource<Int, NowPlayingItem> {
        return NowPlayingDataSource(coroutineScope, repository)
    }

}