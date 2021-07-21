package com.domore.justdo.ui.categories.list

import com.domore.justdo.data.vo.Category
import com.domore.justdo.ui.base.ItemView

interface CategoriesItemView : ItemView {
    fun bind(category: Category)
}