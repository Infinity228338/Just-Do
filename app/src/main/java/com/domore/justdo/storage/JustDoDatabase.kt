package com.domore.justdo.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import com.domore.justdo.data.vo.Category
import com.domore.justdo.data.vo.Mode
import com.domore.justdo.data.vo.Task
import com.domore.justdo.storage.dao.CategoryDao
import com.domore.justdo.storage.dao.ModeDao
import com.domore.justdo.storage.dao.TaskDao

@Database(
    exportSchema = false,
    entities = [Category::class, Mode::class, Task::class], version = 2
)
abstract class JustDoDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
    abstract fun modeDao(): ModeDao
    abstract fun taskDao(): TaskDao
}