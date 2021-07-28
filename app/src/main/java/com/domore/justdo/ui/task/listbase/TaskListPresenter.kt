package com.domore.justdo.ui.task.listbase

import com.domore.justdo.ui.base.IListPresenter

interface TaskListPresenter : IListPresenter<TaskItemView> {
    fun editIconClick(pos: Int)
    fun deleteIconClick(pos: Int)
    var selectedItemPos: Int

}