package com.domore.justdo.ui.task.addTask

import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.domore.justdo.R
import com.domore.justdo.data.vo.Task
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
            itemView.setOnClickListener { presenter.itemClickListener?.invoke(this) }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        presenter.bindView(holder
            .apply { pos = position }
        )
    }

    override fun getItemCount(): Int = presenter.getCount()


    inner class ViewHolder(val binding: ItemTaskOnelineBinding) :
        RecyclerView.ViewHolder(binding.root),
        TaskItemView {

        val editingViews = listOf(
            binding.timeIcon,
            binding.textModeTime,
            binding.textInterval,
            binding.textTimer,
            binding.textPreciseTime,
            binding.barrier,
            binding.calendarIcon,
            binding.textDate,
        )

        override fun bind(task: Task) {
            val current = presenter.selectedItemPos == layoutPosition
            val visibility = getVisibility(current)
            hideEditable()
            binding.taskName.setText(task.name)
            binding.editIcon.setOnClickListener {
                showEditable()
            }
            binding.deleteIcon.setOnClickListener {
                presenter.deleteIconClick(pos)
            }
            task.iconResId?.let { binding.taskIcon.setImageResource(it) }
            val back = AppCompatResources.getDrawable(
                itemView.context, if (current)
                    R.drawable.background_rounded_white
                else R.drawable.background_rounded_ultragrey
            )
            binding.root.background = back
            binding.editIcon.visibility = visibility
            binding.deleteIcon.visibility = visibility
            itemView.setOnClickListener {
                presenter.notifyItemChanged(presenter.selectedItemPos)
                presenter.selectedItemPos = layoutPosition
                presenter.notifyItemChanged(presenter.selectedItemPos)
            }
            binding.taskName.setOnClickListener {
                presenter.notifyItemChanged(presenter.selectedItemPos)
                presenter.selectedItemPos = layoutPosition
                presenter.notifyItemChanged(presenter.selectedItemPos)
            }
        }

        private fun showEditable() {
            editingViews.forEach {
                it.visibility = View.VISIBLE
            }
            binding.taskName.inputType = InputType.TYPE_CLASS_TEXT
        }

        private fun hideEditable() {
            editingViews.forEach {
                it.visibility = View.GONE
            }
            binding.taskName.inputType = InputType.TYPE_NULL

        }

        override var pos: Int = -1
    }

    private fun getVisibility(current: Boolean): Int {
        return if (current) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }
}