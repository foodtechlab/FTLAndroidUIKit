package com.foodtechlab.ftlandroiduikit.textfield.tag

import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.util.ViewThemeManager

class FTLTagViewThemeManager(
    override var darkTheme: FTLTagViewTheme? = FTLTagViewTheme(
        R.color.TextOnColorAdditionalDark,
        R.color.TagBackgroundDark,
        R.color.TagBorderDark
    ),
    override var lightTheme: FTLTagViewTheme = FTLTagViewTheme(
        R.color.TextOnColorAdditionalLight,
        R.color.TagBackgroundLight,
        R.color.TagBorderLight
    )
) : ViewThemeManager<FTLTagViewTheme>()
