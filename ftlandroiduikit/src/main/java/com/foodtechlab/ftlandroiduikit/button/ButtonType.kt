package com.foodtechlab.ftlandroiduikit.button

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.FontRes
import com.foodtechlab.ftlandroiduikit.R

/**
 * Created by Umalt on 23.06.2020
 */
enum class ButtonType(
    @ColorRes var textColor: Int? = null,
    var textSize: Float,
    val isAllCaps: Boolean,
    @FontRes val font: Int,
    @ColorRes var dotColor: Int? = null,
    @ColorRes var bounceDotColor: Int? = null,
    @DrawableRes var background: Int? = null
) {
    PRIMARY(
        textSize = 14f,
        isAllCaps = true,
        font = R.font.roboto_medium
    ),
    SECONDARY(
        textSize = 14f,
        isAllCaps = true,
        font = R.font.roboto_medium
    ),
    CANCEL(
        textSize = 14f,
        isAllCaps = true,
        font = R.font.roboto_medium
    ),
    ADDITIONAL(
        textSize = 14f,
        isAllCaps = false,
        font = R.font.roboto_medium,
    )
}