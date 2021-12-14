package com.foodtechlab.ftlandroiduikit.button

import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.util.ViewThemeManager

class FTLButtonAdditionalThemeManager(
    override var darkTheme: FTLButtonAdditionalTheme? = FTLButtonAdditionalTheme(
        R.color.selector_ftl_button_additional_dark,
        R.color.TextInfoPressedDarkOpacity60,
        R.color.TextInfoPressedDark
    ),
    override var lightTheme: FTLButtonAdditionalTheme = FTLButtonAdditionalTheme(
        R.color.selector_ftl_button_additional_light,
        R.color.TextInfoPressedLightOpacity60,
        R.color.TextInfoPressedLight
    )
) : ViewThemeManager<FTLButtonAdditionalTheme>()
