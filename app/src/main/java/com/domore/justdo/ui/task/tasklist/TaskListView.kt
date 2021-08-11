package com.domore.justdo.ui.task.tasklist

import moxy.MvpView
import moxy.viewstate.strategy.alias.SingleState

@SingleState
interface TaskListView : MvpView {
    fun init()
    fun updateList()
    fun hidetext()
}