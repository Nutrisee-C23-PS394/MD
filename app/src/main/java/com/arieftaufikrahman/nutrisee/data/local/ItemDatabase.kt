package com.arieftaufikrahman.nutrisee.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [ItemEntity:: class],
    version = 2,
    exportSchema = false
)
abstract class ItemDatabase: RoomDatabase() {
    abstract fun itemDao(): ItemDao
}