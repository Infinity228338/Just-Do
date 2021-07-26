package com.domore.justdo.ui.categories.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.domore.justdo.R
import com.domore.justdo.data.vo.Category
import com.domore.justdo.databinding.ItemCategoryBinding

class CategoriesAdapter(val presenter: CategoriesListPresenter) :
    RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoriesAdapter.CategoryViewHolder {
        val binding =
            ItemCategoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false

            )
        return CategoryViewHolder(binding).apply {
            itemView.setOnClickListener { presenter.itemClickListener?.invoke(this) }
        }

    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) =
        presenter.bindView(holder.apply { pos = position })


    override fun getItemCount(): Int = presenter.getCount()


    inner class CategoryViewHolder(val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root), CategoriesItemView {
        override fun bind(category: Category) {
            binding.categoryName.text =
                category.name ?: itemView.context.getString(category.nameRes)
            val iconRes =
                if (category.iconResId == 0) R.drawable.ic_icon_list else category.iconResId
            binding.categoryIcon.also {
                it.setImageDrawable(
                    AppCompatResources.getDrawable(
                        itemView.context,
                        iconRes
                    )
                )
                it.setOnClickListener {
                    presenter.itemClickListener?.invoke(this)
                }
            }
        }

        override var pos: Int = -1
    }
}