package com.foodtechlab.ftlandroiduikit.textfield.time.helper

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import com.foodtechlab.ftlandroiduikit.R

enum class DeliveryStatus(
    @ColorRes var textColor: Int,
    @ColorRes var bgColor: Int,
    @DrawableRes var iconDrawable: Int? = null
) {
    USUAL(R.color.OnBackgroundPrimary, R.color.AdditionalLightGreen, R.drawable.ic_clock_24),
    URGENT(R.color.OnPrimary, R.color.AdditionalPink, R.drawable.ic_flash_24),
    DELIVERED(
        R.color.AdditionalGreen,
        R.color.OnSurfacePrimaryEnabled,
        R.drawable.ic_finish_flag_green_24
    ),
    DELIVERED_LATE(
        R.color.AdditionalPink,
        R.color.OnSurfacePrimaryEnabled,
        R.drawable.ic_finish_flag_pink_24
    ),
    CANCELED(
        R.color.OnSurfacePrimaryAdditionalDark,
        R.color.OnSurfacePrimaryEnabled,
        R.drawable.ic_clock_gray_24
    ),
    IN_PROGRESS(
        R.color.OnBackgroundPrimary,
        android.R.color.transparent,
        R.drawable.ic_timer_24
    ),
    IN_PROGRESS_LATE(
        R.color.AdditionalPink,
        android.R.color.transparent,
        R.drawable.ic_timer_pink_24
    )
}