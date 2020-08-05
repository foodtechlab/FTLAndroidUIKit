package com.foodtechlab.ftlandroiduikitsample.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.foodtechlab.ftlandroiduikit.button.timer.State
import com.foodtechlab.ftlandroiduikitsample.base.BaseListAdapter
import kotlinx.android.synthetic.main.rv_timers_item.view.*

/**
 * Created by Umalt on 04.08.2020
 */
class TimersRVAdapter : BaseListAdapter<TimerItem, TimersRVAdapter.TimerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimerViewHolder {
        return TimerViewHolder(LayoutInflater.from(parent.context).inflate(viewType, parent, false))
    }

    override fun onBindViewHolder(holder: TimerViewHolder, position: Int) {
        holder.bind()
    }

    inner class TimerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val ftlTimerButton = itemView.ftlTimerButton

        fun bind() {
            with(ftlTimerButton) {
                timeZoneId = "Europe/Samara"
                estimateDuration = 60 * 60 * 1000
                estimateSuccessAt = "2020-08-04T14:30:00.001"
                updateState(State.IN_DELIVERY)
            }
        }
    }
}