package com.foodtechlab.ftlandroiduikit.textfield.edit

import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.textfield.FTLEditTextDefaultTheme
import com.foodtechlab.ftlandroiduikit.util.ViewThemeManager

class FTLEditTextDefaultThemeManager(
    override var darkTheme: FTLEditTextDefaultTheme? = FTLEditTextDefaultTheme(
        R.color.TextPrimaryDark,
        R.color.TextPlaceholderDark,
        R.color.TextViewPrimaryDark,
        R.color.TextViewDividerEnabledDark,
        R.color.TextViewDividerErrorDark,
        R.color.TextViewDividerFocusedDark
    ),
    override var lightTheme: FTLEditTextDefaultTheme = FTLEditTextDefaultTheme(
        R.color.TextPrimaryLight,
        R.color.TextPlaceholderLight,
        R.color.TextViewPrimaryLight,
        R.color.TextViewDividerEnabledLight,
        R.color.TextViewDividerErrorLight,
        R.color.TextViewDividerFocusedLight
    )
) : ViewThemeManager<FTLEditTextDefaultTheme>()
