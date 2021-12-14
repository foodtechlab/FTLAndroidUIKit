package com.foodtechlab.ftlandroiduikit.textfield.time

import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.util.ViewThemeManager

class FTLDeliveryTimeViewUsualThemeManager(
    override var darkTheme: FTLDeliveryTimeViewTheme? =
        FTLDeliveryTimeViewTheme(
            R.color.TimeUsualDark,
            R.color.TimeDefaultDark,
            R.color.IconSecondaryDark
        ), // usual
    override var lightTheme: FTLDeliveryTimeViewTheme = FTLDeliveryTimeViewTheme(
        R.color.TimeUsualLight,
        R.color.TimeDefaultLight,
        R.color.IconSecondaryLight
    ), // usual
) : ViewThemeManager<FTLDeliveryTimeViewTheme>()
