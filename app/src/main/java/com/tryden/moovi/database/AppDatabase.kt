package com.tryden.moovi.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.tryden.moovi.database.dao.FavoriteDao
import com.tryden.moovi.database.entity.FavoriteEntity
import javax.inject.Inject
import javax.inject.Provider

@Database(
    entities = [FavoriteEntity::class],
    version = 1
)

abstract class AppDatabase : RoomDatabase() {

//    companion object {
//        private var appDatabase: AppDatabase? = null
//
//        fun getDatabase(context: Context): AppDatabase {
//            if (appDatabase != null) { return appDatabase !! }
//
//            appDatabase = Room.databaseBuilder(
//                    context.applicationContext,
//                    AppDatabase::class.java,
//                    name = "moovi-database"
//                )
//                .build()
//            return appDatabase!!
//        }
//    }


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