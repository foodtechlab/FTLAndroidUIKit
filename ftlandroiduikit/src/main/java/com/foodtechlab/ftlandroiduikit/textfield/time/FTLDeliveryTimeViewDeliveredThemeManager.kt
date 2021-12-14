package com.foodtechlab.ftlandroiduikit.textfield.time

import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.textfield.time.FTLDeliveryTimeView
import com.foodtechlab.ftlandroiduikit.textfield.time.FTLDeliveryTimeViewTheme
import com.foodtechlab.ftlandroiduikit.util.ViewThemeManager

class FTLDeliveryTimeViewDeliveredThemeManager(
    override var darkTheme: FTLDeliveryTimeViewTheme? =
        FTLDeliveryTimeViewTheme(
            R.color.TimerPositiveDark,
            R.color.TimerBackgroundDark,
            R.color.TimerPositiveDark
        ), // delivered
    override var lightTheme: FTLDeliveryTimeViewTheme =
        FTLDeliveryTimeViewTheme(
            R.color.TimerPositiveLight,
            R.color.TimerBackgroundLight,
            R.color.TimerPositiveLight
        ), // delivered
) : ViewThemeManager<FTLDeliveryTimeViewTheme>()
