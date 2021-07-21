package com.domore.justdo.ui.categories.add.colors

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.domore.justdo.R
import com.domore.justdo.data.vo.CategoryColor
import com.domore.justdo.databinding.ColorItemLayoutBinding
import com.domore.justdo.ui.base.getResColor

class ColorAdapter(

) :
    RecyclerView.Adapter<ColorAdapter.ViewHolder>() {

    private val colorsListPresenterImpl: ColorsListPresenterImpl = ColorsListPresenterImpl()
    var selectedItemPos = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ColorItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false

            )
        return ViewHolder(binding).apply {
            itemView.setOnClickListener { }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(colorsListPresenterImpl.colors[position])
    }

    override fun getItemCount(): Int = colorsListPresenterImpl.colors.size
    fun addColors(colors: List<CategoryColor>) {
        colorsListPresenterImpl.colors.addAll(colors)
    }

    class ColorsListPresenterImpl : ColorsListPresenter {
        var colors = mutableListOf<CategoryColor>()
        override var itemClickListener: ((ColorItemView) -> Unit)? = null

        override fun getCount() = colors.size

        override fun bindView(view: ColorItemView) {
            view.bind(colors[view.pos])
        }

    }

    fun getSelected(): CategoryColor? {
        return if (selectedItemPos != -1) {
            colorsListPresenterImpl.colors[selectedItemPos]
        } else null
    }

    inner class ViewHolder(val binding: ColorItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root),
        ColorItemView {
        override fun bind(categoryColor: CategoryColor) {
            with(binding) {
                val drawable = AppCompatResources.getDrawable(
                    itemView.context,
                    R.drawable.circle_back
                )?.apply {
                    setTint(getResColor(itemView.context, categoryColor.colorRes))
                }
                if (selectedItemPos == layoutPosition)
                    colorItem.setImageResource(R.drawable.ic_baseline_check_24)
                else
                    colorItem.setImageDrawable(drawable)
            }
            itemView.setOnClickListener {
                notifyItemChanged(selectedItemPos)
                selectedItemPos = layoutPosition
                notifyItemChanged(selectedItemPos)
            }
        }

        override var pos: Int = -1

    }
}