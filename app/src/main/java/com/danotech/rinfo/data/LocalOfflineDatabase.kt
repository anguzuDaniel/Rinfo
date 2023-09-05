package com.danotech.rinfo.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.danotech.rinfo.model.local.Category
import dagger.hilt.android.qualifiers.ApplicationContext

@Database(entities = [Category::class], version = 2, exportSchema = true)
abstract class LocalOfflineDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao

    companion object {
        @Volatile
        private var instance: LocalOfflineDatabase? = null

        fun getDatabase(@ApplicationContext context: Context): LocalOfflineDatabase {
            return instance ?: synchronized(this) {
                Room.databaseBuilder(context, LocalOfflineDatabase::class.java, "app_database")
                    .createFromAsset("database/offline.db")
                    .build()
//                    .also { instance = it }
            }
        }
    }
}