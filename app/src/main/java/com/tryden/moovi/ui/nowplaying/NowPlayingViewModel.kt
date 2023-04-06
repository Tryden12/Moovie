package com.tryden.moovi.ui.nowplaying

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.tryden.moovi.database.dao.FavoriteDao
import com.tryden.moovi.domain.NowPlayingItem
import com.tryden.moovi.util.Constants.PAGE_SIZE
import com.tryden.moovi.util.Constants.PREFETCH_DISTANCE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class NowPlayingViewModel @Inject constructor(favoriteDao: FavoriteDao): ViewModel() {

    private val repository = NowPlayingRepository(favoriteDao)

    // region Paging
    val flow = Pager(
        // Configure how data is loaded by passing additional properties
        // PagingConfig, such as prefetchDistance.
        PagingConfig(
            pageSize = PAGE_SIZE,
            prefetchDistance = PREFETCH_DISTANCE,
            enablePlaceholders = false
        )
    ) {
        NowPlayingPagingSource(repository)
    }.flow.cachedIn(viewModelScope).map {
        it.insertHeaderItem(
            TerminalSeparatorType.FULLY_COMPLETE,
            NowPlayingUiModel.Header("Now Playing")
        )
    }
    // endregion paging

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