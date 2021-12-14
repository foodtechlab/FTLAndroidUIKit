package com.foodtechlab.ftlandroiduikit.button.circular

import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.util.ViewThemeManager

class FTLCircularCheckBoxThemeManager(
    override var darkTheme: FTLCircularCheckBoxTheme? = FTLCircularCheckBoxTheme(
        R.color.IconGreyDark,
        R.color.IconBlueDark
    ),
    override var lightTheme: FTLCircularCheckBoxTheme = FTLCircularCheckBoxTheme(
        R.color.IconGreyLight,
        R.color.IconBlueLight
    )
) : ViewThemeManager<FTLCircularCheckBoxTheme>()
