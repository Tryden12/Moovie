package com.tryden.moovi.database.dao

import androidx.room.*
import com.tryden.moovi.database.entity.FavoriteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {

    @Query("SELECT * FROM favorite_entity")
    fun getAllFavorites(): Flow<List<FavoriteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favoriteEntity: FavoriteEntity)

    @Delete
    suspend fun delete(favoriteEntity: FavoriteEntity)

    @Update
    suspend fun update(favoriteEntity: FavoriteEntity)
}