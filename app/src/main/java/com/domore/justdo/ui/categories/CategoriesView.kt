package com.domore.justdo.ui.categories

import moxy.MvpView
import moxy.viewstate.strategy.alias.SingleState

@SingleState
interface CategoriesView : MvpView {
    fun init()
    fun updateList()
    fun addItemToList(position: Int)
    fun showDialog()
}