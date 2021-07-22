package com.domore.justdo.ui.task.addTask

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.transition.TransitionManager
import com.domore.justdo.R
import com.domore.justdo.databinding.FragmentAddTaskBinding
import com.domore.justdo.ui.base.BackButtonListener
import com.domore.justdo.ui.base.BaseFragment
import com.domore.justdo.ui.base.getResColor
import com.domore.justdo.ui.base.getVisibility
import moxy.ktx.moxyPresenter
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


const val ARG_CATEGORY_ID = "category_id"

class AddTaskFragment : BaseFragment(R.layout.fragment_add_task), AddTaskView, BackButtonListener {

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
            cardTaskMode.setOnClickListener {
                presenter.cardTaskModeClicked()
            }
            textInterval.setOnClickListener {
                presenter.modeClicked()
            }
            cardDate.setOnClickListener {
                presenter.dateClicked()
            }
            val today = Calendar.getInstance().time
            val formatter = SimpleDateFormat(
                "hh:mm:ss",
                Locale.getDefault()
            )

            val date: String = formatter.format(today)
            timeStart.apply {
                text = date
                setOnClickListener { presenter.timeStartClicked() }
            }
            timeEnd.apply {
                text = date
                setOnClickListener { presenter.timeEndClicked()}
            }
        }
    }

    override fun showOrHideModes(shown: Boolean) {
        val visibility = getVisibility(shown)
        val text =
            requireContext()
                .getString(if (shown) R.string.choose_mode else R.string.mode_and_time)
        val color =
            getResColor(requireContext(), if (shown) R.color.white else R.color.ultra_light_grey)
        viewBinding?.let {
            TransitionManager.beginDelayedTransition(it.cardTaskMode)
            it.textInterval.visibility = visibility
            it.textTimer.visibility = visibility
            it.textPreciseTime.visibility = visibility
            it.textModeTime.text = text
            it.cardTaskMode.setCardBackgroundColor(color)
        }
    }

    override fun showOrHideIntervalTime(shown: Boolean) {
        val visibility = getVisibility(shown)
        viewBinding?.let {
            TransitionManager.beginDelayedTransition(it.cardTaskMode)
            it.textStart.visibility = visibility
            it.timeStart.visibility = visibility
            it.textEnd.visibility = visibility
            it.timeEnd.visibility = visibility
        }
    }

    override fun addItemToList(position: Int) {
        TODO("Not yet implemented")
    }

    override fun showDatePicker() {
        val dateAndTime = Calendar.getInstance()
        DatePickerDialog(
            requireContext(),
            { view, year, month, dayOfMonth ->
                dateAndTime.set(Calendar.YEAR, year)
                dateAndTime.set(Calendar.MONTH, month)
                dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                presenter.setDate(dateAndTime)
            },
            dateAndTime.get(Calendar.YEAR),
            dateAndTime.get(Calendar.MONTH),
            dateAndTime.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    override fun backPressed(): Boolean = presenter.backPressed()
}