package com.domore.justdo.ui.task.addTask

import com.domore.justdo.data.vo.ModeType
import com.domore.justdo.data.vo.TimeTypes
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType
import java.util.*

@StateStrategyType(AddToEndSingleStrategy::class)
interface AddTaskView : MvpView {
    fun init()

    fun showOrHideModes(shown: Boolean)
    fun hideAllTimes()

    fun showDatePicker(date: Calendar)
    fun showTimePicker(time: Calendar, timeTypes: TimeTypes)
    fun showTimerPicker()

    fun setTimeStartText(formattedTime: String)
    fun setTimeEndText(formattedTime: String)
    fun setTimerText(formattedTime: String)
    fun setPreciseText(formattedTime: String)
    fun setDate(dateFormatted: String)

    fun processModeClick(modeType: ModeType, formatted: String)
    fun processNameCardClick(cardTaskNameExpanded: Boolean)

    fun addItemToList(position: Int)

}