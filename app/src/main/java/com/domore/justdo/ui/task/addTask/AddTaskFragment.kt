package com.domore.justdo.ui.task.addTask

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.TransitionManager
import com.domore.justdo.R
import com.domore.justdo.data.vo.ModeType
import com.domore.justdo.data.vo.Task
import com.domore.justdo.data.vo.TimeTypes
import com.domore.justdo.databinding.FragmentAddTaskBinding
import com.domore.justdo.ui.base.*
import com.domore.justdo.ui.task.addTask.timepicker.TimePickerDialogFragment
import com.domore.justdo.util.getDateFormatted
import com.domore.justdo.util.getTimeFormatted
import moxy.ktx.moxyPresenter
import java.util.*
import javax.inject.Inject


const val ARG_CATEGORY_ID = "category_id"

class AddTaskFragment : BaseFragment(R.layout.fragment_add_task), AddTaskView, BackButtonListener,
    TimePickerDialogFragment.OnTimeSelectedListener {


    @Inject
    lateinit var addTaskPresenterFactory: AddTaskPresenterFactory
    private var viewBinding: FragmentAddTaskBinding? = null
    private var categoryId: Long? = null
    private val presenter: AddTaskPresenter by moxyPresenter {
        addTaskPresenterFactory.create()
    }
    private var adapter: AddedTasksAdapter? = null
    lateinit var cardViews: List<View>
    lateinit var modeViews: List<View>
    lateinit var timeViews: List<View>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            categoryId = it.getLong(ARG_CATEGORY_ID)
            presenter.setCategory(categoryId!!)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        activity?.title = getString(R.string.add_task)
        adapter = AddedTasksAdapter(presenter.taskListPresenter)
        viewBinding = FragmentAddTaskBinding.inflate(inflater, container, false)
        viewBinding?.apply {
            cardViews = listOf(
                timeIcon,
                textModeSelector,
                barrier,
                calendarIcon,
                textDate,
                textDateSelected,
                textCancel,
                textOk
            )
            modeViews = listOf(
                textModeInterval, textModeTimer, textModePrecise
            )
            timeViews = listOf(
                textStart,
                timeStart,
                textEnd,
                timeEnd,
                textDuration,
                timeTimer,
                textPrecise,
                timePrecise
            )
            rvNewTasks.also {
                it.layoutManager = LinearLayoutManager(context)
                it.adapter = adapter
            }
        }
        return viewBinding!!.root
    }

    companion object {
        @JvmStatic
        fun newInstance(id: Long) =
            AddTaskFragment().apply {
                arguments = Bundle().apply {
                    putLong(ARG_CATEGORY_ID, id)
                }
            }
    }

    override fun init() {
        viewBinding?.apply {
            cardTask.setOnClickListener {
                presenter.expandCard()
            }
            editTaskName.apply {
                setOnFocusChangeListener { _, hasFocus ->
                    if (!hasFocus)
                        hideKeyboard()
                    else
                        presenter.expandCard()


                }
                setOnClickListener {
                    presenter.expandCard()
                }
            }

            textModeInterval.setOnClickListener {
                presenter.modeClicked(ModeType.INTERVAL)
            }
            textModeTimer.setOnClickListener {
                presenter.modeClicked(ModeType.TIMER)
            }
            textModePrecise.setOnClickListener {
                presenter.modeClicked(ModeType.PRECISE_TIME)
            }


            listOf(textStart, timeStart).forEach {
                it.setOnClickListener { presenter.timeClicked(TimeTypes.INTERVAL_START) }
            }
            listOf(textEnd, timeEnd).forEach {
                it.setOnClickListener { presenter.timeClicked(TimeTypes.INTERVAL_END) }
            }
            listOf(textDuration, timeTimer).forEach {
                it.setOnClickListener { presenter.timeClicked(TimeTypes.TIMER) }
            }
            listOf(textPrecise, timePrecise).forEach {
                it.setOnClickListener { presenter.timeClicked(TimeTypes.PRECISE_TIME) }
            }

            listOf(textOk, addIcon).forEach {
                it.setOnClickListener { presenter.addClicked(editTaskName.text.toString()) }
            }

            textCancel.setOnClickListener {
                presenter.backPressed()
            }

            listOf(timeIcon, textModeSelector).forEach {
                it.setOnClickListener { presenter.modeSelectorClicked() }
            }
            listOf(calendarIcon, textDate, textDateSelected).forEach {
                it.setOnClickListener { presenter.dateClicked() }
            }
        }
    }

    override fun expandOrCollapseCard(cardTaskNameExpanded: Boolean) {
        val color =
            getResColor(
                requireContext(),
                if (cardTaskNameExpanded) R.color.white else R.color.ultra_light_grey
            )
        val visibility = getVisibility(cardTaskNameExpanded)
        val addIconRes = if (cardTaskNameExpanded) R.drawable.ic_icon_check else R.drawable.ic_add_inactive
        val itemsIconRes = if (cardTaskNameExpanded) R.drawable.ic_icon_name else R.drawable.ic_icon_list
        viewBinding?.apply {
            TransitionManager.beginDelayedTransition(addTaskView)
            cardTask.setCardBackgroundColor(color)
            addIcon.setImageResource(addIconRes)
            itemsIcon.setImageResource(itemsIconRes)
            if (!cardTaskNameExpanded) timeViews.forEach { it.visibility = visibility }
            if (!cardTaskNameExpanded) modeViews.forEach { it.visibility = visibility }
            cardViews.forEach { it.visibility = visibility }
            editTaskName.also {
                if (!cardTaskNameExpanded) hideKeyboard()
                it.isCursorVisible = cardTaskNameExpanded
            }
        }
    }

    override fun showOrHideModes(modesExpanded: Boolean) {
        val visibility = getVisibility(modesExpanded)
        val text =
            requireContext()
                .getString(if (modesExpanded) R.string.choose_mode else R.string.mode_and_time)
        viewBinding?.let {
            TransitionManager.beginDelayedTransition(it.cardTask)
            modeViews.forEach { view ->
                view.visibility = visibility
            }
            it.textModeSelector.text = text
        }
    }

    override fun processModeClick(modeType: ModeType) {
        viewBinding?.let {
            TransitionManager.beginDelayedTransition(it.cardTask)
            val color = resources.getColor(R.color.main_green)
            when (modeType) {
                ModeType.INTERVAL -> {
                    it.textModeInterval.setTextColor(color)
                    it.textStart.visibility = View.VISIBLE
                    it.timeStart.visibility = View.VISIBLE
                    it.textEnd.visibility = View.VISIBLE
                    it.timeEnd.visibility = View.VISIBLE
                }
                ModeType.TIMER -> {
                    it.textModeTimer.setTextColor(color)
                    it.textDuration.visibility = View.VISIBLE
                    it.timeTimer.visibility = View.VISIBLE
                }
                ModeType.PRECISE_TIME -> {
                    it.textModePrecise.setTextColor(color)
                    it.textPrecise.visibility = View.VISIBLE
                    it.timePrecise.visibility = View.VISIBLE
                }
            }
        }
    }


    override fun hideAllTimes() {
        viewBinding?.let {
            TransitionManager.beginDelayedTransition(it.cardTask)
            timeViews.forEach { view ->
                view.visibility = View.GONE
            }
            resources.getColor(R.color.main_green_light).let { color ->
                it.textModeInterval.setTextColor(color)
                it.textModeTimer.setTextColor(color)
                it.textModePrecise.setTextColor(color)
            }
        }
    }

    override fun drawTask(currentTask: Task) {
        viewBinding?.apply {
            timeStart.text = currentTask.timeStart?.getTimeFormatted()
            timeEnd.text = currentTask.timeEnd?.getTimeFormatted()
            timeTimer.text = currentTask.timerTime
            timePrecise.text = currentTask.timeStart?.getTimeFormatted()
            textDateSelected.text = currentTask.date?.getDateFormatted()
        }
    }

    override fun addItemToList(position: Int) {
        adapter?.notifyItemInserted(position)
        viewBinding?.editTaskName?.setText("")
    }

    override fun removeItem(pos: Int) {
        adapter?.notifyDataSetChanged()
    }

    override fun notifyItemChanged(selectedItemPos: Int) {
        adapter?.notifyItemChanged(selectedItemPos)
    }

    override fun showDatePicker(date: Calendar) {
        DatePickerDialog(
            requireContext(),
            { _, year, month, dayOfMonth ->
                date.set(Calendar.YEAR, year)
                date.set(Calendar.MONTH, month)
                date.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                presenter.setDate(date)
            },
            date.get(Calendar.YEAR),
            date.get(Calendar.MONTH),
            date.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    override fun showTimePicker(time: Calendar, timeTypes: TimeTypes) {
        val timePickerDialog = TimePickerDialog(
            requireContext(),
            { _, hourOfDay, minute ->
                time.set(Calendar.HOUR_OF_DAY, hourOfDay)
                time.set(Calendar.MINUTE, minute)
                presenter.setTime(time, timeTypes)
            },
            time[Calendar.HOUR_OF_DAY],
            time[Calendar.MINUTE],
            false
        )
        timePickerDialog.show()
    }

    override fun showTimerPicker() {
        val timePickerDialogFragment: TimePickerDialogFragment =
            TimePickerDialogFragment.newInstance()
        timePickerDialogFragment.isCancelable = true
        timePickerDialogFragment.setTargetFragment(this, 0)
        timePickerDialogFragment.show(parentFragmentManager, "fragment_time")
    }

    override fun onTimeSubmit(hours: Int, minutes: Int, seconds: Int) {
        presenter.timerSelected(hours, minutes, seconds)
    }

    override fun backPressed(): Boolean = presenter.backPressed()
}