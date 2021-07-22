package com.domore.justdo.ui.task.addTask

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface AddTaskView : MvpView {
    fun init()
    fun showOrHideModes(shown: Boolean)
    fun showOrHideIntervalTime(shown: Boolean)
    fun addItemToList(position: Int)
    fun showDatePicker()
}