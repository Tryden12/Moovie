package com.tryden.moovi.domain

data class NowPlayingItem(
    val id: String = "",
    val title: String = "",
    val releaseDate: String = "",
    val imageUrl: String = "",
    val isFavorite: Boolean
)