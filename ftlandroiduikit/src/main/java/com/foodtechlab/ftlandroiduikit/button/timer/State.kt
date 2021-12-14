package com.foodtechlab.ftlandroiduikit.button.timer

import androidx.annotation.ColorRes
import com.foodtechlab.ftlandroiduikit.util.ThemeManager

/**
 * Created by Umalt on 16.06.2020
 */
enum class State(
    @ColorRes var textColor: Int? = null,
    @ColorRes var progressColor: Int? = null,
    @ColorRes var progressBgColor: Int? = null,
    @ColorRes var dotColor: Int? = null,
    @ColorRes var bounceDotColor: Int? = null
) {
    NEW,
    READY_FOR_DELIVERY,
    IN_DELIVERY
}