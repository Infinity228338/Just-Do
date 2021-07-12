package com.domore.justdo.ui.addTask

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.domore.justdo.R
import com.domore.justdo.databinding.FragmentAddTaskBinding
import com.domore.justdo.ui.base.BaseFragment

const val ARG_CATEGORY_ID = "category_id"

class AddTaskFragment : BaseFragment(R.layout.fragment_add_task) {

    private var viewBinding: FragmentAddTaskBinding? = null

    // TODO: Rename and change types of parameters
    private var categoryId: Long? = null

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
}