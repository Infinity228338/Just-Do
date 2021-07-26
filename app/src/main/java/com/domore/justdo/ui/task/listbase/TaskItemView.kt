package com.domore.justdo.ui.task.listbase

import com.domore.justdo.data.vo.Task
import com.domore.justdo.ui.base.ItemView

interface TaskItemView : ItemView {
    fun bind(task: Task)
}