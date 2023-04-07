package com.tryden.moovi.domain

import com.tryden.moovi.network.response.NowPlayingPageResponse
import com.tryden.moovi.util.Constants.IMAGE_BASE_URL
import com.tryden.moovi.util.formatReleaseDate

object NowPlayingMapper {

    fun buildFrom(item: NowPlayingPageResponse.Result): NowPlayingItem {
        return NowPlayingItem(
            id = item.id.toString(),
            title = item.title,
            releaseDate = formatReleaseDate(item.release_date),
            imageUrl = IMAGE_BASE_URL + item.backdrop_path,
            isFavorite = false
        )
    }
}