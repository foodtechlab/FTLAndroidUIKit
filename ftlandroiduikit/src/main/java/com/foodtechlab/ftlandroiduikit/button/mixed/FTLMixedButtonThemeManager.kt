package com.foodtechlab.ftlandroiduikit.button.mixed

import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.util.ViewThemeManager

class FTLMixedButtonThemeManager(
    override var darkTheme: FTLMixedButtonTheme? = FTLMixedButtonTheme(
        R.color.TextPrimaryDark,
        R.color.IconPrimaryDark,
        R.color.IconBackgroundGreenDark,
        R.color.selector_ftl_mixed_button_dark
    ),
    override var lightTheme: FTLMixedButtonTheme = FTLMixedButtonTheme(
        R.color.TextPrimaryLight,
        R.color.IconPrimaryLight,
        R.color.IconBackgroundGreenLight,
        R.color.selector_ftl_mixed_button_light
    )
) : ViewThemeManager<FTLMixedButtonTheme>()
