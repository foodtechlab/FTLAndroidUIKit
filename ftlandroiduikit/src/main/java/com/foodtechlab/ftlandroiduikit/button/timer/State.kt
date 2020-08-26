package com.foodtechlab.ftlandroiduikit.button.timer

import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import com.foodtechlab.ftlandroiduikit.R

/**
 * Created by Umalt on 16.06.2020
 */
enum class State(
    @ColorRes val textColor: Int,
    @ColorRes val progressColor: Int,
    @ColorRes val progressBgColor: Int,
    @ColorRes val dotColor: Int,
    @ColorRes val bounceDotColor: Int
) {
    NEW(
        R.color.OnSurfacePrimaryAdditionalDark,
        R.color.OnSurfacePrimaryPressed,
        R.color.OnSurfacePrimaryEnabled,
        R.color.OnBackgroundSecondaryOpacity60,
        R.color.OnBackgroundSecondary
    ),
    READY_FOR_DELIVERY(
        R.color.OnPrimary,
        R.color.PrimaryDangerPressed,
        R.color.PrimaryDangerEnabled,
        R.color.OnPrimaryLight,
        R.color.OnPrimary
    ),
    IN_DELIVERY(
        R.color.BackgroundPrimary,
        R.color.AdditionalGreen,
        R.color.AdditionalDarkGreen,
        R.color.OnPrimaryLight,
        R.color.OnPrimary
    )
}