package com.tryden.moovi.ui

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.tryden.moovi.database.dao.FavoriteDao
import com.tryden.moovi.database.entity.FavoriteEntity
import com.tryden.moovi.domain.NowPlayingItem
import com.tryden.moovi.network.response.NowPlayingPageResponse
import com.tryden.moovi.ui.home.NowPlayingDataSourceFactory
import com.tryden.moovi.util.Constants.PAGE_SIZE
import com.tryden.moovi.util.Constants.PREFETCH_DISTANCE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    favoriteDao: FavoriteDao
): ViewModel(), LifecycleObserver {

    private val repository = MoviesRepository(favoriteDao)

//    private val _moviesNowPlaying = MutableLiveData<NowPlayingPageResponse?>()
//    val moviesNowPlayingLiveData: LiveData<NowPlayingPageResponse?> = _moviesNowPlaying

//    private val nowPlayingResponseFlow = MutableStateFlow<List<NowPlayingPageResponse.Result>>(emptyList())
//    val nowPlayingFlow: Flow<List<NowPlayingItem>> = combine(favoriteDao.getAllFavorites(), nowPlayingResponseFlow) { favorites, items ->
//        items.map { item ->
//            NowPlayingItem(
//                item = item,
//                isFavorite = favorites.find { it.id == item.id.toString() } != null
//            )
//        }
//    }

    val favoritesLiveData = MutableLiveData<List<FavoriteEntity>>()

    fun init() {

        viewModelScope.launch {
            repository.getAllFavorites().collect() { favorites ->
                favoritesLiveData.postValue(favorites)
            }
        }
    }

    private val pageListConfig: PagedList.Config = PagedList.Config.Builder()
        .setPageSize(PAGE_SIZE)
        .setPrefetchDistance(PREFETCH_DISTANCE) // needs to be 2-3x your page size
        .build()

    private val dataSourceFactory = NowPlayingDataSourceFactory(viewModelScope, repository)
    val nowPlayingPagedListLiveData: LiveData<PagedList<NowPlayingItem>> =
        LivePagedListBuilder(dataSourceFactory, pageListConfig).build()

    fun refreshNowPlaying() {
//        viewModelScope.launch {
//            val response = repository.getMoviesNowPlaying()
//
//            _moviesNowPlaying.postValue(response)
//        }

//        viewModelScope.launch {
//            val response = repository.getMoviesNowPlaying()
//
//            nowPlayingResponseFlow.emit(response!!.results)
//        }
    }



}