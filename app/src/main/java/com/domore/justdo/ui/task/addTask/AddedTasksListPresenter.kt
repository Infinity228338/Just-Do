package com.domore.justdo.ui.task.addTask

import com.domore.justdo.data.vo.ModeType
import com.domore.justdo.data.vo.TimeTypes
import com.domore.justdo.ui.base.IListPresenter
import com.domore.justdo.ui.task.TaskItemView

interface AddedTasksListPresenter : IListPresenter<TaskItemView> {
    fun notifyItemChanged(selectedItemPos: Int)
    fun timeClicked(timeType: TimeTypes)
    fun modifyClicked(name: String)
    fun dateClicked()
    var editClickListener: ((TaskItemView) -> Unit)?
    var deleteClickListener: ((TaskItemView) -> Unit)?
    var selectedModeChangedListener: ((TaskItemView, ModeType) -> Unit)?
    var editDoneClickListener: ((TaskItemView) -> Unit)?

    var selectedItemPos: Int
}