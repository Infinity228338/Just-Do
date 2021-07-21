package com.domore.justdo.ui.categories.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.domore.justdo.data.vo.Category
import com.domore.justdo.databinding.CategoryItemBinding

class CategoriesAdapter(val presenter: CategoriesListPresenter) :
    RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoriesAdapter.CategoryViewHolder {
        val binding =
            CategoryItemBinding.inflate(
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


    inner class CategoryViewHolder(val binding: CategoryItemBinding) :
        RecyclerView.ViewHolder(binding.root), CategoriesItemView {
        override fun bind(category: Category) {
            binding.categoryName.text = itemView.context.getString(category.nameRes)
            binding.categotyIcon.also {
                it.setImageDrawable(
                    AppCompatResources.getDrawable(
                        itemView.context,
                        category.iconResId
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