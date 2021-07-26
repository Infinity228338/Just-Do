package com.domore.justdo.ui.stats

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.domore.justdo.R
import com.domore.justdo.databinding.FragmentStatisticsBinding
import com.domore.justdo.ui.base.BaseFragment

class StatisticsFragment : BaseFragment(R.layout.fragment_statistics) {


    private var viewBinding: FragmentStatisticsBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        activity?.title = getString(R.string.my_productivity)
        viewBinding = FragmentStatisticsBinding.inflate(inflater, container, false)
        return viewBinding!!.root
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            StatisticsFragment()
    }
}