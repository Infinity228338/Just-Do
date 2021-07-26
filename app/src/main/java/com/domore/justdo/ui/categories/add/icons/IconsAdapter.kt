package com.domore.justdo.ui.categories.add.icons

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.domore.justdo.R
import com.domore.justdo.data.vo.CategoryIcon
import com.domore.justdo.databinding.ItemIconBinding
import com.domore.justdo.ui.base.getResColor

class IconsAdapter : RecyclerView.Adapter<IconsAdapter.ViewHolder>() {

    private val iconsListPresenterImpl: IconsListPresenterImpl = IconsListPresenterImpl()
    var selectedItemPos = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemIconBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false

            )
        return ViewHolder(binding).apply {
            itemView.setOnClickListener { }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(iconsListPresenterImpl.icons[position])
    }

    override fun getItemCount(): Int = iconsListPresenterImpl.icons.size
    fun addIcons(icons: List<CategoryIcon>) {
        iconsListPresenterImpl.icons.addAll(icons)
    }

    class IconsListPresenterImpl : IconsListPresenter {
        var icons = mutableListOf<CategoryIcon>()
        override var itemClickListener: ((IconsItemView) -> Unit)? = null

        override fun getCount() = icons.size

        override fun bindView(view: IconsItemView) {
            view.bind(icons[view.pos])
        }

    }

    inner class ViewHolder(val binding: ItemIconBinding) : RecyclerView.ViewHolder(binding.root),
        IconsItemView {
        override fun bind(categoryIcon: CategoryIcon) {
            with(binding) {
                iconItem.apply {
                    setImageDrawable(AppCompatResources.getDrawable(
                        itemView.context,
                        categoryIcon.drawRes
                    )?.apply {
                        setTint(
                            if (selectedItemPos == layoutPosition) {
                                getResColor(itemView.context, R.color.main_green)
                            } else {
                                getResColor(itemView.context, R.color.black)
                            }
                        )
                    })
                    setOnClickListener { changeSelectedPosition() }
                }

            }
        }

        private fun changeSelectedPosition() {
            notifyItemChanged(selectedItemPos)
            selectedItemPos = layoutPosition
            notifyItemChanged(selectedItemPos)
        }

        override var pos: Int = -1
    }

    fun getSelected(): CategoryIcon? {
        return if (selectedItemPos != -1) {
            iconsListPresenterImpl.icons[selectedItemPos]
        } else null
    }
}