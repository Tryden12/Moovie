package com.tryden.moovi.ui.nowplaying

import com.tryden.moovi.domain.NowPlayingItem

sealed class NowPlayingUiModel {

    class Item(val item: NowPlayingItem): NowPlayingUiModel()
    class Header(val text: String): NowPlayingUiModel()
    // class Footer
}