package com.tryden.moovi.ui.nowplaying

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.tryden.moovi.domain.NowPlayingItem
import com.tryden.moovi.domain.NowPlayingMapper
import com.tryden.moovi.network.NetworkLayer

class NowPlayingPagingSource(
    private val repository: NowPlayingRepository
) : PagingSource<Int, NowPlayingUiModel>(){

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NowPlayingUiModel> {
        val pageNumber = params.key ?: 1
        val previousKey = if (pageNumber == 1) null else pageNumber - 1

        val pageRequest = NetworkLayer.apiClient.getNowPlayingMoviesPage(pageNumber)
        pageRequest.exception?.let { return LoadResult.Error(it) }

        return LoadResult.Page(
            data = pageRequest.body.results.map {
                NowPlayingUiModel.Item(NowPlayingMapper.buildFrom(it))
            },
            prevKey = previousKey,
            nextKey = pageNumber + 1
        )
    }

    override fun getRefreshKey(state: PagingState<Int, NowPlayingUiModel>): Int? {
        // Try to find the page key of the closest page to anchorPosition, from
        // either the prevKey or the nextKey, but you need to handle nullability
        // here:
        //  * prevKey == null -> anchorPage is the first page.
        //  * nextKey == null -> anchorPage is the last page.
        //  * both prevKey and nextKey null -> anchorPage is the initial page, so
        //    just return null.
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

}