package com.foodtechlab.ftlandroiduikit.textfield.time

import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.textfield.time.FTLDeliveryTimeViewTheme
import com.foodtechlab.ftlandroiduikit.util.ViewThemeManager

class FTLDeliveryTimeViewDeliveredLateThemeManager(
    override var darkTheme: FTLDeliveryTimeViewTheme? =
        FTLDeliveryTimeViewTheme(
            R.color.TimerNegativeDark,
            R.color.TimerBackgroundDark,
            R.color.TimerNegativeDark
        ), // delivered late
    override var lightTheme: FTLDeliveryTimeViewTheme = FTLDeliveryTimeViewTheme(
        R.color.TimerNegativeLight,
        R.color.TimerBackgroundLight,
        R.color.TimerNegativeLight
    ), // delivered late
) : ViewThemeManager<FTLDeliveryTimeViewTheme>()