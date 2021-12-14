package com.foodtechlab.ftlandroiduikit.textfield.time

import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.util.ViewThemeManager

class FTLDeliveryTimeViewInProgressThemeManager(
    override var darkTheme: FTLDeliveryTimeViewTheme? =
        FTLDeliveryTimeViewTheme(
            R.color.TextOnColorSecondaryDark,
            android.R.color.transparent,
            R.color.IconSecondaryDark
        ), // in progress
    override var lightTheme: FTLDeliveryTimeViewTheme =
        FTLDeliveryTimeViewTheme(
            R.color.TextOnColorSecondaryLight,
            android.R.color.transparent,
            R.color.IconSecondaryLight
        ), // in progress
) : ViewThemeManager<FTLDeliveryTimeViewTheme>() {
}