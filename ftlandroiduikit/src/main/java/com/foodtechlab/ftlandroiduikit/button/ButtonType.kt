package com.foodtechlab.ftlandroiduikit.button

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.FontRes
import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.util.ThemeManager

/**
 * Created by Umalt on 23.06.2020
 */
enum class ButtonType(
    @ColorRes var textColor: Int,
    var textSize: Float,
    val isAllCaps: Boolean,
    @FontRes val font: Int,
    @ColorRes var dotColor: Int,
    @ColorRes var bounceDotColor: Int,
    @DrawableRes var background: Int? = null
) {
    PRIMARY(
        ThemeManager.theme.ftlButtonPrimaryTheme.textColor,
        14f,
        true,
        R.font.roboto_medium,
        ThemeManager.theme.ftlButtonPrimaryTheme.dotColor,
        ThemeManager.theme.ftlButtonPrimaryTheme.bounceDotColor,
        ThemeManager.theme.ftlButtonPrimaryTheme.bgDrawable
    ),
    SECONDARY(
        ThemeManager.theme.ftlButtonSecondaryTheme.textColor,
        14f,
        true,
        R.font.roboto_medium,
        ThemeManager.theme.ftlButtonSecondaryTheme.dotColor,
        ThemeManager.theme.ftlButtonSecondaryTheme.bounceDotColor,
        ThemeManager.theme.ftlButtonSecondaryTheme.bgDrawable
    ),
    CANCEL(
        ThemeManager.theme.ftlButtonCancelTheme.textColor,
        14f,
        true,
        R.font.roboto_medium,
        ThemeManager.theme.ftlButtonCancelTheme.dotColor,
        ThemeManager.theme.ftlButtonCancelTheme.bounceDotColor,
        ThemeManager.theme.ftlButtonCancelTheme.bgDrawable
    ),
    ADDITIONAL(
        ThemeManager.theme.ftlButtonAdditionalTheme.textColor,
        14f,
        false,
        R.font.roboto_regular,
        ThemeManager.theme.ftlButtonAdditionalTheme.dotColor,
        ThemeManager.theme.ftlButtonAdditionalTheme.bounceDotColor
    )
}