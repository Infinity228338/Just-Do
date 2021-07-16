package com.domore.justdo.ui.categories.add.colors

import com.domore.justdo.data.vo.CategoryColor
import com.domore.justdo.ui.base.ItemView

interface ColorItemView : ItemView {
    fun bind(categoryColor: CategoryColor)
}