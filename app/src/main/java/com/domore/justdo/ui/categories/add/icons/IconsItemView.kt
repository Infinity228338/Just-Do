package com.domore.justdo.ui.categories.add.icons

import com.domore.justdo.data.vo.CategoryIcon
import com.domore.justdo.ui.base.ItemView

interface IconsItemView : ItemView {
    fun bind(categoryIcon: CategoryIcon)
}