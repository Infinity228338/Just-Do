package com.domore.justdo.ui.task.addTask

import com.domore.justdo.data.task.repository.TaskRepository
import com.domore.justdo.data.vo.ModeType
import com.domore.justdo.data.vo.TimeTypes
import com.domore.justdo.schedulers.Schedulers
import com.github.terrakok.cicerone.Router
import dagger.assisted.AssistedInject
import io.reactivex.rxjava3.disposables.CompositeDisposable
import moxy.MvpPresenter
import java.text.SimpleDateFormat
import java.util.*

class AddTaskPresenter @AssistedInject constructor(
    private val taskRepository: TaskRepository,
    private val schedulers: Schedulers,
    private val router: Router
) : MvpPresenter<AddTaskView>() {

    private var disposables = CompositeDisposable()
    private var cardTaskModeExpanded = false


    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
    }

    fun cardTaskModeClicked() {
        cardTaskModeExpanded = !cardTaskModeExpanded
        viewState.showOrHideModes(cardTaskModeExpanded)
        viewState.hideAllTimes()
        if (cardTaskModeExpanded)
            modeClicked(ModeType.INTERVAL)
    }

    fun modeClicked(mode: ModeType) {
        viewState.hideAllTimes()
        viewState.processModeClick(
            mode, Calendar.getInstance().getFormatted()
        )
    }

    fun dateClicked() {
        if (cardTaskModeExpanded) cardTaskModeClicked()
        viewState.showDatePicker(Calendar.getInstance())
    }

    fun setDate(dateAndTime: Calendar) {
        dateAndTime.time
    }

    fun timeStartClicked() {
        setTime(Calendar.getInstance(), TimeTypes.INTERVAL_START)
        viewState.showTimePicker(Calendar.getInstance(), TimeTypes.INTERVAL_START)
    }

    fun timeEndClicked() {
        setTime(Calendar.getInstance(), TimeTypes.INTERVAL_END)
        viewState.showTimePicker(Calendar.getInstance(), TimeTypes.INTERVAL_END)
    }

    fun timeDurationClicked() {
        viewState.showTimerPicker()
    }

    fun timerSelected(hours: Int, minutes: Int, seconds: Int) {
        setTimeFormatted("$hours:$minutes:$seconds", TimeTypes.TIMER)
    }

    fun timePreciseClicked() {
        setTime(Calendar.getInstance(), TimeTypes.PRECISE_TIME)
        viewState.showTimePicker(Calendar.getInstance(), TimeTypes.PRECISE_TIME)
    }

    fun backPressed(): Boolean {
        router.exit()
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.dispose()
    }

    fun setTime(time: Calendar, timeTypes: TimeTypes) {
        time.getFormatted().let {
            setTimeFormatted(it, timeTypes)
        }
    }

    private fun setTimeFormatted(timeFormatted: String, timeTypes: TimeTypes) {
        timeFormatted.let {
            when (timeTypes) {
                TimeTypes.INTERVAL_START -> viewState.setTimeStartText(it)
                TimeTypes.INTERVAL_END -> viewState.setTimeEndText(it)
                TimeTypes.TIMER -> viewState.setTimerText(it)
                TimeTypes.PRECISE_TIME -> viewState.setPreciseText(it)
            }
        }
    }
}

fun Calendar.getFormatted(): String {
    val formatter = SimpleDateFormat(
        "hh:mm:ss",
        Locale.getDefault()
    )
    return formatter.format(this.time)
}