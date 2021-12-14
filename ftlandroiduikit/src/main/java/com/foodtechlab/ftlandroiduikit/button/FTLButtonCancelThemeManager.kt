package com.foodtechlab.ftlandroiduikit.button

import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.util.ViewThemeManager

class FTLButtonCancelThemeManager(
    override var darkTheme: FTLButtonCancelTheme? = FTLButtonCancelTheme(
        R.color.selector_ftl_button_cancel_text_dark,
        R.color.TextDangerEnabledLightOpacity60,
        R.color.TextDangerEnabledLight,
        R.drawable.selector_ftl_button_cancel_dark
    ),
    override var lightTheme: FTLButtonCancelTheme = FTLButtonCancelTheme(
        R.color.selector_ftl_button_cancel_text_light,
        R.color.TextDangerEnabledLightOpacity60,
        R.color.TextDangerEnabledLight,
        R.drawable.selector_ftl_button_cancel_light
    )
) : ViewThemeManager<FTLButtonCancelTheme>() {
}