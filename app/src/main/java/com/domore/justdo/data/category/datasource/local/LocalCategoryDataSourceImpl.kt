package com.domore.justdo.data.category.datasource.local

import com.domore.justdo.R
import com.domore.justdo.data.vo.Category
import com.domore.justdo.storage.JustDoDatabase
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class LocalCategoryDataSourceImpl
@Inject constructor(
    justDoDb: JustDoDatabase
) : LocalCategoryDataSource {
    private val categoryDao = justDoDb.categoryDao()
    override fun getCategories(): Single<List<Category>> =
        categoryDao
            .getCategories()
            .flatMap(::populateDbIfRequired)


    override fun getCategoryById(id: Long): Single<Category> =
        categoryDao.getCategoriesById(id)

    override fun addCategory(name: String, colorRes: Int, drawRes: Int): Single<Category> =
        categoryDao
            .retain(Category(0, 0, name, colorRes, drawRes))
            .flatMap { categoryDao.getCategoriesById(it) }

    private fun populateDbIfRequired(categories: List<Category>): Single<List<Category>> =
        if (categories.isEmpty()) {
            Single.just(get())
                .flatMap(::retain)
        } else {
            Single.just(categories)
        }

    fun retain(categories: List<Category>): Single<List<Category>> =
        categoryDao
            .retain(categories)
            .andThen(getCategories())

    companion object PredefinedCategories {
        fun get() = mutableListOf<Category>(
            Category(1, R.string.sport, null, 0, R.drawable.predef_icon_sport),
            Category(2, R.string.house, null, 0, R.drawable.predef_icon_home),
            Category(3, R.string.celebration, null, 0, R.drawable.predef_icon_present),
            Category(4, R.string.health, null, 0, R.drawable.predef_icon_health),
            Category(5, R.string.meeting, null, 0, R.drawable.predef_icon_person),
            Category(6, R.string.coffee, null, 0, R.drawable.predef_icon_coffee),
            Category(7, R.string.study, null, 0, R.drawable.predef_icon_study),
            Category(8, R.string.food, null, 0, R.drawable.predef_icon_food),
            Category(9, R.string.sleeping, null, 0, R.drawable.predef_icon_sleep),
            Category(10, R.string.shopping, null, 0, R.drawable.predef_icon_shopping),
            Category(11, R.string.chill, null, 0, R.drawable.predef_icon_chill),
            Category(12, R.string.reading, null, 0, R.drawable.predef_icon_read),
            Category(13, R.string.finance, null, 0, R.drawable.predef_icon_wallet),
            Category(14, R.string.walking, null, 0, R.drawable.predef_icon_walk),
            Category(15, R.string.drive, null, 0, R.drawable.predef_icon_car),
        )
    }

}