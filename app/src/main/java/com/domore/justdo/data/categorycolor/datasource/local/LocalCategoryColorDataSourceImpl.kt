package com.domore.justdo.data.categorycolor.datasource.local

import com.domore.justdo.R
import com.domore.justdo.data.vo.CategoryColor
import com.domore.justdo.storage.JustDoDatabase
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class LocalCategoryColorDataSourceImpl
@Inject constructor(
    justDoDb: JustDoDatabase
) : LocalCategoryColorDataSource {

    private val colorDao = justDoDb.categoryColorDao()

    override fun getColors(): Single<List<CategoryColor>> =
        colorDao
            .getColors()
            .flatMap(::populateDbIfRequired)

    private fun populateDbIfRequired(colors: List<CategoryColor>): Single<List<CategoryColor>> =
        if (colors.isEmpty()) {
            Single.just(getPredefined())
                .flatMap(::retain)
        } else {
            Single.just(colors)
        }

    override fun getColorById(id: Long): Single<CategoryColor> = colorDao.getColorById(id)

    override fun retain(colors: List<CategoryColor>): Single<List<CategoryColor>> =
        colorDao
            .retain(colors)
            .andThen(getColors())


    override fun retain(color: CategoryColor): Single<CategoryColor> =
        colorDao
            .retain(color)
            .andThen(getColorById(color.id))

    companion object PredefinedColors {
        fun getPredefined() = mutableListOf(
            CategoryColor(1, R.color.cat1),
            CategoryColor(2, R.color.cat2),
            CategoryColor(3, R.color.cat3),
            CategoryColor(4, R.color.cat4),
            CategoryColor(5, R.color.cat5),
            CategoryColor(6, R.color.cat6),
            CategoryColor(7, R.color.cat7),
            CategoryColor(8, R.color.cat8),
            CategoryColor(9, R.color.cat9),
            CategoryColor(10, R.color.cat10),
            CategoryColor(11, R.color.cat11),
            CategoryColor(12, R.color.cat12),
            CategoryColor(13, R.color.cat13),
            CategoryColor(14, R.color.cat4),
            CategoryColor(15, R.color.cat15),
            CategoryColor(16, R.color.cat16),
            CategoryColor(17, R.color.cat17),
            CategoryColor(18, R.color.cat18),
            CategoryColor(19, R.color.cat19),
            CategoryColor(20, R.color.cat20),
            CategoryColor(21, R.color.cat21)
        )
    }
}