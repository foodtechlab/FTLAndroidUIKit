package com.foodtechlab.ftlandroiduikit.textfield.complete

import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.textfield.FTLCompleteEditTextTheme
import com.foodtechlab.ftlandroiduikit.util.ViewThemeManager

class FTLCompleteEditTextThemeManager(
    override var darkTheme: FTLCompleteEditTextTheme? = FTLCompleteEditTextTheme(
        R.color.TextPrimaryDark,
        R.color.TextPlaceholderDark,
        R.color.TextViewPrimaryDark,
        R.color.TextViewDividerEnabledDark,
        R.color.TextViewDividerErrorDark,
        R.color.TextViewDividerFocusedDark,
        R.color.IconGreyDark,
        R.drawable.shape_ftl_drop_down_background_dark
    ),
    override var lightTheme: FTLCompleteEditTextTheme = FTLCompleteEditTextTheme(
        R.color.TextPrimaryLight,
        R.color.TextPlaceholderLight,
        R.color.TextViewPrimaryLight,
        R.color.TextViewDividerEnabledLight,
        R.color.TextViewDividerErrorLight,
        R.color.TextViewDividerFocusedLight,
        R.color.IconGreyLight,
        R.drawable.shape_ftl_drop_down_background_light
    )
) : ViewThemeManager<FTLCompleteEditTextTheme>()