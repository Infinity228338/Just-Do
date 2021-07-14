package com.domore.justdo.di.category

import com.domore.justdo.data.category.datasource.local.LocalCategoryDataSource
import com.domore.justdo.data.category.datasource.local.LocalCategoryDataSourceImpl
import com.domore.justdo.data.category.repository.CategoryRepository
import com.domore.justdo.data.category.repository.CategoryRepositoryImpl
import com.domore.justdo.di.storage.StorageModule
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module(includes = [CategoryUiModule::class, StorageModule::class])
interface CategoryModule {
    @Singleton
    @Binds
    fun bindCategoryRepository(categoryRepository: CategoryRepositoryImpl): CategoryRepository

    @Singleton
    @Binds
    fun bindCacheCategoryDataSource(localCategoryDataSource: LocalCategoryDataSourceImpl): LocalCategoryDataSource
}