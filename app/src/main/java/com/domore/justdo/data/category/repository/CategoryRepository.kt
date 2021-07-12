package com.domore.justdo.data.category.repository

import com.domore.justdo.data.vo.Category
import io.reactivex.rxjava3.core.Single

interface CategoryRepository {
    fun getCategories(): Single<List<Category>>
    fun getCategoryById(id: Long): Single<Category>
}