package com.tryden.moovi.ui.nowplaying

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.tryden.moovi.domain.NowPlayingItem
import com.tryden.moovi.util.Constants.PAGE_SIZE
import com.tryden.moovi.util.Constants.PREFETCH_DISTANCE
import kotlinx.coroutines.flow.map

class NowPlayingViewModel: ViewModel() {

    private val repository = NowPlayingRepository()
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
        it.insertSeparators { model: NowPlayingUiModel?, model2: NowPlayingUiModel? ->
            if (model == null) {
                return@insertSeparators NowPlayingUiModel.Header("Now Playing")
            } else {
                return@insertSeparators null
            }
        }
    }

}