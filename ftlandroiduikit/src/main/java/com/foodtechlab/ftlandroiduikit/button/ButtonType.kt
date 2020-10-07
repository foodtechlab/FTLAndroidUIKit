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
        ThemeManager.theme.ftlButtonAdditionalTheme.textColor,
        14f,
        false,
        R.font.roboto_regular,
        ThemeManager.theme.ftlButtonAdditionalTheme.dotColor,
        ThemeManager.theme.ftlButtonAdditionalTheme.bounceDotColor
    )
}