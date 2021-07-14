package com.domore.justdo.ui.task.currenttask

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.domore.justdo.R
import com.domore.justdo.ui.base.BaseFragment


class CurrentTaskFragment : BaseFragment(R.layout.fragment_current_task) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_current_task, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            CurrentTaskFragment()
    }
}
