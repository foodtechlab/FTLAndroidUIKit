package com.foodtechlab.ftlandroiduikit.textfield.auth

import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.util.ViewThemeManager

class FTLAuthCodeEditTextThemeManager(
    override var darkTheme: FTLAuthCodeEditTextTheme? = FTLAuthCodeEditTextTheme(
        R.color.TextPrimaryDark,
        R.color.TextPlaceholderDark,
        R.color.TextViewPrimaryDark,
        R.color.TextViewDividerEnabledDark,
        R.color.TextViewDividerErrorDark,
        R.color.TextViewDividerFocusedDark
    ),
    override var lightTheme: FTLAuthCodeEditTextTheme = FTLAuthCodeEditTextTheme(
        R.color.TextPrimaryLight,
        R.color.TextPlaceholderLight,
        R.color.TextViewPrimaryLight,
        R.color.TextViewDividerEnabledLight,
        R.color.TextViewDividerErrorLight,
        R.color.TextViewDividerFocusedLight
    )
) : ViewThemeManager<FTLAuthCodeEditTextTheme>() {
}