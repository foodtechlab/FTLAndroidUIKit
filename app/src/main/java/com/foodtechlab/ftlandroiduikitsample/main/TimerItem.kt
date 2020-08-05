package com.foodtechlab.ftlandroiduikitsample.main

import com.foodtechlab.ftlandroiduikitsample.R
import com.foodtechlab.ftlandroiduikitsample.base.ItemBase

/**
 * Created by Umalt on 04.08.2020
 */

const val ITEM_TIMER = R.layout.rv_timers_item

data class TimerItem(
    val id: Int,
    override val itemViewType: Int = ITEM_TIMER
) : ItemBase {
    override fun getItemId() = id
}