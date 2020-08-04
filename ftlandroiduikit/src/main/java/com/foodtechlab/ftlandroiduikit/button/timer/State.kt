package com.foodtechlab.ftlandroiduikit.button.timer

import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import com.foodtechlab.ftlandroiduikit.R

/**
 * Created by Umalt on 16.06.2020
 */
enum class State(
    @StringRes val text: Int,
    @ColorRes val textColor: Int,
    @ColorRes val progressColor: Int,
    @ColorRes val progressBgColor: Int,
    @ColorRes val dotColor: Int,
    @ColorRes val bounceDotColor: Int
) {
    NEW(
        R.string.ftl_timer_button_order_make,
        R.color.OnSurfacePrimaryAdditionalDark,
        R.color.OnSurfacePrimaryPressed,
        R.color.OnSurfacePrimaryEnabled,
        R.color.OnBackgroundSecondaryOpacity60,
        R.color.OnBackgroundSecondary
    ),
    READY_FOR_DELIVERY(
        R.string.ftl_timer_button_start_delivery,
        R.color.OnPrimary,
        R.color.PrimaryDangerPressed,
        R.color.PrimaryDangerEnabled,
        R.color.OnPrimaryLight,
        R.color.OnPrimary
    ),
    IN_DELIVERY(
        R.string.ftl_timer_button_delivered,
        R.color.BackgroundPrimary,
        R.color.AdditionalGreen,
        R.color.AdditionalDarkGreen,
        R.color.PrimaryInfoEnabledOpacity60,
        R.color.PrimaryInfoEnabled
    )
}