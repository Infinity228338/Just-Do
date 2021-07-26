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
        iconDao
            .getIcons()
            .flatMap(::populateDbIfRequired)

    override fun getPredefinedIcons(): Single<List<CategoryIcon>> =
        iconDao
            .getIcons()


    private fun populateDbIfRequired(icons: List<CategoryIcon>): Single<List<CategoryIcon>> =
        if (icons.isEmpty()) {
            Single.just(getPredefined())
                .flatMap(::retain)
        } else {
            Single.just(icons)
        }

    fun retain(colors: List<CategoryIcon>): Single<List<CategoryIcon>> =
        iconDao
            .retain(colors)
            .andThen(getIcons())

    override fun getIconById(id: Long): Single<CategoryIcon> =
        iconDao.getIconById(id)

    companion object PredefinedIcons {
        fun getPredefined() = mutableListOf(
            CategoryIcon(1, R.drawable.ic_glasses),
            CategoryIcon(2, R.drawable.ic_movie),
            CategoryIcon(3, R.drawable.ic_birthday),
            CategoryIcon(4, R.drawable.ic_bus),
            CategoryIcon(5, R.drawable.ic_house),
            CategoryIcon(6, R.drawable.ic_airplane),
            CategoryIcon(7, R.drawable.ic_planet),
            CategoryIcon(8, R.drawable.ic_paint),
            CategoryIcon(9, R.drawable.ic_diving),
            CategoryIcon(10, R.drawable.ic_ticket),
            CategoryIcon(11, R.drawable.ic_notebook),
            CategoryIcon(12, R.drawable.ic_tasks),
            CategoryIcon(13, R.drawable.ic_geometry),
            CategoryIcon(14, R.drawable.ic_medal)
        )
    }
}