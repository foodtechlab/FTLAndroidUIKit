package com.foodtechlab.ftlandroiduikit.button

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.FontRes
import com.foodtechlab.ftlandroiduikit.R

/**
 * Created by Umalt on 23.06.2020
 */
enum class ButtonType(
    @ColorRes val textColor: Int,
    val textSize: Float,
    val isAllCaps: Boolean,
    @FontRes val font: Int,
    @ColorRes val dotColor: Int,
    @ColorRes val bounceDotColor: Int,
    @DrawableRes val background: Int? = null
) {
    PRIMARY(
        R.color.BackgroundPrimary,
        14f,
        true,
        R.font.roboto_medium,
        R.color.BackgroundSecondaryOpacity60,
        R.color.BackgroundSecondary,
        R.drawable.selector_ftl_primary_button
    ),
    SECONDARY(
        R.color.BackgroundPrimary,
        14f,
        true,
        R.font.roboto_medium,
        R.color.BackgroundSecondaryOpacity60,
        R.color.BackgroundSecondary,
        R.drawable.selector_ftl_button_secondary
    ),
    CANCEL(
        R.color.selector_ftl_button_cancel,
        14f,
        true,
        R.font.roboto_medium,
        R.color.PrimaryDangerEnabledOpacity60,
        R.color.PrimaryDangerEnabled,
        R.drawable.selector_ftl_button_cancel
    ),
    ADDITIONAL(
        R.color.selector_ftl_button_additional,
        14f,
        false,
        R.font.roboto_regular,
        R.color.PrimaryInfoEnabledOpacity60,
        R.color.PrimaryInfoEnabled
    )
}