package com.foodtechlab.ftlandroiduikitsample.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.foodtechlab.ftlandroiduikitsample.base.BaseListAdapter
import kotlinx.android.synthetic.main.rv_timers_item.view.*

/**
 * Created by Umalt on 04.08.2020
 */
class TimersRVAdapter :
    BaseListAdapter<TimerItem, TimersRVAdapter.TimerViewHolder>(TimerDiffUtilItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimerViewHolder {
        return TimerViewHolder(LayoutInflater.from(parent.context).inflate(viewType, parent, false))
    }

    override fun onBindViewHolder(holder: TimerViewHolder, position: Int) {
        holder.bind()
    }

    override fun onBindViewHolder(
        holder: TimerViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            holder.bind()
        }
    }

    inner class TimerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val ftlTimerButton = itemView.ftlTimerButton

        fun bind() {
            val item = getItem(adapterPosition)

            with(ftlTimerButton) {
                timeZoneId = "Europe/Samara"
                estimateSuccessAt = "2020-08-06T20:00:00.001"
                estimateDuration = 60 * 60 * 1000
                updateRemainedDuration(item.remainedDuration)
            }

            ftlTimerButton.setOnClickListener(object : View.OnClickListener {
                var i = 0
                override fun onClick(v: View) {
                    ftlTimerButton.updateDotProgressVisibility(i++ % 2 == 0)
                }
            })
        }
    }
}

private class TimerDiffUtilItemCallback : DiffUtil.ItemCallback<TimerItem>() {
    override fun areContentsTheSame(oldItem: TimerItem, newItem: TimerItem): Boolean {
        return oldItem == newItem
    }

    override fun areItemsTheSame(oldItem: TimerItem, newItem: TimerItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun getChangePayload(oldItem: TimerItem, newItem: TimerItem): Any? {
        return oldItem.remainedDuration != newItem.remainedDuration
    }
}