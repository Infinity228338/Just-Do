package com.domore.justdo.ui.task.addTask

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.domore.justdo.data.vo.Task
import com.domore.justdo.databinding.ItemTaskOnelineBinding
import com.domore.justdo.ui.task.listbase.TaskItemView
import com.domore.justdo.ui.task.listbase.TaskListPresenter

class AddedTasksAdapter(val presenter: TaskListPresenter) :
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
        presenter.bindView(holder.apply { pos = position })
    }

    override fun getItemCount(): Int = presenter.getCount()


    class ViewHolder(val binding: ItemTaskOnelineBinding) : RecyclerView.ViewHolder(binding.root),
        TaskItemView {
        override fun bind(task: Task) {
            binding.taskName.text = task.name
        }

        override var pos: Int = -1

    }
}