package com.foodtechlab.ftlandroiduikit.button

import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.util.ViewThemeManager

class FTLButtonPrimaryThemeManager(
    override var darkTheme: FTLButtonPrimaryTheme? = FTLButtonPrimaryTheme(
        R.color.selector_ftl_button_primary_or_secondary_text_dark,
        R.color.TextOnColorPrimaryOpacity60Dark,
        R.color.TextOnColorPrimaryDark,
        R.drawable.selector_ftl_button_primary_dark
    ),
    override var lightTheme: FTLButtonPrimaryTheme = FTLButtonPrimaryTheme(
        R.color.selector_ftl_button_primary_or_secondary_text_light,
        R.color.TextOnColorPrimaryOpacity60Light,
        R.color.TextOnColorPrimaryLight,
        R.drawable.selector_ftl_button_primary_light
    )
) : ViewThemeManager<FTLButtonPrimaryTheme>()
