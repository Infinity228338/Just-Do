package com.domore.justdo.ui.task.addTask

import com.domore.justdo.ui.base.IListPresenter
import com.domore.justdo.ui.task.TaskItemView

interface AddedTasksListPresenter : IListPresenter<TaskItemView> {
    fun editIconClick(pos: Int)
    fun deleteIconClick(pos: Int)
    fun notifyItemChanged(selectedItemPos: Int)

    var selectedItemPos: Int
}