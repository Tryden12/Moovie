package com.tryden.moovi.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tryden.moovi.database.dao.FavoriteDao
import com.tryden.moovi.database.entity.FavoriteEntity

@Database(
    entities = [FavoriteEntity::class],
    version = 1
)

abstract class AppDatabase : RoomDatabase() {

    companion object {
        private var appDatabase: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            if (appDatabase != null) { return appDatabase !! }

            appDatabase = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    name = "moovi-database"
                )
                .build()
            return appDatabase!!
        }
    }

    abstract fun favoriteDao(): FavoriteDao

}