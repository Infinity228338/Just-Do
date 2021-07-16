package com.domore.justdo.ui.categories.add.colors

import com.domore.justdo.data.vo.CategoryColor
import com.domore.justdo.ui.base.IListPresenter

interface ColorsListPresenter : IListPresenter<ColorItemView> {
}

class ColorsListPresenterImpl : ColorsListPresenter {
    val colors = mutableListOf<CategoryColor>()
    override var itemClickListener: ((ColorItemView) -> Unit)? = null

    override fun getCount() = colors.size

    override fun bindView(view: ColorItemView) {
        view.bind(colors[view.pos])
    }

}