package com.domore.justdo.ui.task.addTask

import com.domore.justdo.data.vo.TimeTypes
import com.domore.justdo.ui.base.IListPresenter
import com.domore.justdo.ui.task.TaskItemView

interface AddedTasksListPresenter : IListPresenter<TaskItemView> {
    fun deleteIconClick(pos: Int)
    fun notifyItemChanged(selectedItemPos: Int)
    fun timeClicked(timeType: TimeTypes)
    fun modifyClicked(name: String)
    fun dateClicked()
    var editClickListener: ((TaskItemView) -> Unit)?
    var editDoneClickListener: ((TaskItemView) -> Unit)?

    var selectedItemPos: Int
}