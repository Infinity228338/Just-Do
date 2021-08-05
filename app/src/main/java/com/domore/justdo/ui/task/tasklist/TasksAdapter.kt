package com.domore.justdo.ui.task.tasklist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.domore.justdo.data.vo.ModeType
import com.domore.justdo.data.vo.Task
import com.domore.justdo.databinding.ItemTaskBinding
import com.domore.justdo.ui.task.TaskItemView

class TasksAdapter(val presenter: TaskListPresenter) :
    RecyclerView.Adapter<TasksAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTaskBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding).apply {
            itemView.setOnClickListener { presenter.itemClickListener?.invoke(this) }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        presenter.bindView(holder.apply { pos = position })
    }

    override fun getItemCount(): Int = presenter.getCount()

    class ViewHolder(private val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root),
        TaskItemView {
        override fun bind(task: Task) {
            task.iconResId?.let { binding.categoryIcon.setImageResource(it) }
            task.name?.let { binding.taskName.text = it }
        }

        override fun itemClicked(task: Task) {

        }

        override fun editClicked() {
            TODO("Not yet implemented")
        }

        override fun editDoneClicked() {
            TODO("Not yet implemented")
        }

        override fun modeClicked(modeType: ModeType) {
            TODO("Not yet implemented")
        }

        override var pos: Int = -1

    }
}