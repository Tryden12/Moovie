package com.tryden.moovi.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.tryden.moovi.data.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val repository: MoviesRepository
) : ViewModel() {

    val nowPlayingMovies = repository.getNowPlayingMovies().cachedIn(viewModelScope)

    // region Favorites
//    val nowPlayingFlow: Flow<List<NowPlayingItem>> = combine(favoriteDao.getAllFavorites(), flow) { favorites, items ->
//        items.map { item ->
//            NowPlayingItem(
//                item = item,
//                isFavorite = favorites.find { it.id == item.id.toString() } != null
//            )
//        }
//    }
    // endregion Favorites
}