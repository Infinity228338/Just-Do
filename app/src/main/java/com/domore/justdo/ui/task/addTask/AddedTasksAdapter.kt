package com.domore.justdo.ui.task.addTask

import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionManager
import com.domore.justdo.R
import com.domore.justdo.data.vo.ModeType
import com.domore.justdo.data.vo.Task
import com.domore.justdo.data.vo.TimeTypes
import com.domore.justdo.databinding.ItemTaskOnelineBinding
import com.domore.justdo.ui.task.TaskItemView
import com.domore.justdo.util.getDateFormatted
import com.domore.justdo.util.getTimeFormatted

class AddedTasksAdapter(val presenter: AddedTasksListPresenter) :
    RecyclerView.Adapter<AddedTasksAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ItemTaskOnelineBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding).apply {
            itemView.setOnClickListener {
                presenter.itemClickListener?.invoke(this)
            }
            binding.taskName.setOnClickListener {
                presenter.itemClickListener?.invoke(this)
            }
            binding.editIcon.setOnClickListener {
                presenter.editClickListener?.invoke(this)
            }
            binding.editDoneIcon.setOnClickListener {
                presenter.editDoneClickListener?.invoke(this)
            }
            binding.deleteIcon.setOnClickListener {
                presenter.deleteClickListener?.invoke(this)
            }
            binding.textModeInterval.setOnClickListener {
                presenter.selectedModeChangedListener?.invoke(this, ModeType.INTERVAL)
            }
            binding.textModeTimer.setOnClickListener {
                presenter.selectedModeChangedListener?.invoke(this, ModeType.TIMER)

            }
            binding.textModePrecise.setOnClickListener {
                presenter.selectedModeChangedListener?.invoke(this, ModeType.PRECISE_TIME)
            }
            listOf(binding.textStart, binding.timeStart).forEach {
                it.setOnClickListener {
                    presenter.timeClicked(TimeTypes.INTERVAL_START)
                }
            }
            listOf(binding.textEnd, binding.timeEnd).forEach {
                it.setOnClickListener {
                    presenter.timeClicked(TimeTypes.INTERVAL_END)
                }
            }
            listOf(binding.textDuration, binding.timeTimer).forEach {
                it.setOnClickListener {
                    presenter.timeClicked(TimeTypes.TIMER)
                }
            }
            listOf(binding.textPrecise, binding.timePrecise).forEach {
                it.setOnClickListener {
                    presenter.timeClicked(TimeTypes.PRECISE_TIME)
                }
            }

            listOf(binding.calendarIcon, binding.textDate, binding.textDateSelected).forEach {
                it.setOnClickListener { presenter.dateClicked() }
            }
        }
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        presenter.bindView(holder
            .apply {
                pos = position
            }
        )
    }

    override fun getItemCount(): Int = presenter.getCount()


    inner class ViewHolder(val binding: ItemTaskOnelineBinding) :
        RecyclerView.ViewHolder(binding.root),
        TaskItemView {

        private val editingViews = listOf(
            binding.timeIcon,
            binding.editDoneIcon,
            binding.textModeSelector,
            binding.textModeInterval,
            binding.textModeTimer,
            binding.textModePrecise,
            binding.barrier,
            binding.calendarIcon,
            binding.textDate,
            binding.textDateSelected
        )

        private val timeViews = listOf(
            binding.textStart,
            binding.timeStart,
            binding.textEnd,
            binding.timeEnd,
            binding.textDuration,
            binding.timeTimer,
            binding.textPrecise,
            binding.timePrecise
        )
        private val isCurrent: Boolean
            get() = presenter.selectedItemPos == layoutPosition

        private val itemVisibility: Int
            get() {
                return if (isCurrent && !binding.editDoneIcon.isVisible) {
                    View.VISIBLE
                } else {
                    View.GONE
                }
            }

        private var modeType: ModeType = ModeType.INTERVAL

        override fun bind(task: Task) {
            if (!isCurrent)
                collapseEditCard()
            task.iconResId?.let { binding.taskIcon.setImageResource(it) }
            val back = AppCompatResources.getDrawable(
                itemView.context, if (isCurrent)
                    R.drawable.background_rounded_white
                else R.drawable.background_rounded_ultragrey
            )
            binding.apply {
                taskName.setText(task.name)
                timeStart.text = task.timeStart?.getTimeFormatted()
                timeEnd.text = task.timeEnd?.getTimeFormatted()
                timeTimer.text = task.timerTime
                timePrecise.text = task.timeStart?.getTimeFormatted()
                textDateSelected.text = task.date?.getDateFormatted()
                root.background = back
                editIcon.visibility = itemVisibility
                deleteIcon.visibility = itemVisibility
                modeType = task.mode?.let { ModeType.valueOf(it) } ?: ModeType.INTERVAL
                taskTime.text = when (modeType) {
                    ModeType.INTERVAL -> (task.timeStart?.getTimeFormatted() ?: "") +
                            "-" +
                            (task.timeEnd?.getTimeFormatted() ?: "")
                    ModeType.TIMER -> task.timerTime ?: ""
                    ModeType.PRECISE_TIME -> task.timeStart?.getTimeFormatted() ?: ""
                }

            }
        }

        override fun itemClicked(task: Task) {
            if (presenter.selectedItemPos != layoutPosition) {
                presenter.notifyItemChanged(presenter.selectedItemPos)
                presenter.selectedItemPos = layoutPosition
                presenter.notifyItemChanged(presenter.selectedItemPos)
            }
        }

        override fun editClicked() {
            expandEditCard()
        }

        override fun editDoneClicked() {
            collapseEditCard()
        }

        override fun modeClicked(modeType: ModeType) {
            hideAllTimes()
            processModeClick(modeType)
        }

        private fun processModeClick(modeType: ModeType) {
            binding.let {
                TransitionManager.beginDelayedTransition(it.cardTask)
                val color = itemView.context.resources.getColor(R.color.main_green)
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


        private fun hideAllTimes() {
            binding.let {
                TransitionManager.beginDelayedTransition(it.cardTask)
                timeViews.forEach { view ->
                    view.visibility = View.GONE
                }
                itemView.context.resources.getColor(R.color.main_green_light).let { color ->
                    it.textModeInterval.setTextColor(color)
                    it.textModeTimer.setTextColor(color)
                    it.textModePrecise.setTextColor(color)
                }
            }
        }

        private fun expandEditCard() {
            TransitionManager.beginDelayedTransition(binding.root)
            editingViews.forEach {
                it.visibility = View.VISIBLE
            }
            listOf(binding.editIcon, binding.deleteIcon).forEach {
                it.visibility = View.GONE
            }
            binding.taskName.inputType = InputType.TYPE_CLASS_TEXT
            modeClicked(modeType)
        }

        private fun collapseEditCard() {
            TransitionManager.beginDelayedTransition(binding.root)
            (editingViews + timeViews + binding.editDoneIcon).forEach {
                it.visibility = View.GONE
            }
            if (isCurrent) {
                listOf(binding.editIcon, binding.deleteIcon).forEach {
                    it.visibility = View.VISIBLE
                }
                binding.taskName.inputType = InputType.TYPE_NULL
            }

        }

        override var pos: Int = -1
    }
}