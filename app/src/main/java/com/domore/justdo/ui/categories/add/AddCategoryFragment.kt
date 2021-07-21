package com.domore.justdo.ui.categories.add

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.annotation.Nullable
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.GridLayoutManager
import com.domore.justdo.data.categorycolor.repository.CategoryColorRepository
import com.domore.justdo.data.categoryicon.repository.CategoryIconRepository
import com.domore.justdo.data.vo.CategoryColor
import com.domore.justdo.data.vo.CategoryIcon
import com.domore.justdo.databinding.FragmentAddCategoryBinding
import com.domore.justdo.ui.categories.add.colors.ColorAdapter
import com.domore.justdo.ui.categories.add.icons.IconsAdapter
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import javax.inject.Inject


class AddCategoryFragment : DialogFragment(), HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    @Inject
    lateinit var colorRepository: CategoryColorRepository

    @Inject
    lateinit var iconRepository: CategoryIconRepository

    private var callback: OnCategoryAddedListener? = null
    private var colorAdapter: ColorAdapter? = null
    private var iconAdapter: IconsAdapter? = null
    private var viewBinding: FragmentAddCategoryBinding? = null

    interface OnCategoryAddedListener {
        fun onCategorySubmit(name: String, colorRes: Int, drawRes: Int)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        callback = try {
            targetFragment as OnCategoryAddedListener?
        } catch (e: Exception) {
            throw ClassCastException("Calling Fragment must implement OnAddFriendListener")
        }
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun androidInjector(): AndroidInjector<Any> {
        return androidInjector
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        colorAdapter = ColorAdapter()
        iconAdapter = IconsAdapter()
        viewBinding = FragmentAddCategoryBinding.inflate(inflater, container, false)
        viewBinding?.apply {
            rvColorPicker.also {
                it.layoutManager = GridLayoutManager(context, 7)
                it.adapter = colorAdapter
                colorRepository
                    .getColors()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(::addColors)
            }
            rvIconPicker.also {
                it.layoutManager = GridLayoutManager(context, 7)
                it.adapter = iconAdapter
                iconRepository
                    .getIcons()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(::addIcons)
            }
        }
        return viewBinding!!.root
    }

    override fun onViewCreated(view: View, @Nullable savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog!!.window!!.setSoftInputMode(
            WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE
        )

        viewBinding!!.buttonAdd.setOnClickListener {
            viewBinding?.let {
                if (it.categoryName.text.toString().isNotEmpty()) {
                    callback?.onCategorySubmit(
                        it.categoryName.text.toString(),
                        colorAdapter?.getSelected()?.colorRes ?: 0,
                        iconAdapter?.getSelected()?.drawRes ?: 0
                    )
                    this.dismiss()
                }
            }
        }
    }

    private fun addColors(colors: List<CategoryColor>) {
        colorAdapter?.apply {
            addColors(colors)
            notifyDataSetChanged()
        }
    }

    private fun addIcons(icons: List<CategoryIcon>) {
        iconAdapter?.apply {
            addIcons(icons)
            notifyDataSetChanged()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            AddCategoryFragment()
    }
}
