package com.domore.justdo.data.categorycolor.datasource.local

import com.domore.justdo.data.vo.CategoryColor
import com.domore.justdo.storage.JustDoDatabase
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class LocalCategoryColorDataSourceImpl
@Inject constructor(
    justDoDb: JustDoDatabase
) : LocalCategoryColorDataSource {

    private val colorDao = justDoDb.categoryColorDao()

    override fun getColors(): Single<List<CategoryColor>> = colorDao.getColors()
    override fun getColorById(id: Long): Single<CategoryColor> = colorDao.getColorById(id)
}