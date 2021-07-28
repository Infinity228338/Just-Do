package com.domore.justdo.ui.task.tasklist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.domore.justdo.R
import com.domore.justdo.databinding.FragmentTaskListBinding
import com.domore.justdo.ui.base.BackButtonListener
import com.domore.justdo.ui.base.BaseFragment
import moxy.ktx.moxyPresenter
import javax.inject.Inject

class TasksFragment : BaseFragment(R.layout.fragment_task_list), TaskListView,
    BackButtonListener {

    @Inject
    lateinit var taskListPresenterFactory: TaskListPresenterFactory
    private var viewBinding: FragmentTaskListBinding? = null
    private val presenter: TasksPresenter by moxyPresenter {
        taskListPresenterFactory.create()
    }
    private var adapter: TasksAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        activity?.title = getString(R.string.current_tasks)
        adapter = TasksAdapter(presenter.taskListPresenter)
        viewBinding = FragmentTaskListBinding.inflate(inflater, container, false)
        viewBinding?.apply {
            rvTasks.also {
                it.layoutManager = LinearLayoutManager(context)
                it.adapter = adapter
            }
        }
        return viewBinding!!.root
    }

    override fun init() {

    }

    override fun updateList() {
        adapter?.notifyDataSetChanged()
    }

    override fun hidetext() {
        viewBinding?.let {
            it.noTasksTv.visibility = View.GONE
            it.addTaskTv.visibility = View.GONE
        }
    }

    companion object {
        fun newInstance() =
            TasksFragment()
    }

    override fun backPressed(): Boolean = presenter.backPressed()


}
