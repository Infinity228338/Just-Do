package com.domore.justdo.data.category.datasource.local

import com.domore.justdo.storage.JustDoDatabase
import com.domore.justdo.data.vo.Category
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