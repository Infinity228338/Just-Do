package com.domore.justdo.ui.task.addTask

import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.domore.justdo.R
import com.domore.justdo.data.vo.Task
import com.domore.justdo.data.vo.TimeTypes
import com.domore.justdo.databinding.ItemTaskOnelineBinding
import com.domore.justdo.ui.task.TaskItemView

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
            binding.apply {
                taskName.setOnClickListener {
                    presenter.notifyItemChanged(presenter.selectedItemPos)
                    presenter.selectedItemPos = layoutPosition
                    presenter.notifyItemChanged(presenter.selectedItemPos)
                }
                editIcon.setOnClickListener {
//                    expandEditCard()
                    val int = 1
                }
                deleteIcon.setOnClickListener {
                    presenter.deleteIconClick(pos)
                }
                listOf(textStart, timeStart).forEach {
                    it.setOnClickListener {
                        presenter.timeClicked(TimeTypes.INTERVAL_START)
                    }
                }
                listOf(textEnd, timeEnd).forEach {
                    it.setOnClickListener {
                        presenter.timeClicked(TimeTypes.INTERVAL_END)
                    }
                }
                listOf(textDuration, timeTimer).forEach {
                    it.setOnClickListener {
                        presenter.timeClicked(TimeTypes.TIMER)
                    }
                }
                listOf(textPrecise, timePrecise).forEach {
                    it.setOnClickListener {
                        presenter.timeClicked(TimeTypes.PRECISE_TIME)
                    }
                }

                listOf(editDoneIcon).forEach {
                    it.setOnClickListener {
                        presenter.modifyClicked(taskName.text.toString())
//                        collapseEditCard()

                    }
                }

                listOf(timeIcon, textModeSelector).forEach {
                    it.setOnClickListener {
//                        val visibility = getVisibility(true)
//                        val text =
//                            itemView.context
//                                .getString(if (true) R.string.choose_mode else R.string.mode_and_time)
//                        TransitionManager.beginDelayedTransition(binding.cardTask)
//                        modeViews.forEach { view ->
//                            view.visibility = visibility
//                        }
//                        binding.textModeSelector.text = text
                    }
                }
                listOf(calendarIcon, textDate, textDateSelected).forEach {
                    it.setOnClickListener { presenter.dateClicked() }
                }
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
        )

        private val modeViews = listOf(
            binding.textModeInterval, binding.textModeTimer, binding.textModePrecise
        )
        private val isCurrent: Boolean
            get() = presenter.selectedItemPos == layoutPosition

        private val itemVisibility: Int
            get() {
                return if (isCurrent) {
                    View.VISIBLE
                } else {
                    View.GONE
                }
            }

        private val expanded: Boolean
            get() = binding.editDoneIcon.visibility == View.VISIBLE

        override fun bind(task: Task) {
            if (!isCurrent && expanded)
                collapseEditCard()
            binding.taskName.setText(task.name)
            task.iconResId?.let { binding.taskIcon.setImageResource(it) }
            val back = AppCompatResources.getDrawable(
                itemView.context, if (isCurrent)
                    R.drawable.background_rounded_white
                else R.drawable.background_rounded_ultragrey
            )
            binding.root.background = back
            binding.editIcon.visibility = itemVisibility
            binding.deleteIcon.visibility = itemVisibility
        }

        override fun itemClicked(task: Task) {
            if (presenter.selectedItemPos != layoutPosition) {
                presenter.notifyItemChanged(presenter.selectedItemPos)
                presenter.selectedItemPos = layoutPosition
                presenter.notifyItemChanged(presenter.selectedItemPos)
            }
        }

        private fun expandEditCard() {
            editingViews.forEach {
                it.visibility = View.VISIBLE
            }
            listOf(binding.editIcon, binding.deleteIcon).forEach {
                it.visibility = View.GONE
            }
            binding.taskName.inputType = InputType.TYPE_CLASS_TEXT
        }

        private fun collapseEditCard() {
            editingViews.forEach {
                it.visibility = View.GONE
            }
            if (isCurrent)
                listOf(binding.editIcon, binding.deleteIcon).forEach {
                    it.visibility = View.VISIBLE
                }
            binding.taskName.inputType = InputType.TYPE_NULL

        }

        override var pos: Int = -1
    }
}