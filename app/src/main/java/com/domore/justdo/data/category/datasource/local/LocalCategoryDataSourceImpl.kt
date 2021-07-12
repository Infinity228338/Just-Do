package com.domore.justdo.data.category.datasource.local

import com.domore.justdo.data.storage.JustDoDatabase
import com.domore.justdo.data.vo.Category
import com.domore.justdo.di.Persisted
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class LocalCategoryDataSourceImpl
@Inject constructor(
    justDoDb: JustDoDatabase
) : LocalCategoryDataSource {
    private val categoryDao = justDoDb.categoryDao()
    override fun getCategories(): Single<List<Category>> =
        categoryDao.getCategories()


    override fun getCategoryById(id: Long): Single<Category> =
        categoryDao.getCategoriesById(id)

}