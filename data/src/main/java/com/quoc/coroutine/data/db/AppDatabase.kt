package com.quoc.coroutine.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.quoc.coroutine.data.db.entity.ImageEntity

@Database(
    entities = [
        ImageEntity::class
    ], version = 1, exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun imageDao(): ImageDao
}