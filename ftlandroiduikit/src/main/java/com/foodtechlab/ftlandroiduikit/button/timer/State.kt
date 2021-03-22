package com.foodtechlab.ftlandroiduikit.button.timer

import androidx.annotation.ColorRes
import com.foodtechlab.ftlandroiduikit.util.ThemeManager

/**
 * Created by Umalt on 16.06.2020
 */
enum class State(
    @ColorRes var textColor: Int,
    @ColorRes var progressColor: Int,
    @ColorRes var progressBgColor: Int,
    @ColorRes var dotColor: Int,
    @ColorRes var bounceDotColor: Int
) {
    NEW(
        ThemeManager.theme.ftlTimerButtonTheme.textColorNew,
        ThemeManager.theme.ftlTimerButtonTheme.progressColorNew,
        ThemeManager.theme.ftlTimerButtonTheme.progressBgColorNew,
        ThemeManager.theme.ftlTimerButtonTheme.dotColorNew,
        ThemeManager.theme.ftlTimerButtonTheme.bounceDotColorNew
    ),
    READY_FOR_DELIVERY(
        ThemeManager.theme.ftlTimerButtonTheme.textColorReadyForDelivery,
        ThemeManager.theme.ftlTimerButtonTheme.progressColorReadyForDelivery,
        ThemeManager.theme.ftlTimerButtonTheme.progressBgColorReadyForDelivery,
        ThemeManager.theme.ftlTimerButtonTheme.dotColorReadyForDelivery,
        ThemeManager.theme.ftlTimerButtonTheme.bounceDotColorReadyForDelivery
    ),
    IN_DELIVERY(
        ThemeManager.theme.ftlTimerButtonTheme.textColorInDelivery,
        ThemeManager.theme.ftlTimerButtonTheme.progressColorInDelivery,
        ThemeManager.theme.ftlTimerButtonTheme.progressBgColorInDelivery,
        ThemeManager.theme.ftlTimerButtonTheme.dotColorInDelivery,
        ThemeManager.theme.ftlTimerButtonTheme.bounceDotColorInDelivery
    )
}