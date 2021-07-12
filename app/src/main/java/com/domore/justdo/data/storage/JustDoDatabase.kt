package com.domore.justdo.data.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import com.domore.justdo.data.storage.dao.CategoryDao
import com.domore.justdo.data.vo.Category

@Database(exportSchema = false, entities = [Category::class], version = 1)
abstract class JustDoDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
}