package com.domore.justdo.ui.task.addTask

import com.domore.justdo.data.category.repository.CategoryRepository
import com.domore.justdo.data.task.repository.TaskRepository
import com.domore.justdo.data.vo.Category
import com.domore.justdo.data.vo.ModeType
import com.domore.justdo.data.vo.Task
import com.domore.justdo.data.vo.TimeTypes
import com.domore.justdo.schedulers.Schedulers
import com.domore.justdo.ui.JustDoScreensImpl
import com.domore.justdo.ui.task.TaskItemView
import com.domore.justdo.ui.task.addTask.timepicker.TimePickerDialogFragment
import com.github.terrakok.cicerone.Router
import dagger.assisted.AssistedInject
import io.reactivex.rxjava3.disposables.CompositeDisposable
import moxy.MvpPresenter
import java.util.*

class AddTaskPresenter @AssistedInject constructor(
    private val taskRepository: TaskRepository,
    private val categoryRepository: CategoryRepository,
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
        override var editClickListener: ((TaskItemView) -> Unit)? = null
        override var deleteClickListener: ((TaskItemView) -> Unit)? = null
        override var selectedModeChangedListener: ((TaskItemView, ModeType) -> Unit)? = null
        override var editDoneClickListener: ((TaskItemView) -> Unit)? = null

        override fun bindView(view: TaskItemView) {
            view.bind(tasks[view.pos])
        }

        override fun notifyItemChanged(selectedItemPos: Int) {
            viewState.notifyItemChanged(selectedItemPos)
        }

        override fun timeClicked(timeType: TimeTypes) {
            if (timeType == TimeTypes.TIMER)
                viewState.showTimerPicker(object : TimePickerDialogFragment.OnTimeSelectedListener {
                    override fun onTimeSubmit(hours: Int, minutes: Int, seconds: Int) {
                        timerListSelected(hours, minutes, seconds)
                    }
                })
            else {
                val calendar = Calendar.getInstance()
                viewState.showTimePicker(calendar, timeType) { _, hourOfDay, minute ->
                    calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                    calendar.set(Calendar.MINUTE, minute)
                    setTimeListItem(calendar, timeType)
                }
            }
        }

        override fun dateClicked() {
            val calendar = Calendar.getInstance()
            viewState.showDatePicker(
                calendar
            ) { _, year, month, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                setDate(calendar)
            }
        }


        private fun setDate(dateAndTime: Calendar) {
            selectedTask?.let {
                it.date = dateAndTime.time
                updateTask(it)
            }

            viewState.notifyItemChanged(selectedItemPos)
        }

        private fun setTimeListItem(time: Calendar, timeTypes: TimeTypes) {
            if (timeTypes == TimeTypes.INTERVAL_START)
                selectedTask?.timeStart = time.time
            if (timeTypes == TimeTypes.INTERVAL_END)
                selectedTask?.timeEnd = time.time
            if (timeTypes == TimeTypes.PRECISE_TIME)
                time.time.let {
                    selectedTask?.timeStart = it
                    selectedTask?.timeEnd = it
                }
            selectedTask?.let { updateTask(it) }
            viewState.notifyItemChanged(selectedItemPos)
        }

        fun timerListSelected(hours: Int, minutes: Int, seconds: Int) {
            "$hours:$minutes:$seconds".apply {
                selectedTask?.let {
                    it.timerTime = this
                    updateTask(it)
                }
                viewState.notifyItemChanged(selectedItemPos)
            }
        }

        override fun modifyClicked(name: String) {
            selectedTask?.name = name
            viewState.notifyItemChanged(selectedItemPos)
        }

        override fun getCount(): Int = tasks.size

        private val selectedTask: Task?
            get() =
                if (selectedItemPos != -1)
                    tasks[selectedItemPos]
                else null
    }

    val taskListPresenter = TaskListPresenterImpl()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
        currentTask = Task()
        taskListPresenter.apply {
            itemClickListener = { it.itemClicked(tasks[it.pos]) }
            editClickListener = { it.editClicked() }
            deleteClickListener = {
                tasks.removeAt(it.pos)
                viewState.removeItem(it.pos)
                viewState.changeRange(it.pos, tasks.size);
            }
            selectedModeChangedListener = { view, type ->
                tasks[view.pos].mode = type.name
                view.modeClicked(type)
            }
            editDoneClickListener = { it.editDoneClicked() }
        }
    }

    fun expandCard() {
        if (currentCategory == null) setCategory(-1L)
        cardTaskExpanded = true
        viewState.expandOrCollapseCard(cardTaskExpanded)
    }

    private fun collapseCard() {
        cardTaskExpanded = false
        viewState.expandOrCollapseCard(cardTaskExpanded)
    }

    fun setCategory(categoryId: Long) {
        if (categoryId == -1L) {
            router.setResultListener("kek_lol") {
                setCategory(it as Long)
            }
            router.navigateTo(JustDoScreensImpl.categoriesScreen())
        } else {
            currentTask.categoryId = categoryId
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

    fun modeSelectorClicked() {
        modesExpanded = !modesExpanded
        viewState.showOrHideModes(modesExpanded)
        viewState.hideAllTimes()
        if (modesExpanded)
            modeClicked(ModeType.INTERVAL)
    }

    fun modeClicked(modeType: ModeType) {
        currentTask.mode = modeType.name
        viewState.hideAllTimes()
        viewState.processModeClick(modeType)
        viewState.drawTask(currentTask)

    }

    fun addClicked(name: String) {
        if (name.isNotBlank())
            saveTask(name)
        if (cardTaskExpanded) collapseCard()
        else expandCard()
    }

    private fun saveTask(name: String) {
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
        collapseCard()
        currentTask = Task()
        viewState.drawTask(currentTask)
        currentCategory = null
    }

    private fun updateTask(task: Task) {
        task.let {
            taskRepository.update(it)
                .subscribeOn(schedulers.background())
                .observeOn(schedulers.background())
                .subscribe()
        }
    }

    fun timeClicked(timeTypes: TimeTypes) {
        if (timeTypes == TimeTypes.TIMER)
            viewState.showTimerPicker(object : TimePickerDialogFragment.OnTimeSelectedListener {
                override fun onTimeSubmit(hours: Int, minutes: Int, seconds: Int) {
                    timerSelected(hours, minutes, seconds)
                }
            })
        else {
            val calendar = Calendar.getInstance()
            viewState.showTimePicker(calendar, timeTypes) { _, hourOfDay, minute ->
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                calendar.set(Calendar.MINUTE, minute)
                setTime(calendar, timeTypes)
            }
        }
    }

    private fun setTime(time: Calendar, timeTypes: TimeTypes) {
        if (timeTypes == TimeTypes.INTERVAL_START)
            currentTask.timeStart = time.time
        if (timeTypes == TimeTypes.INTERVAL_END)
            currentTask.timeEnd = time.time
        if (timeTypes == TimeTypes.PRECISE_TIME)
            time.time.let {
                currentTask.timeStart = it
                currentTask.timeEnd = it
            }
        viewState.drawTask(currentTask)
    }

    fun dateClicked() {
        val calendar = Calendar.getInstance()
        viewState.showDatePicker(
            calendar
        ) { _, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            setDate(calendar)
        }
    }

    private fun setDate(dateAndTime: Calendar) {
        currentTask.date = dateAndTime.time
        viewState.drawTask(currentTask)
    }


    fun timerSelected(hours: Int, minutes: Int, seconds: Int) {
        "$hours:$minutes:$seconds".let {
            currentTask.timerTime = it
            viewState.drawTask(currentTask)
        }
    }

    fun backPressed(): Boolean {
        router.exit()
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.dispose()
    }
}

