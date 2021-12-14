package com.foodtechlab.ftlandroiduikit.textfield.time

import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.util.ViewThemeManager

class FTLDeliveryTimeViewCanceledThemeManager(
    override var darkTheme: FTLDeliveryTimeViewTheme? =
        FTLDeliveryTimeViewTheme(
            R.color.TextOnColorAdditionalDark,
            R.color.TimerBackgroundDark,
            R.color.IconGreyDark
        ), // canceled
    override var lightTheme: FTLDeliveryTimeViewTheme =
        FTLDeliveryTimeViewTheme(
            R.color.TextOnColorAdditionalLight,
            R.color.TimerBackgroundLight,
            R.color.IconGreyLight
        ), // canceled
) : ViewThemeManager<FTLDeliveryTimeViewTheme>() {
}