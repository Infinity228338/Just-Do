package com.domore.justdo.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import com.domore.justdo.data.vo.Category
import com.domore.justdo.data.vo.CategoryColor
import com.domore.justdo.data.vo.CategoryIcon
import com.domore.justdo.data.vo.Task
import com.domore.justdo.storage.dao.CategoryColorDao
import com.domore.justdo.storage.dao.CategoryDao
import com.domore.justdo.storage.dao.CategoryIconDao
import com.domore.justdo.storage.dao.TaskDao

@Database(
    exportSchema = false,
    entities = [
        Category::class,
        CategoryColor::class,
        CategoryIcon::class,
        Task::class],
    version = 2
)
@androidx.room.TypeConverters(TypeConverters::class)
abstract class JustDoDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
    abstract fun categoryColorDao(): CategoryColorDao
    abstract fun categoryIconDao(): CategoryIconDao
    abstract fun taskDao(): TaskDao
}