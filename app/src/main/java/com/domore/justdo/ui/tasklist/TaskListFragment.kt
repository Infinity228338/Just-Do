package com.domore.justdo.ui.tasklist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.domore.justdo.R
import com.domore.justdo.databinding.FragmentTaskListBinding
import com.domore.justdo.ui.base.BaseFragment

class TaskListFragment : BaseFragment(R.layout.fragment_task_list) {

    private var viewBinding: FragmentTaskListBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        activity?.title = getString(R.string.current_tasks)
        viewBinding = FragmentTaskListBinding.inflate(inflater, container, false)
        return viewBinding!!.root
    }

    companion object {
        fun newInstance() =
            TaskListFragment()
    }
}
