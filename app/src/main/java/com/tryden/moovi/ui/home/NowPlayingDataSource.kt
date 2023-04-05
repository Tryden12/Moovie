package com.tryden.moovi.ui.home

import androidx.paging.PageKeyedDataSource
import com.tryden.moovi.network.response.NowPlayingPageResponse
import com.tryden.moovi.ui.MoviesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class NowPlayingDataSource(
    private val coroutineScope: CoroutineScope,
    private val repository: MoviesRepository
) : PageKeyedDataSource<Int, NowPlayingPageResponse.Result>() {

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, NowPlayingPageResponse.Result>,
    ) {
        coroutineScope.launch {
            val page = repository.getNowPlayingMoviesPage(1) // safe to hard code 1

            if (page == null) {
                callback.onResult(emptyList(), null, null)
                return@launch
            }

            callback.onResult(page.results, null, 2)
        }
    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, NowPlayingPageResponse.Result>,
    ) {
        coroutineScope.launch {
            val page = repository.getNowPlayingMoviesPage(params.key)

            if (page == null) {
                callback.onResult(emptyList(), null)
                return@launch
            }

            callback.onResult(page.results, params.key + 1) // todo clean up adjacentPageKey
        }
    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, NowPlayingPageResponse.Result>,
    ) {
        // Nothing to do
    }
}