package com.tryden.moovi.ui.nowplaying

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.tryden.moovi.domain.NowPlayingItem
import com.tryden.moovi.domain.NowPlayingMapper
import com.tryden.moovi.network.NetworkLayer
import com.tryden.moovi.util.Constants.STARTING_PAGE_INDEX
import retrofit2.HttpException
import java.io.IOException

class NowPlayingPagingSource() : PagingSource<Int, NowPlayingItem>(){

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NowPlayingItem> {
        val position = params.key ?: STARTING_PAGE_INDEX
        val previousKey = if (position == 1) null else position - 1

        return try {
            val response = NetworkLayer.apiClient.getNowPlayingMoviesPage(position)
            val movies = response.body.results
            LoadResult.Page(
                data = movies.map {
                    NowPlayingMapper.buildFrom(it)
                },
                prevKey = previousKey,
                nextKey = position + 1
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, NowPlayingItem>): Int? {
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