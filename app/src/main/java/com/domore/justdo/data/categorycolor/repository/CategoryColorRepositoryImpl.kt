package com.domore.justdo.data.categorycolor.repository

import com.domore.justdo.data.categorycolor.datasource.local.LocalCategoryColorDataSource
import com.domore.justdo.data.vo.CategoryColor
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class CategoryColorRepositoryImpl
@Inject constructor(
    private val localColorDataSource: LocalCategoryColorDataSource
) : CategoryColorRepository {
    override fun getColors(): Single<List<CategoryColor>> = localColorDataSource.getColors()

    override fun getColorById(id: Long): Single<CategoryColor> =
        localColorDataSource.getColorById(id)
}