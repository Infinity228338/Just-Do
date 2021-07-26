package com.domore.justdo.ui.task.addTask

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.transition.TransitionManager
import com.domore.justdo.R
import com.domore.justdo.data.vo.ModeType
import com.domore.justdo.data.vo.TimeTypes
import com.domore.justdo.databinding.FragmentAddTaskBinding
import com.domore.justdo.ui.base.*
import com.domore.justdo.ui.task.addTask.timepicker.TimePickerDialogFragment
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
        viewBinding = FragmentAddTaskBinding.inflate(inflater, container, false)
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
            cardTaskName.setOnClickListener {
                presenter.cardTaskNameClicked()
            }
            editTaskName.apply {
                setOnFocusChangeListener { _, hasFocus ->
                    if (hasFocus)
                        presenter.cardTaskNameClicked()
                    else {
                        hideKeyboard()
                    }
                }
                setOnClickListener {
                    presenter.cardTaskNameClicked()
                }

            }

            cardTaskMode.setOnClickListener {
                presenter.cardTaskModeClicked()
            }
            textInterval.setOnClickListener {
                presenter.modeClicked(ModeType.INTERVAL)
            }
            textTimer.setOnClickListener {
                presenter.modeClicked(ModeType.TIMER)
            }
            textPreciseTime.setOnClickListener {
                presenter.modeClicked(ModeType.PRECISE_TIME)
            }
            cardTaskDate.setOnClickListener {
                presenter.dateClicked()
            }
            timeStart.setOnClickListener {
                presenter.timeStartClicked()
            }
            timeEnd.setOnClickListener {
                presenter.timeEndClicked()
            }
            timeDuration.setOnClickListener {
                presenter.timeDurationClicked()
            }

            timePrecise.setOnClickListener {
                presenter.timePreciseClicked()
            }
            textCancel.setOnClickListener {
                presenter.backPressed()
            }

            textOk.setOnClickListener {
                presenter.okClicked(editTaskName.text.toString())
            }
        }
    }

    override fun processModeClick(modeType: ModeType, formatted: String) {
        viewBinding?.let {
            TransitionManager.beginDelayedTransition(it.cardTaskMode)
            val color = resources.getColor(R.color.main_green)
            when (modeType) {
                ModeType.INTERVAL -> {
                    it.textInterval.setTextColor(color)
                    it.textStart.visibility = View.VISIBLE
                    it.timeStart.visibility = View.VISIBLE
                    it.timeStart.text = formatted
                    it.textEnd.visibility = View.VISIBLE
                    it.timeEnd.visibility = View.VISIBLE
                    it.timeEnd.text = formatted
                }
                ModeType.TIMER -> {
                    it.textTimer.setTextColor(color)
                    it.textDuration.visibility = View.VISIBLE
                    it.timeDuration.visibility = View.VISIBLE
                    it.timeDuration.text = formatted
                }
                ModeType.PRECISE_TIME -> {
                    it.textPreciseTime.setTextColor(color)
                    it.textPrecise.visibility = View.VISIBLE
                    it.timePrecise.visibility = View.VISIBLE
                    it.timePrecise.text = formatted
                }
            }
        }
    }

    override fun hideAllTimes() {
        viewBinding?.let {
            TransitionManager.beginDelayedTransition(it.cardTaskMode)
            it.textStart.visibility = View.GONE
            it.timeStart.visibility = View.GONE
            it.textEnd.visibility = View.GONE
            it.timeEnd.visibility = View.GONE
            it.textDuration.visibility = View.GONE
            it.timeDuration.visibility = View.GONE
            it.textPrecise.visibility = View.GONE
            it.timePrecise.visibility = View.GONE
            resources.getColor(R.color.main_green_light).let { color ->
                it.textInterval.setTextColor(color)
                it.textTimer.setTextColor(color)
                it.textPreciseTime.setTextColor(color)
            }
        }
    }

    override fun showOrHideModes(shown: Boolean) {
        val visibility = getVisibility(shown)
        val text =
            requireContext()
                .getString(if (shown) R.string.choose_mode else R.string.mode_and_time)
        val color =
            getResColor(
                requireContext(),
                if (shown) R.color.white else R.color.ultra_light_grey
            )
        viewBinding?.let {
//            TransitionManager.beginDelayedTransition(it.cardTaskMode)
            it.textInterval.visibility = visibility
            it.textTimer.visibility = visibility
            it.textPreciseTime.visibility = visibility
            it.textModeTime.text = text
            it.cardTaskMode.setCardBackgroundColor(color)
        }
    }

    override fun processNameCardClick(shown: Boolean) {
        val color =
            getResColor(
                requireContext(),
                if (shown) R.color.white else R.color.ultra_light_grey
            )
        val addIconRes = if (shown) R.drawable.ic_icon_check else R.drawable.ic_add_inactive
        val itemsIconRes = if (shown) R.drawable.ic_icon_name else R.drawable.ic_icon_list
        viewBinding?.apply {
            TransitionManager.beginDelayedTransition(addTaskView)
            cardTaskName.setCardBackgroundColor(color)
            addIcon.setImageResource(addIconRes)
            itemsIcon.setImageResource(itemsIconRes)
            cardTaskMode.visibility = View.VISIBLE
            cardTaskDate.visibility = View.VISIBLE
            textCancel.visibility = View.VISIBLE
            textOk.visibility = View.VISIBLE
            editTaskName.also {
                if (!shown) hideKeyboard()
                it.isCursorVisible = shown
            }
        }
    }


    override fun addItemToList(position: Int) {
        TODO("Not yet implemented")
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

    override fun setTimeStartText(formattedTime: String) {
        viewBinding?.timeStart?.text = formattedTime
    }

    override fun setTimeEndText(formattedTime: String) {
        viewBinding?.timeEnd?.text = formattedTime
    }

    override fun setTimerText(formattedTime: String) {
        viewBinding?.timeDuration?.text = formattedTime
    }

    override fun setDate(dateFormatted: String) {
        viewBinding?.textDateSelected?.text = dateFormatted
    }

    override fun setPreciseText(formattedTime: String) {
        viewBinding?.timePrecise?.text = formattedTime
    }

    override fun backPressed(): Boolean = presenter.backPressed()

}