package com.domore.justdo.data.category.datasource.local

import com.domore.justdo.data.vo.Category
import io.reactivex.rxjava3.core.Single

interface LocalCategoryDataSource {
    fun getCategories(): Single<List<Category>>
    fun getCategoryById(id: Long): Single<Category>
    fun addCategory(name: String, colorRes: Int, drawRes: Int): Single<Category>
}