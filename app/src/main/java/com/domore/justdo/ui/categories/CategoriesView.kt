package com.domore.justdo.ui.categories

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface CategoriesView : MvpView {
    fun init()
    fun updateList()
    fun addItemToList(position: Int)
    fun updateItem(position: Int)
    fun showDialog()
}