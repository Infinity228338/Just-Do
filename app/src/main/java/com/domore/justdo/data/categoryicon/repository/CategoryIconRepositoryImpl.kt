package com.domore.justdo.data.categoryicon.repository

import com.domore.justdo.data.categoryicon.datasource.LocalCategoryIconDataSourse
import com.domore.justdo.data.vo.CategoryIcon
import com.domore.justdo.schedulers.Schedulers
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class CategoryIconRepositoryImpl @Inject constructor(
    private val localDataSource: LocalCategoryIconDataSourse,
    private val schedulers: Schedulers
) : CategoryIconRepository {
    override fun getIcons(): Single<List<CategoryIcon>> =
        localDataSource
            .getIcons()
            .subscribeOn(schedulers.background())
            .observeOn(schedulers.main())

    override fun getIconById(id: Long): Single<CategoryIcon> =
        localDataSource
            .getIconById(id)
            .subscribeOn(schedulers.background())
}