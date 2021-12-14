package com.foodtechlab.ftlandroiduikit.button.radio

import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.util.ViewThemeManager

class FTLRadioButtonThemeManager(
    override var darkTheme: FTLRadioButtonTheme? = FTLRadioButtonTheme(
        R.color.TextPrimaryDark,
        R.color.ButtonSecondaryEnableDark
    ),
    override var lightTheme: FTLRadioButtonTheme = FTLRadioButtonTheme(
        R.color.TextPrimaryLight,
        R.color.ButtonSecondaryEnableLight
    )
) : ViewThemeManager<FTLRadioButtonTheme>()