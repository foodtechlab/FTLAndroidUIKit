package com.foodtechlab.ftlandroiduikit.textfield.time.helper

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.util.ThemeManager

enum class DeliveryStatus(
    @ColorRes var textColor: Int,
    @ColorRes var bgColor: Int,
    @DrawableRes var iconDrawable: Int? = null,
    @ColorRes var iconColor: Int? = null
) {
    USUAL(
        ThemeManager.theme.ftlDeliveryTimeViewUsualTheme.textColor,
        ThemeManager.theme.ftlDeliveryTimeViewUsualTheme.bgColor,
        R.drawable.ic_clock_24,
        ThemeManager.theme.ftlDeliveryTimeViewUsualTheme.iconColor
    ),
    URGENT(
        ThemeManager.theme.ftlDeliveryTimeViewUrgentTheme.textColor,
        ThemeManager.theme.ftlDeliveryTimeViewUrgentTheme.bgColor,
        R.drawable.ic_flash_24,
        ThemeManager.theme.ftlDeliveryTimeViewUrgentTheme.iconColor
    ),
    DELIVERED(
        ThemeManager.theme.ftlDeliveryTimeViewDeliveredTheme.textColor,
        ThemeManager.theme.ftlDeliveryTimeViewDeliveredTheme.bgColor,
        R.drawable.ic_finish_flag_green_24,
        ThemeManager.theme.ftlDeliveryTimeViewDeliveredTheme.iconColor
    ),
    DELIVERED_LATE(
        ThemeManager.theme.ftlDeliveryTimeViewDeliveredLateTheme.textColor,
        ThemeManager.theme.ftlDeliveryTimeViewDeliveredLateTheme.bgColor,
        R.drawable.ic_finish_flag_pink_24,
        ThemeManager.theme.ftlDeliveryTimeViewDeliveredLateTheme.iconColor
    ),
    CANCELED(
        ThemeManager.theme.ftlDeliveryTimeViewCanceledTheme.textColor,
        ThemeManager.theme.ftlDeliveryTimeViewCanceledTheme.bgColor,
        R.drawable.ic_clock_gray_24,
        ThemeManager.theme.ftlDeliveryTimeViewCanceledTheme.iconColor
    ),
    IN_PROGRESS(
        ThemeManager.theme.ftlDeliveryTimeViewInProgressTheme.textColor,
        ThemeManager.theme.ftlDeliveryTimeViewInProgressTheme.bgColor,
        R.drawable.ic_timer_24,
        ThemeManager.theme.ftlDeliveryTimeViewInProgressTheme.iconColor
    ),
    IN_PROGRESS_LATE(
        ThemeManager.theme.ftlDeliveryTimeViewInProgressLateTheme.textColor,
        ThemeManager.theme.ftlDeliveryTimeViewInProgressLateTheme.bgColor,
        R.drawable.ic_timer_pink_24,
        ThemeManager.theme.ftlDeliveryTimeViewInProgressLateTheme.iconColor
    )
}