package com.domore.justdo.ui.task.addTask

import com.domore.justdo.data.mode.repository.ModeRepository
import com.domore.justdo.data.task.repository.TaskRepository
import com.domore.justdo.data.vo.Mode
import com.domore.justdo.data.vo.ModeType
import com.domore.justdo.data.vo.Task
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
    private val modeRepository: ModeRepository,
    private val schedulers: Schedulers,
    private val router: Router
) : MvpPresenter<AddTaskView>() {

    private var disposables = CompositeDisposable()
    private var cardTaskModeExpanded = false
    private var cardTaskNameExpanded = false
    private lateinit var currentTask: Task


    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
    }

    fun cardTaskNameClicked() {
        cardTaskNameExpanded = !cardTaskNameExpanded
        viewState.processNameCardClick(cardTaskNameExpanded)
    }

    fun cardTaskModeClicked() {
        if (cardTaskNameExpanded) cardTaskNameClicked()
        cardTaskModeExpanded = !cardTaskModeExpanded
        viewState.showOrHideModes(cardTaskModeExpanded)
        viewState.hideAllTimes()
        if (cardTaskModeExpanded)
            modeClicked(ModeType.INTERVAL)
    }

    fun modeClicked(mode: ModeType) {
        modeRepository
            .getModeByName(mode.name)
            .observeOn(schedulers.main())
            .subscribeOn(schedulers.background())
            .subscribe(::onModeLoaded)

        viewState.hideAllTimes()
        viewState.processModeClick(
            mode,
            if (mode == ModeType.TIMER) "00:00:00"
            else Calendar.getInstance().getTimeFormatted()
        )
    }

    private fun onModeLoaded(mode: Mode) {
        currentTask.modeId = mode.id
    }

    fun dateClicked() {
        if (cardTaskNameExpanded) cardTaskNameClicked()
        if (cardTaskModeExpanded) cardTaskModeClicked()
        viewState.showDatePicker(Calendar.getInstance())
    }

    fun setDate(dateAndTime: Calendar) {
        currentTask.date = dateAndTime.time
        viewState.setDate(dateAndTime.getDateFormatted())
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
        currentTask.period = "$hours:$minutes:$seconds"
        setTimeFormatted("$hours:$minutes:$seconds", TimeTypes.TIMER)
    }

    fun timePreciseClicked() {
        setTime(Calendar.getInstance(), TimeTypes.PRECISE_TIME)
        viewState.showTimePicker(Calendar.getInstance(), TimeTypes.PRECISE_TIME)
    }

    fun okClicked(name: String) {
        currentTask.name = name
        taskRepository.saveTask(currentTask)
        currentTask = Task()
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
        if (timeTypes == TimeTypes.INTERVAL_START)
            currentTask.timeStart = time.time
        if (timeTypes == TimeTypes.INTERVAL_END)
            currentTask.timeEnd = time.time
        if (timeTypes == TimeTypes.PRECISE_TIME)
            time.time.let {
                currentTask.timeStart = it
                currentTask.timeEnd = it
            }
        setTimeFormatted(time.getTimeFormatted(), timeTypes)
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

    fun setCategory(categoryId: Long) {
        currentTask = Task()
        currentTask.categoryId = categoryId
    }

}

fun Calendar.getTimeFormatted(): String {
    val formatter = SimpleDateFormat(
        "hh:mm:ss",
        Locale.getDefault()
    )
    return formatter.format(this.time)
}

fun Calendar.getDateFormatted(): String {
    val formatter = SimpleDateFormat(
        "dd.MM.yy",
        Locale.getDefault()
    )
    return formatter.format(this.time)
}