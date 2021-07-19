package com.domore.justdo.data.categoryicon.repository

import com.domore.justdo.data.vo.CategoryIcon
import io.reactivex.rxjava3.core.Single

interface CategoryIconRepository {
    fun getIcons(): Single<List<CategoryIcon>>
    fun getIconById(id: Long): Single<CategoryIcon>
}