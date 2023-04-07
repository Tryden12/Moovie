package com.tryden.moovi.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_entity")
data class FavoriteEntity(
    @PrimaryKey val id: String = "",
    val title: String = "",
    val description: String = "",
    val imageUrl: String = "",
    val releaseDate: String = ""
)