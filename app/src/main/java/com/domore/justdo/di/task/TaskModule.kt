package com.domore.justdo.di.task

import com.domore.justdo.data.task.datasource.local.LocalTaskDataSource
import com.domore.justdo.data.task.datasource.local.LocalTaskDataSourceImpl
import com.domore.justdo.data.task.repository.TaskRepository
import com.domore.justdo.data.task.repository.TaskRepositoryImpl
import com.domore.justdo.di.mode.ModeModule
import com.domore.justdo.di.storage.StorageModule
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module(includes = [TaskUiModule::class, ModeModule::class, StorageModule::class])
interface TaskModule {
    @Singleton
    @Binds
    fun bindTaskRepository(taskRepository: TaskRepositoryImpl): TaskRepository

    @Singleton
    @Binds
    fun bindCacheModeDataSource(localTaskDataSource: LocalTaskDataSourceImpl): LocalTaskDataSource

}