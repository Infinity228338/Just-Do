package com.domore.justdo.di.categorydesc

import com.domore.justdo.data.categorycolor.datasource.local.LocalCategoryColorDataSource
import com.domore.justdo.data.categorycolor.datasource.local.LocalCategoryColorDataSourceImpl
import com.domore.justdo.data.categorycolor.repository.CategoryColorRepository
import com.domore.justdo.data.categorycolor.repository.CategoryColorRepositoryImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module(includes = [CategoryColorUiModule::class])
interface CategoryColorModule {
    @Singleton
    @Binds
    fun bindColorRepository(categoryColorRepositoryImpl: CategoryColorRepositoryImpl): CategoryColorRepository

    @Singleton
    @Binds
    fun bindCacheCategoryDataSource(localCategoryColorDataSourceImpl: LocalCategoryColorDataSourceImpl): LocalCategoryColorDataSource
}