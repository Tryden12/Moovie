package com.tryden.moovi.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.tryden.moovi.data.MoviesRepository
import com.tryden.moovi.data.database.entity.FavoriteEntity
import com.tryden.moovi.domain.NowPlayingItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val repository: MoviesRepository
) : ViewModel() {

    val nowPlayingMovies = repository.getNowPlayingMovies().cachedIn(viewModelScope)

    // region Favorites

    val updatedItemsHashMutableMap = mutableMapOf<FavoriteEntity, NowPlayingItem>()
    val favoriteMovies: Flow<List<FavoriteEntity>> = repository.getAllFavoriteMovies()

    fun addFavoriteMovie(favoriteEntity: FavoriteEntity) = viewModelScope.launch {
        repository.insertFavoriteMovie(favoriteEntity)
    }

    fun deleteFavoriteMovie(favoriteEntity: FavoriteEntity) = viewModelScope.launch {
        repository.deleteFavoriteMovie(favoriteEntity)
    }

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