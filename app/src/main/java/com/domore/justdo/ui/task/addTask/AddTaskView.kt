package com.domore.justdo.ui.task.addTask

import com.domore.justdo.data.vo.ModeType
import com.domore.justdo.data.vo.Task
import com.domore.justdo.data.vo.TimeTypes
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.SkipStrategy
import moxy.viewstate.strategy.StateStrategyType
import java.util.*

@StateStrategyType(AddToEndSingleStrategy::class)
interface AddTaskView : MvpView {
    fun init()

    fun hideAllTimes()

    @StateStrategyType(SkipStrategy::class)
    fun showDatePicker(date: Calendar)

    @StateStrategyType(SkipStrategy::class)
    fun showTimePicker(time: Calendar, timeTypes: TimeTypes)

    @StateStrategyType(SkipStrategy::class)
    fun showTimerPicker()

    fun drawTask(currentTask: Task)

    fun expandOrCollapseCard(cardTaskNameExpanded: Boolean)

    fun addItemToList(position: Int)
    fun removeItem(pos: Int)
    fun notifyItemChanged(selectedItemPos: Int)

    fun processModeClick(modeType: ModeType)
    fun showOrHideModes(modesExpanded: Boolean)
}