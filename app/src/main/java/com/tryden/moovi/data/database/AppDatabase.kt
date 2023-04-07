package com.tryden.moovi.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.tryden.moovi.data.database.dao.FavoriteDao
import com.tryden.moovi.data.database.entity.FavoriteEntity
import javax.inject.Inject
import javax.inject.Provider

@Database(
    entities = [FavoriteEntity::class],
    version = 1
)

abstract class AppDatabase : RoomDatabase() {


    class Callback @Inject constructor(
        private val database: Provider<AppDatabase>
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            // db operations
            database.get()
            val dao = database.get().favoriteDao()

        }
    }

    abstract fun favoriteDao(): FavoriteDao

}