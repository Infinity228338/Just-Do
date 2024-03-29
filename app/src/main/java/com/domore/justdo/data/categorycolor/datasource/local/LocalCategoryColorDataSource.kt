package com.domore.justdo.data.categorycolor.datasource.local

import com.domore.justdo.data.vo.CategoryColor
import io.reactivex.rxjava3.core.Single

interface LocalCategoryColorDataSource {
    fun getColors(): Single<List<CategoryColor>>
    fun getColorById(id: Long): Single<CategoryColor>
    fun retain(colors: List<CategoryColor>): Single<List<CategoryColor>>
    fun retain(color: CategoryColor): Single<CategoryColor>
}