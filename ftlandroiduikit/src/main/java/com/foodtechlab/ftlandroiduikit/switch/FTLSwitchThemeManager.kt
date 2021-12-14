package com.foodtechlab.ftlandroiduikit.switch

import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.util.ViewThemeManager

class FTLSwitchThemeManager(
    override var darkTheme: FTLSwitchTheme? = FTLSwitchTheme(
        R.color.TextPrimaryDark,
        R.color.SwitchTrackEnableDark,
        R.color.ButtonSecondaryEnableDark,
        R.color.ButtonSecondaryEnableDark
    ),
    override var lightTheme: FTLSwitchTheme = FTLSwitchTheme(
        R.color.TextPrimaryLight,
        R.color.SwitchTrackEnableLight,
        R.color.ButtonSecondaryEnableLight,
        R.color.ButtonSecondaryEnableLight
    )
) : ViewThemeManager<FTLSwitchTheme>()
