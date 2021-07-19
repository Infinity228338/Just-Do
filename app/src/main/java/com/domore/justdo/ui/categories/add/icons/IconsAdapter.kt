package com.domore.justdo.ui.categories.add.icons

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.domore.justdo.data.vo.CategoryIcon
import com.domore.justdo.databinding.IconItemLayoutBinding

class IconsAdapter : RecyclerView.Adapter<IconsAdapter.ViewHolder>() {

    private val iconsListPresenterImpl: IconsListPresenterImpl = IconsListPresenterImpl()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            IconItemLayoutBinding.inflate(
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

    class ViewHolder(val binding: IconItemLayoutBinding) : RecyclerView.ViewHolder(binding.root),
        IconsItemView {
        override fun bind(icon: CategoryIcon) {
            with(binding) {

                val drawable = AppCompatResources.getDrawable(
                    itemView.context,
                    icon.drawRes
                )
                iconItem.setImageDrawable(drawable)
            }
        }

        override var pos: Int = -1
    }
}