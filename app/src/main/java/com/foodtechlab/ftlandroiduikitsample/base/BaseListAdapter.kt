package com.foodtechlab.ftlandroiduikitsample.base

import android.annotation.SuppressLint
import android.view.View
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Umalt on 04.08.2020
 */
abstract class BaseListAdapter<D : ItemBase, VH : RecyclerView.ViewHolder>
@JvmOverloads
constructor(differ: DiffUtil.ItemCallback<D> = DiffCallback()) : ListAdapter<D, VH>(differ) {

    var listener: BaseClickListener? = null

    fun update(list: List<D>) {
        this.submitList(ArrayList(list))
    }

    @SuppressLint("DiffUtilEquals")
    private class DiffCallback<D : ItemBase> : DiffUtil.ItemCallback<D>() {

        override fun areContentsTheSame(oldItem: D, newItem: D): Boolean =
            oldItem == newItem

        override fun areItemsTheSame(oldItem: D, newItem: D): Boolean =
            oldItem.getItemId() == newItem.getItemId()
    }

    override fun getItemViewType(position: Int) = getItem(position).itemViewType
}

interface DiffComparable {
    fun getItemId(): Int
}

interface BaseClickListener {
    fun onClick(view: View, position: Int)
}