package com.domore.justdo.data.categorycolor.repository

import com.domore.justdo.data.vo.CategoryColor
import io.reactivex.rxjava3.core.Single

interface CategoryColorRepository {
    fun getColors(): Single<List<CategoryColor>>
    fun getColorById(id: Long): Single<CategoryColor>
}