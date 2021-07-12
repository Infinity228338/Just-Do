package com.domore.justdo.data.category.repository

import com.domore.justdo.data.category.datasource.local.LocalCategoryDataSource
import com.domore.justdo.data.vo.Category
import com.domore.justdo.di.Cache
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class CategoryRepositoryImpl
@Inject constructor(
    @Cache private val localCategoryDataSource: LocalCategoryDataSource
) :
    CategoryRepository {
    override fun getCategories(): Single<List<Category>> =
        localCategoryDataSource.getCategories()


    override fun getCategoryById(id: Long): Single<Category> =
        localCategoryDataSource.getCategoryById(id)
}