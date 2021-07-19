package com.domore.justdo.ui.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.GridLayoutManager
import com.domore.justdo.R
import com.domore.justdo.databinding.FragmentCategoriesBinding
import com.domore.justdo.ui.base.BaseFragment
import com.domore.justdo.ui.categories.add.AddCategoryFragment
import com.domore.justdo.ui.categories.list.CategoriesAdapter
import moxy.ktx.moxyPresenter
import javax.inject.Inject

class CategoriesFragment : BaseFragment(R.layout.fragment_categories), CategoriesView {


    @Inject
    lateinit var categoriesPresenterFactory: CategoriesPresenterFactory
    private var viewBinding: FragmentCategoriesBinding? = null
    private val presenter: CategoriesPresenter by moxyPresenter {
        categoriesPresenterFactory.create()
    }
    private var adapter: CategoriesAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        activity?.title = ""
        viewBinding = FragmentCategoriesBinding.inflate(inflater, container, false)
        return viewBinding!!.root
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            CategoriesFragment()
    }

    override fun init() {
        adapter = CategoriesAdapter(presenter.categoryListPresenter)
        viewBinding?.rvCat?.also {
            it.layoutManager = GridLayoutManager(context, 4)
            it.adapter = adapter
        }
    }

    override fun updateList() {
        adapter?.notifyDataSetChanged()
    }

    override fun addItemToList(position: Int) {
        adapter?.notifyItemInserted(position)
    }

    override fun showDialog() {
        val fm: FragmentManager = parentFragmentManager
        val addCategoryDialogFragment: AddCategoryFragment =
            AddCategoryFragment.newInstance()
        addCategoryDialogFragment.show(fm, "fragment_add")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }
}