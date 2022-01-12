package com.foodtechlab.ftlandroiduikit.textfield.indicator

import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.util.ViewThemeManager

class FTLIndicatorViewThemeManager(
    override var darkTheme: FTLIndicatorViewTheme? = null,
    override var lightTheme: FTLIndicatorViewTheme = FTLIndicatorViewTheme(
        R.color.BackgroundDefaultDark,
        null
    )
) : ViewThemeManager<FTLIndicatorViewTheme>()
