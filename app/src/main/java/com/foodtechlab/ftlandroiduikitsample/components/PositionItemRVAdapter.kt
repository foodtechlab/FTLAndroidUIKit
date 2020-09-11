package com.foodtechlab.ftlandroiduikitsample.components

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.foodtechlab.ftlandroiduikitsample.R
import com.foodtechlab.ftlandroiduikitsample.base.BaseListAdapter
import com.foodtechlab.ftlandroiduikitsample.base.ItemBase
import com.foodtechlab.ftlandroiduikitsample.utils.Utils.getString
import kotlinx.android.synthetic.main.rv_position_item.view.*

class PositionItemRVAdapter :
    BaseListAdapter<ItemBase, PositionItemRVAdapter.PositionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PositionViewHolder {
        return PositionViewHolder(
            LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        )
    }

    override fun onBindViewHolder(holder: PositionViewHolder, position: Int) {
        holder.bind()
    }

    inner class PositionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind() {
            val item = getItem(adapterPosition) as ItemPosition
            with(itemView.trPosition) {
                textForStartColumn = item.title
                isLastRow = adapterPosition == itemCount.minus(1)
                textForCenterColumn = getString(R.string.common_count_positions, item.count)
                textForEndColumn = getString(R.string.common_price_format, item.price)
            }
        }
    }
}