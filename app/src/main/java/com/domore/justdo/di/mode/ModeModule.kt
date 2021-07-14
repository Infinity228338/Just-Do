package com.domore.justdo.di.mode

import com.domore.justdo.data.mode.datasource.local.LocalModeDataSource
import com.domore.justdo.data.mode.datasource.local.LocalModeDataSourceImpl
import com.domore.justdo.data.mode.repository.ModeRepository
import com.domore.justdo.data.mode.repository.ModeRepositoryImpl
import com.domore.justdo.di.storage.StorageModule
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module(includes = [StorageModule::class])
interface ModeModule {
    @Singleton
    @Binds
    fun bindModeRepository(modeRepository: ModeRepositoryImpl): ModeRepository

    @Singleton
    @Binds
    fun bindCacheModeDataSource(localModeDataSource: LocalModeDataSourceImpl): LocalModeDataSource

}