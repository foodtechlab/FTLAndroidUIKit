package com.foodtechlab.ftlandroiduikit.button

import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.util.ViewThemeManager

class FTLButtonSecondaryThemeManager(
    override var darkTheme: FTLButtonSecondaryTheme? = FTLButtonSecondaryTheme(
        R.color.selector_ftl_button_primary_or_secondary_text_dark,
        R.color.TextOnColorPrimaryOpacity60Dark,
        R.color.TextOnColorPrimaryDark,
        R.drawable.selector_ftl_button_secondary_dark
    ),
    override var lightTheme: FTLButtonSecondaryTheme = FTLButtonSecondaryTheme(
        R.color.selector_ftl_button_primary_or_secondary_text_light,
        R.color.TextOnColorPrimaryOpacity60Light,
        R.color.TextOnColorPrimaryLight,
        R.drawable.selector_ftl_button_secondary_light
    )
) : ViewThemeManager<FTLButtonSecondaryTheme>()
