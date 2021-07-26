package com.domore.justdo.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import com.domore.justdo.data.vo.*
import com.domore.justdo.storage.dao.*

@Database(
    exportSchema = false,
    entities = [
        Category::class,
        CategoryColor::class,
        CategoryIcon::class,
        Mode::class,
        Task::class],
    version = 2
)
@androidx.room.TypeConverters(TypeConverters::class)
abstract class JustDoDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
    abstract fun categoryColorDao(): CategoryColorDao
    abstract fun categoryIconDao(): CategoryIconDao
    abstract fun modeDao(): ModeDao
    abstract fun taskDao(): TaskDao
}