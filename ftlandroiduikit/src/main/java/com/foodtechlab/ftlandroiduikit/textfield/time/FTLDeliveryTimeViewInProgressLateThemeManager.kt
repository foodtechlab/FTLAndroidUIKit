package com.foodtechlab.ftlandroiduikit.textfield.time

import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.util.ViewThemeManager

class FTLDeliveryTimeViewInProgressLateThemeManager(
    override var darkTheme: FTLDeliveryTimeViewTheme? = FTLDeliveryTimeViewTheme(
        R.color.TimerNegativeDark,
        android.R.color.transparent,
        R.color.TimerNegativeDark
    ), // in progress late
    override var lightTheme: FTLDeliveryTimeViewTheme = FTLDeliveryTimeViewTheme(
        R.color.TimerNegativeLight,
        android.R.color.transparent,
        R.color.TimerNegativeLight
    ) // in progress late
) : ViewThemeManager<FTLDeliveryTimeViewTheme>()
