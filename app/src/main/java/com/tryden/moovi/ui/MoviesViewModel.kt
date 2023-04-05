package com.tryden.moovi.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.tryden.moovi.network.response.NowPlayingPageResponse
import com.tryden.moovi.ui.home.NowPlayingDataSourceFactory
import com.tryden.moovi.util.Constants.PAGE_SIZE
import com.tryden.moovi.util.Constants.PREFETCH_DISTANCE
import kotlinx.coroutines.launch

class MoviesViewModel : ViewModel() {

    private val repository = MoviesRepository()

    private val _moviesNowPlaying = MutableLiveData<NowPlayingPageResponse?>()
    val moviesNowPlayingLiveData: LiveData<NowPlayingPageResponse?> = _moviesNowPlaying

    private val pageListConfig: PagedList.Config = PagedList.Config.Builder()
        .setPageSize(PAGE_SIZE)
        .setPrefetchDistance(PREFETCH_DISTANCE) // needs to be 2-3x your page size
        .build()

    private val dataSourceFactory = NowPlayingDataSourceFactory(viewModelScope, repository)
    val nowPlayingPagedListLiveData: LiveData<PagedList<NowPlayingPageResponse.Result>> =
        LivePagedListBuilder(dataSourceFactory, pageListConfig).build()

    fun refreshNowPlaying() {
        viewModelScope.launch {
            val response = repository.getMoviesNowPlaying()

            _moviesNowPlaying.postValue(response)
        }
    }

}