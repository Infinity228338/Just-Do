package com.domore.justdo.ui.task

import com.domore.justdo.data.vo.ModeType
import com.domore.justdo.data.vo.Task
import com.domore.justdo.ui.base.ItemView

interface TaskItemView : ItemView {
    fun bind(task: Task)
    fun itemClicked(task: Task)
    fun editClicked()
    fun editDoneClicked()
    fun modeClicked(modeType: ModeType)
}