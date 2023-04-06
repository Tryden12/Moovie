package com.tryden.moovi.ui

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tryden.moovi.database.dao.FavoriteDao
import com.tryden.moovi.database.entity.FavoriteEntity
import dagger.hilt.android.lifecycle.HiltViewModel
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