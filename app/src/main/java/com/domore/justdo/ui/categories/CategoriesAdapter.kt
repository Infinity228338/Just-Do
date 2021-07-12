package com.domore.justdo.ui.categories

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.domore.justdo.data.vo.Category
import com.domore.justdo.databinding.CategoryItemAddBinding
import com.domore.justdo.databinding.CategoryItemBinding
import com.domore.justdo.ui.categories.CategoriesListPresenter.Companion.TYPE_FOOTER
import com.domore.justdo.ui.categories.CategoriesListPresenter.Companion.TYPE_ITEM

class CategoriesAdapter(val presenter: CategoriesListPresenter) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val binding: ViewBinding
        if (viewType == TYPE_ITEM) {
            binding =
                CategoryItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false

                )
            return CategoryViewHolder(binding).apply {
                itemView.setOnClickListener { presenter.itemClickListener?.invoke(this) }
            }
        } else if (viewType == TYPE_FOOTER) {
            binding =
                CategoryItemAddBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false

                )
            return FooterHolder(binding).apply {
                itemView.setOnClickListener { presenter.itemClickListener?.invoke(this) }
            }
        }
        throw RuntimeException("There is no type that matches the type $viewType. Make sure you are using view types correctly!")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) =
        when (getItemViewType(position)) {
            TYPE_ITEM -> presenter.bindView((holder as CategoryViewHolder).apply { pos = position })
            TYPE_FOOTER -> presenter.bindView((holder as FooterHolder).apply { pos = position })
            else -> presenter.bindView((holder as CategoryViewHolder).apply { pos = position })
        }


    override fun getItemCount(): Int = presenter.getCount()

    override fun getItemViewType(position: Int): Int {
        return if (position == itemCount) {
            TYPE_FOOTER
        } else {
            TYPE_ITEM
        }
    }

    inner class CategoryViewHolder(val binding: CategoryItemBinding) :
        RecyclerView.ViewHolder(binding.root), CategoriesItemView {
        override fun bind(category: Category) {
            with(binding) {
                categoryName.text = category.name
            }
        }

        override var pos: Int = -1

    }


    inner class FooterHolder(val binding: CategoryItemAddBinding) :
        RecyclerView.ViewHolder(binding.root), CategoriesAddItemView {

        override fun bind(category: Category) {
            binding.categoryAdd.setOnClickListener {
//                addImageListener.invoke()
            }
        }

        override var pos: Int = -1
    }
}