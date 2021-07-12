package com.domore.justdo.ui.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.domore.justdo.R
import com.domore.justdo.databinding.FragmentCategoriesBinding
import com.domore.justdo.ui.base.BaseFragment

class CategoriesFragment : BaseFragment(R.layout.fragment_categories) {


    private var viewBinding: FragmentCategoriesBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        activity?.title = getString(R.string.my_productivity)
        viewBinding = FragmentCategoriesBinding.inflate(inflater, container, false)
        return viewBinding!!.root
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            CategoriesFragment()
    }
}