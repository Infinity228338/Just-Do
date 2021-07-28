package com.domore.justdo.ui.task.addTask

import com.domore.justdo.data.category.repository.CategoryRepository
import com.domore.justdo.data.mode.repository.ModeRepository
import com.domore.justdo.data.task.repository.TaskRepository
import com.domore.justdo.data.vo.*
import com.domore.justdo.getDateFormatted
import com.domore.justdo.getTimeFormatted
import com.domore.justdo.schedulers.Schedulers
import com.domore.justdo.ui.JustDoScreensImpl
import com.domore.justdo.ui.task.TaskItemView
import com.github.terrakok.cicerone.Router
import dagger.assisted.AssistedInject
import io.reactivex.rxjava3.disposables.CompositeDisposable
import moxy.MvpPresenter
import java.util.*

class AddTaskPresenter @AssistedInject constructor(
    private val taskRepository: TaskRepository,
    private val categoryRepository: CategoryRepository,
    private val modeRepository: ModeRepository,
    private val schedulers: Schedulers,
    private val router: Router
) : MvpPresenter<AddTaskView>() {

    private var disposables = CompositeDisposable()
    private var modesExpanded = false
    private var cardTaskExpanded = false
    private lateinit var currentTask: Task
    private var currentCategory: Category? = null

    inner class TaskListPresenterImpl : AddedTasksListPresenter {
        val tasks = mutableListOf<Task>()
        override var selectedItemPos = -1

        override var itemClickListener: ((TaskItemView) -> Unit)? = null

        override fun bindView(view: TaskItemView) {
            view.bind(tasks[view.pos])
        }

        override fun editIconClick(pos: Int) {
            currentTask = tasks[pos]
        }

        override fun deleteIconClick(pos: Int) {
            tasks.removeAt(pos)
            viewState.removeItem(pos)
        }

        override fun getCount(): Int = tasks.size

        fun getSelected(): Task? {
            return if (selectedItemPos != -1) {
                tasks[selectedItemPos]
            } else null
        }
    }

    val taskListPresenter = TaskListPresenterImpl()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
        currentTask = Task()
//        taskListPresenter.itemClickListener = {
//            it.itemClicked(taskListPresenter.tasks[it.pos])
//        }
    }


    fun cardTaskClicked() {
        cardTaskExpanded = !cardTaskExpanded
        if (cardTaskExpanded && currentCategory == null) setCategory(-1L)
        viewState.expandOrCollapseCard(cardTaskExpanded)
    }

    fun modeSelectorClicked() {
        modesExpanded = !modesExpanded
        viewState.showOrHideModes(modesExpanded)
        viewState.hideAllTimes()
        if (modesExpanded)
            modeClicked(ModeType.INTERVAL)
    }

    fun modeClicked(mode: ModeType) {
        disposables.add(
            modeRepository
                .getModeByName(mode.name)
                .observeOn(schedulers.background())
                .subscribeOn(schedulers.background())
                .subscribe(::onModeLoaded)
        )

        viewState.hideAllTimes()
        viewState.processModeClick(
            mode,
            if (mode == ModeType.TIMER) "00:00:00"
            else Calendar.getInstance().getTimeFormatted()
        )
    }

    fun addIconClicked(name: String) {
        if (cardTaskExpanded && name.isNotBlank())
            okClicked(name)
        else {
            cardTaskClicked()
        }
    }

    private fun onModeLoaded(mode: Mode) {
        currentTask.modeId = mode.id
    }

    fun dateClicked() {
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
        "$hours:$minutes:$seconds".let {
            currentTask.period = it
            setTimeFormatted(it, TimeTypes.TIMER)
        }

    }

    fun timePreciseClicked() {
        setTime(Calendar.getInstance(), TimeTypes.PRECISE_TIME)
        viewState.showTimePicker(Calendar.getInstance(), TimeTypes.PRECISE_TIME)
    }

    fun okClicked(name: String) {
        currentTask.name = name
        taskRepository
            .saveTask(currentTask)
            .observeOn(schedulers.main())
            .subscribeOn(schedulers.background())
            .subscribe(::onTaskSaved)
    }

    private fun onTaskSaved(task: Task) {
        taskListPresenter.tasks.let {
            it.add(task)
            viewState.addItemToList(it.lastIndex)
        }
        cardTaskClicked()
        currentTask = Task()
        currentCategory = null
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
        if (categoryId == -1L) {
            router.setResultListener("kek_lol") {
                setCategory(it as Long)
            }
            router.navigateTo(JustDoScreensImpl.categoriesScreen())
        } else {
            currentTask?.categoryId = categoryId
            categoryRepository
                .getCategoryById(categoryId)
                .subscribeOn(schedulers.background())
                .observeOn(schedulers.background())
                .subscribe { category ->
                    currentCategory = category
                    currentTask.iconResId = category.iconResId
                }
        }
    }
}

