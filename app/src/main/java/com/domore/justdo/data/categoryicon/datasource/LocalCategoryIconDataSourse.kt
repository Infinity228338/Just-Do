package com.domore.justdo.data.categoryicon.datasource

import com.domore.justdo.data.vo.CategoryIcon
import io.reactivex.rxjava3.core.Single


interface LocalCategoryIconDataSourse {
    fun getIconById(id: Long): Single<CategoryIcon>
    fun getIcons(): Single<List<CategoryIcon>>
    fun getPredefinedIcons(): Single<List<CategoryIcon>>
}