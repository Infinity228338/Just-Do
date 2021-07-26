package com.domore.justdo.di.categorydesc

import com.domore.justdo.data.categoryicon.datasource.LocalCategoryIconDataSourceImpl
import com.domore.justdo.data.categoryicon.datasource.LocalCategoryIconDataSourse
import com.domore.justdo.data.categoryicon.repository.CategoryIconRepository
import com.domore.justdo.data.categoryicon.repository.CategoryIconRepositoryImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface CategoryIconModule {
    @Singleton
    @Binds
    fun bindColorRepository(categoryIconRepository: CategoryIconRepositoryImpl): CategoryIconRepository

    @Singleton
    @Binds
    fun bindCacheCategoryDataSource(localCategoryIconDataSource: LocalCategoryIconDataSourceImpl): LocalCategoryIconDataSourse
}