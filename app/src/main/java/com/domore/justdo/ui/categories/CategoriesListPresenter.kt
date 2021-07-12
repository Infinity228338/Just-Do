package com.domore.justdo.ui.categories

import com.domore.justdo.ui.base.IListPresenter

interface CategoriesListPresenter : IListPresenter<CategoriesItemView> {
    companion object {
        const val TYPE_ITEM = 1
        const val TYPE_FOOTER = 0
    }
}