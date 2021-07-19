package com.domore.justdo.data.categoryicon.datasource

import com.domore.justdo.R
import com.domore.justdo.data.vo.CategoryIcon
import com.domore.justdo.storage.JustDoDatabase
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class LocalCategoryIconDataSourceImpl @Inject constructor(justDoDb: JustDoDatabase) :
    LocalCategoryIconDataSourse {

    private val iconDao = justDoDb.categoryIconDao()
    override fun getIcons(): Single<List<CategoryIcon>> =
        Single.just(
            mutableListOf(
                CategoryIcon(0, R.drawable.ic_glasses),
                CategoryIcon(1, R.drawable.ic_movie),
                CategoryIcon(2, R.drawable.ic_birthday),
                CategoryIcon(3, R.drawable.ic_bus),
                CategoryIcon(4, R.drawable.ic_house),
                CategoryIcon(5, R.drawable.ic_airplane),
                CategoryIcon(6, R.drawable.ic_planet),
                CategoryIcon(7, R.drawable.ic_paint),
                CategoryIcon(8, R.drawable.ic_diving),
                CategoryIcon(9, R.drawable.ic_ticket),
                CategoryIcon(10, R.drawable.ic_notebook),
                CategoryIcon(11, R.drawable.ic_tasks),
                CategoryIcon(12, R.drawable.ic_geometry),
                CategoryIcon(13, R.drawable.ic_medal),
            )
        )

    override fun getIconById(id: Long): Single<CategoryIcon> =
        iconDao.getIconById(id)

}