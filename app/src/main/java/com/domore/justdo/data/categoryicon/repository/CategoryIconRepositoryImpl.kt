package com.domore.justdo.data.categoryicon.repository

import com.domore.justdo.data.categoryicon.datasource.LocalCategoryIconDataSourse
import com.domore.justdo.data.vo.CategoryIcon
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class CategoryIconRepositoryImpl @Inject constructor(
    private val localDataSource: LocalCategoryIconDataSourse
) : CategoryIconRepository {
    override fun getIcons(): Single<List<CategoryIcon>> = localDataSource.getIcons()

    override fun getIconById(id: Long): Single<CategoryIcon> = localDataSource.getIconById(id)
}